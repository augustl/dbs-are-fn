(ns dbs-are-fn-com.cli
  (:require [ring.adapter.jetty :as jetty]
            [stasis.core :as stasis]
            dbs-are-fn-com.web
            [optimus.prime :as optimus]
            [optimus.optimizations :as optimizations]
            [optimus.strategies :refer [serve-live-assets]]
            optimus.export
            clojure.tools.nrepl.server))

(def optimize optimizations/all)

(defn run
  [port]
  (let [repl (clojure.tools.nrepl.server/start-server :port 0 :bind "127.0.0.1")]
    (println "Repl started at" (:port repl)))
  (jetty/run-jetty
   (->
    (stasis/serve-pages dbs-are-fn-com.web/get-pages)
    (optimus/wrap dbs-are-fn-com.web/get-assets optimize serve-live-assets))
   {:port (Integer/parseInt port)
    :join? true}))


(defn export
  [dir]
  (let [assets (optimize (dbs-are-fn-com.web/get-assets) {})]
    (println "Cleaning previously generated files")
    (stasis/empty-directory! dir)

    (println "Saving static assets")
    (optimus.export/save-assets assets dir)

    (println "Exporting all pages")
    (stasis/export-pages (dbs-are-fn-com.web/get-pages) dir {:optimus-assets assets})

    (println "Voila!")
    (System/exit 0)))

