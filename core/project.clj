(defproject dw "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.9.0"]]
  :plugins [[com.siili/lein-cucumber "1.0.7"]]
  :cucumber-feature-paths ["features/"]
  :profiles
  {:dev {:dependencies [[com.siili/lein-cucumber "1.0.7"]]}}
  :aot [dw.core dw.nouns]
  )
