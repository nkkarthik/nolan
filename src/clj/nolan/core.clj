
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

(ns nolan.core
  (require [nolan.capture :as capture]
           [nolan.selenium :as selenium]))

(defn record-selenium-script [script-file movie-file]
  (selenium/with-firefox browser
    (let [commands (selenium/parse-commands script-file)
          screen-area (selenium/browser-area browser)]
      (capture/with-recording-to movie-file screen-area
        (selenium/run-commands browser commands)))))

(defn -main [& args]
  (record-selenium-script "test.html" "test.mov"))