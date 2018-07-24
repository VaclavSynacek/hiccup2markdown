(defproject org.clojars.vaclavsynacek/hiccup2markdown "0.1.0-SNAPSHOT"
  :description "Clojure hiccup to markdown converter."
  :url "https://github.com/VaclavSynacek/hiccup2markdown"
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.clojure/clojurescript "1.10.238"]
                 [org.clojure/tools.trace "0.7.9"]
                 [cljs-node-io "1.0.0"]]

  :plugins [[lein-npm "0.6.2"]
            [lein-cljsbuild "1.1.7"]
            [lein-marginalia "0.9.1"]
            [lein-auto "0.1.3"]]

  :npm {:dependencies [[source-map-support "0.4.0"]]}

  :clean-targets ["target"]

  :cljsbuild
    {:builds
      [{:id "lib"
        :source-paths  ["src"]
        :compiler {
          :main          hiccup2markdown.core
          :output-dir    "target/out"
          :output-to     "target/hiccup2markdown.js"
          :target        :nodejs
          :optimizations :none}}
       {:id "test"
        :source-paths  ["src" "test"]
        :compiler {
          :main          hiccup2markdown.tests
          :output-dir    "target/test/out"
          :output-to     "target/test/tests.js"
          :target        :nodejs
          :optimizations :none}}]})
