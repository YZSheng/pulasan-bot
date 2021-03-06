(defproject pulasan-bot "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"

  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.11.1"]
                 [org.clojure/core.async "1.5.648"]
                 [environ             "1.1.0"]
                 [morse               "0.2.4"]
                 [clj-http            "3.12.3"]
                 [cheshire            "5.10.2"]]

  :plugins [[lein-environ "1.1.0"]
            [jonase/eastwood "1.2.3"]]
  :min-lein-version "2.0.0"
  :main ^:skip-aot pulasan-bot.core
  :uberjar-name "pulasan-bot-standalone.jar"
  :profiles {:production {:env {:production true}}})
