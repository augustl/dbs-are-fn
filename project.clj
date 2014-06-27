(defproject augustl-com "0.1.0-SNAPSHOT"
  :description "dbs-are-fn.com source code"
  :url "http://dbs-are-fn.com"
  :license {:name "BSD 2 Clause"
            :url "http://opensource.org/licenses/BSD-2-Clause"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [stasis "1.0.0"]
                 [ring "1.2.1"]
                 [ring/ring-jetty-adapter "1.2.1"]
                 [joda-time "2.3"]
                 [hiccup "1.0.5"]
                 [optimus "0.14.2"]
                 [org.clojure/tools.nrepl "0.2.2"]
                 [augusts-fancy-blog-post-parser "0.1.0"]]
  :aliases {"export" ["run" "-m" "dbs-are-fn-com.cli/export" "dist"]
            "server" ["run" "-m" "dbs-are-fn-com.cli/run" "4567"]})
