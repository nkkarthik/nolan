
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

(ns nolan.selenium
  (require [clojure.xml :as xml]
           [clojure.zip :as zip]
           [clojure.contrib.zip-filter.xml :as zf])
  (import [org.openqa.selenium.firefox FirefoxDriver]
          [org.openqa.selenium WebDriverBackedSelenium]))

(defmulti do-command
  (fn [driver command] (:command command)))

(defmacro defcommand [command & body]
  `(defmethod do-command ~(str command)
     [driver# command#]
     (let [~'target (:target command#)
           ~'value (:value command#)]
       (doto driver#
         ~@body)
       (Thread/sleep 1000))))

(defcommand open (.open target))
(defcommand click (.click target))
(defcommand clickAndWait
  (.click target)
  (.waitForPageToLoad "10000"))

(defn parse-commands [html-file]
  (map
   (partial zipmap [:command :target :value])
   (partition 3
              (-> (java.io.File. html-file)
                  (xml/parse)
                  (zip/xml-zip)
                  (zf/xml-> :body :table :tbody :tr :td zf/text)))))

(defn run-commands [web-driver commands]
  (let [web-backed-selenium-driver
        (WebDriverBackedSelenium. web-driver "")]
    (doseq [c commands]
      (do-command web-backed-selenium-driver c))))

(defmacro with-firefox-driver [driver-name & body]
  `(let [~driver-name (FirefoxDriver.)]
     (try
       ~@body
       (finally (.close ~driver-name)))))

