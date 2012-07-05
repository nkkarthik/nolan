(defproject nolan "1.0.0-SNAPSHOT"
  :description "Play the script and record the video"
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [org.clojure/clojure-contrib "1.2.0"]
                 [javax.media/jmf "2.1.1e"]
                 [org.seleniumhq.selenium/selenium-java "2.9.0"]]
  :source-path "src/clj"
  :java-source-path "src/java"

  :repl-init nolan.core
  
  ;; un-comment to enable remote debugging
  ;; :jvm-opts ["-Xdebug"
  ;;            "-Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5004"]
  )