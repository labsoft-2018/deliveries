(ns deliveries.db.datomic.config
  (:require [deliveries.models.delivery :as models.delivery]))

(def settings {:schemas [models.delivery/delivery-skeleton]
               :enums   [models.delivery/delivery-statuses]})
