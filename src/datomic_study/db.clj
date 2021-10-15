(ns datomic-study.db
  (:require [datomic.api :as d]))

(def db-uri "datomic:dev://localhost:4334/hello")

(defn open-connection
  []
  (d/create-database db-uri)
  (d/connect db-uri))

(defn delete-database []
  (d/delete-database db-uri))



(defn get-all-product-by-slug
  [snapshot slug]
  (d/q '[:find ?entity ?slug
         :in $ ?slug
         :where [?entity :product/slug ?slug]] snapshot slug))



(defn get-all-product-by-price
    [snapshot]
    (d/q '[:find ?name ?price
           :keys name, price
           :where [?product :product/value ?price]
                  [?product :product/name ?name]] snapshot))

;USING PULL

;pull attr by attr
;(defn get-all-products [snapshot]
;  (d/q '[:find (pull ?identifier [:product/name :product/value :product/slug])
;         :where [?identifier :product/name  ?name]] snapshot))

(defn get-all-products [snapshot]
  (d/q '[:find (pull ?identifier [*])
         :where [?identifier :product/name  ?name]] snapshot))

; USING KEYS
;(defn get-all-product-by-price
;  [snapshot]
;  (d/q '[:find ?name ?price
;         :keys name, price
;         :where [?product :product/value ?price]
;                [?product :product/name ?name]] snapshot))


(defn get-all-slugs
  [snapshot]
  (d/q '[:find ?value
         :where [?entity :product/slug ?value]]
       snapshot))


(def schema [{:db/ident       :product/name
              :db/valueType   :db.type/string
              :db/cardinality :db.cardinality/one
              :db/doc         "Product Name"}
             {:db/ident       :product/slug
              :db/valueType   :db.type/string
              :db/cardinality :db.cardinality/one
              :db/doc         "Product Slug will appear in url"}
             {:db/ident       :product/value
              :db/valueType   :db.type/bigdec
              :db/cardinality :db.cardinality/one
              :db/doc         "The product price"}])

(defn create-schema [conn]
  (d/transact conn schema))
