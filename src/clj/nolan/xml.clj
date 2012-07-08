
;; Copyright (C) 2012 by Pramati Technologies

;; Licensed under the Apache License, Version 2.0 (the "License");
;; you may not use this file except in compliance with the License.
;; You may obtain a copy of the License at
;; http://www.apache.org/licenses/LICENSE-2.0

;; Unless required by applicable law or agreed to in writing,
;; software distributed under the License is distributed on an "AS
;; IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
;; express or implied.

;; See the License for the specific language governing permissions
;; and limitations under the License.

(ns nolan.xml)

(defn non-validating-sax [s ch]
  (.. (doto (. javax.xml.parsers.SAXParserFactory (newInstance))
        (.setValidating false)
        (.setFeature "http://apache.org/xml/features/nonvalidating/load-dtd-grammar" false)
        (.setFeature "http://apache.org/xml/features/nonvalidating/load-external-dtd" false)
        (.setFeature "http://xml.org/sax/features/validation" false)
        (.setFeature "http://xml.org/sax/features/external-general-entities" false)
        (.setFeature "http://xml.org/sax/features/external-parameter-entities" false))
      (newSAXParser)
      (parse s ch)))
