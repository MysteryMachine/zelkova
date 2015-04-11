(defproject jamesmacaulay/zelkova "0.3.2-SNAPSHOT"
  :description "Elm-style FRP for Clojure and ClojureScript"
  :url "http://github.com/jamesmacaulay/zelkova"
  :license {:name "MIT License"
            :url "http://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.7.0-alpha6"]
                 [org.clojure/clojurescript "0.0-3178"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]]
  :plugins [[lein-cljsbuild "1.0.5"]
            [com.keminglabs/cljx "0.6.0" :exclusions [org.clojure/clojure
                                                      com.cemerick/piggieback
                                                      net.cgrand/parsley]]
            [com.cemerick/clojurescript.test "0.3.3"]]
  :jar-exclusions [#"\.cljx"]
  :aliases {"repl" ["with-profile" "repl" "repl"]
            "clj-test" ["with-profile" "clj" "test"]
            "cljs-test" ["do" "cljx" ["cljsbuild" "test"]]
            "cljs-autotest" ["do" "cljx" ["cljsbuild" "auto" "test"]]
            "all-tests" ["do" "clean" ["clj-test"] ["cljs-test"]]}
  :profiles {:dev {:dependencies [[org.clojure/tools.namespace "0.2.10"]
                                  [org.clojure/tools.nrepl "0.2.10"]
                                  [com.cemerick/piggieback "0.2.0"]
                                  [net.cgrand/parsley "0.9.3" :exclusions [org.clojure/clojure]]]
                   :source-paths ["target/classes"]}
             :repl [:dev {:source-paths ["repl" "target/classes" "target/generated/test/clj" "target/generated/test/cljs"]
                          :test-paths ["target/generated/test/clj" "target/generated/test/cljs"]}]
             :clj [:dev {:source-paths ["target/classes"]
                         :test-paths ["target/generated/test/clj"]}]
             :cljs [:dev]}
  :prep-tasks [["cljx" "once"] "javac" "compile"]
  :cljsbuild {:test-commands {"phantom" ["phantomjs" :runner "target/testable.js"]
                              "node" ["node" :node-runner "target/testable.js"]}
              :builds [{:id "test"
                        :source-paths ["target/classes" "target/generated/test/clj" "target/generated/test/cljs"]
                        :notify-command ["phantomjs" :cljs.test/runner "target/testable.js"]
                        :compiler {:output-to "target/testable.js"
                                   :libs [""]
                                   ; node doesn't like source maps I guess?
                                   ;:source-map "target/testable.js.map"
                                   :optimizations :simple
                                   :pretty-print true}}]}
  :cljx {:builds [{:source-paths ["src/cljx"]
                   :output-path "target/classes"
                   :rules :clj}
                  {:source-paths ["src/cljx"]
                   :output-path "target/classes"
                   :rules :cljs}
                  {:source-paths ["test/cljx"]
                   :output-path "target/generated/test/clj"
                   :rules :clj}
                  {:source-paths ["test/cljx"]
                   :output-path "target/generated/test/cljs"
                   :rules :cljs}]})
