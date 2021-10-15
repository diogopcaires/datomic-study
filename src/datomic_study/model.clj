(ns datomic-study.model)

(defn create-product
  [name slug price]
  {:product/name  name
   :product/slug  slug
   :product/value price})