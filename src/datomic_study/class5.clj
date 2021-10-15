(ns datomic-study.class5
  (:use clojure.pprint)
  (:require
    [datomic.api :as d]
    [datomic-study.db :as db]
    [datomic-study.model :as model]))


(def conn (db/open-connection))


(db/create-schema conn)

(let [computer (model/create-product "Computer" "computer-slug" 1000.00M)
      expensive-smartphone (model/create-product "Expensive Smartphone" "expensive-smartphone" 12000.00M)
      calculator {:product/name "Calculator for 4 operations" :product/value 100M}
      cheap-smartphone (model/create-product "Cheap Smartphone" "cheap-smartphone" 500.00M)]
  (pprint @(d/transact conn [computer expensive-smartphone calculator cheap-smartphone])))


(d/transact conn [[:db/add 17592186045418 :product/tags "desktop"]
                  [:db/add 17592186045418 :product/tags "gamer"]])

(db/get-all-products (d/db conn))

;removing tags
(d/transact conn [[:db/retract 17592186045418 :product/tags "desktop"]])

(db/get-all-products (d/db conn))

; add a new tag
(d/transact conn [[:db/add 17592186045418 :product/tags "laptop"]])

(db/get-all-products (d/db conn))

;add tags to other products

(d/transact conn [[:db/add 17592186045420 :product/tags "mobile"]])
(d/transact conn [[:db/add 17592186045421 :product/tags "mobile"]])

(db/get-products-by-tag (d/db conn) "laptop")
(db/get-products-by-tags (d/db conn) ["mobile" "laptop"])


(db/delete-database)