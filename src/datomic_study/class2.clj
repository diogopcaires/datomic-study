
(ns datomic-study.class2
  (:use clojure.pprint)
  (:require [datomic-study.db :as db]
            [datomic-study.model :as model]))

(def conn (db/open-connection))

(db/create-schema conn)

(let [computer (model/create-product "New Computer" "new_computer" 1000.00M)]
  (d/transact conn [computer]))


(let [computer (model/create-product "Smartphone" "smartphone" 100.00M)]
  (let [result @(d/transact conn [computer])
        product-id (-> result
                       :tempids
                       vals
                       first
                       )]
    @(d/transact conn [[:db/add product-id :product/value 200.00M]])
    @(d/transact conn [[:db/retract product-id :product/slug "smartphone"]])))

(db/delete-database)
; se vc n quer o slug só n colocar
;(let [object {:product/name "Radio with clock" :product/slug nil}]
;  (d/transact conn [object]))