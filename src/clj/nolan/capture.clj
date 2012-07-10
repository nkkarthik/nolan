
;; Copyright (C) 2012 by Pramati Technologies

;; Licensed under the Apache License, Version 2.0 (the "License");
;; you may not use this file except in compliance with the License.
;; You may obtain a copy of the License at 
;; http://www.apache.org/licenses/LICENSE-2.0

;; Unless required by applicable law or agreed to in writing, software
;; distributed under the License is distributed on an "AS IS" BASIS,
;; WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
;; See the License for the specific language governing permissions and
;; limitations under the License.

(ns nolan.capture
  (require [nolan.mouse :as mouse]
           [nolan.image :as image])
  (import [nolan MovieWriter MovieDataSource]
          [java.awt Robot Dimension Rectangle Toolkit]
          [javax.media Format]
          [javax.media.format VideoFormat]))

(def frame-rate 5)

(defn- jpeg-video-format [size]
  (VideoFormat. VideoFormat/JPEG
                (Dimension. (.width size) (.height size))
                Format/NOT_SPECIFIED
                Format/byteArray
                frame-rate))

(defn- take-screen-shot [robot size]
  (.createScreenCapture robot size))

(defn- transform [transformations img]
  (image/with-graphics [g img]
    (doseq [t transformations]
      (t g)))
  img)

(defn- transformations []
  [(partial mouse/draw (mouse/cursor-image))])

(defn record [movie-file screen-area]
  (let [size screen-area
        screen-shot (comp (partial transform (transformations))
                          (partial take-screen-shot (Robot.) size))
        done? (atom false)
        writer (future
                 (.write (MovieWriter.)
                         movie-file
                         (MovieDataSource.
                          (jpeg-video-format size)
                          (take-while (fn [x] (not @done?))
                                      (repeatedly screen-shot)))))]
    (fn [cmd]
      (cond (= cmd :stop) (do (reset! done? true) @writer)
            :else (println "unknown command: " cmd)))))

(defn stop [recorder]
  (recorder :stop))

(defmacro with-recording-to [movie-file screen-area & body]
  `(let [recorder# (record ~movie-file ~screen-area)]
     (try
       ~@body
       (finally (stop recorder#)))))