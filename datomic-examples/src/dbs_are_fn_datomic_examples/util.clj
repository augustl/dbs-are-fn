(ns dbs-are-fn-datomic-examples.util
  (:require [datomic.api :as d]))

(defn get-inserted-entity
  [tempid tx-res]
  (d/entity (:db-after tx-res) (d/resolve-tempid (:db-after tx-res) (:tempids tx-res) tempid)))

(defn transact-entity
  [datomic-conn tempid tx]
  (get-inserted-entity tempid @(d/transact datomic-conn tx)))
