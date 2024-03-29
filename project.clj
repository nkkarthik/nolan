(defproject nolan "0.0.1"
  :description "Play the selenium script and record the video"

  :dependencies [[org.clojure/clojure "1.4.0"]
                 [org.clojure/clojure-contrib "1.2.0"]
                 [javax.media/jmf "2.1.1e"]
                 [org.seleniumhq.selenium/selenium-java "2.24.1"]]

  ;; ;; lein 1
  ;; :source-path "src/clj"
  ;; :java-source-path "src/java"
  ;; :resources-path "src/resources"
  ;; :repl-init nolan.core

  ;; lein 2
  :source-paths ["src/clj"]
  :java-source-paths ["src/java"]
  :resource-paths ["src/resources"]
  :repl-options {:init-ns nolan.core
                 :init (println "take this repl, bro")}
  
  ;; lein 1 and 2
  :main nolan.core

  ;; un-comment to enable remote debugging
  ;; :jvm-opts ["-Xdebug"
  ;;            "-Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5004"]

  :plugins [[lein-swank "1.4.4"]])