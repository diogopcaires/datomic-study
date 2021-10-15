(ns datomic-study.class3
  (:require
    [datomic.api :as d]
    [datomic-study.db :as db]
    [datomic-study.model :as model]))


(def conn (db/open-connection))


(db/create-schema conn)

(let [computer (model/create-product "Computer" "computer-slug" 1000.00M)
      expensive-smartphone (model/create-product "Expensive Smartphone" "expensive-smartphone" 12000.00M)
      calculator {:product/name "Calculator for 4 operations"}
      cheap-smartphone (model/create-product "Cheap Smartphone" "cheap-smartphone" 500.00M)]
  @(d/transact conn [computer expensive-smartphone calculator cheap-smartphone]))


(def reader (d/db conn))

(println (db/get-all-products reader))

(println (db/get-all-product-by-price reader))

(println) (db/get-all-slugs reader)

(db/delete-database)