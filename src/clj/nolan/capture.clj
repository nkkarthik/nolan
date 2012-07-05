
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
  (import [nolan MovieWriter MovieDataSource
           RobotScreenshotImageProvider]
          [java.awt Robot]))

(defn record [movie-file]
  (let [done? (atom false)
        writer (future
                 (.write (MovieWriter.)
                         movie-file
                         (MovieDataSource.
                          (proxy [RobotScreenshotImageProvider] [(Robot.)]
                            (done [] @done?)))))]
    (fn [cmd]
        (cond (= cmd :stop) (do (reset! done? true) @writer)
              :else (println "unknown command: " cmd)))))

(defn stop [recorder]
  (recorder :stop))

(defmacro with-recording-to [movie-file & body]
  `(let [recorder# (record ~movie-file)]
     (try
       ~@body
       (finally (stop recorder#)))))