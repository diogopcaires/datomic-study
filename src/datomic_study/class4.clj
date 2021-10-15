(ns datomic-study.class4
  (:require
    [datomic.api :as d]
    [datomic-study.db :as db]
    [datomic-study.model :as model]))


(def conn (db/open-connection))


(db/create-schema conn)

(let [computer (model/create-product "Computer" "computer-slug" 1000.00M)
      expensive-smartphone (model/create-product "Expensive Smartphone" "expensive-smartphone" 12000.00M)]
  @(d/transact conn [computer expensive-smartphone]))

(def older-snapshot (d/db conn))

(let [calculator {:product/name "Calculator for 4 operations"}
      cheap-smartphone (model/create-product "Cheap Smartphone" "cheap-smartphone" 500.00M)]
  @(d/transact conn [calculator cheap-smartphone]))

(def new-snapshot (d/db conn))

(count (db/get-all-products (d/as-of (d/db conn)  #inst"2021-10-15T18:14:04.885-00:00" )))

;current moment
;(println (db/get-all-products (d/db conn)))

;filtered by point of time
;(println (db/get-all-products (d/as-of conn ??)))

;(db/delete-database)