(ns deliveries.db.datomic.config
  (:require [deliveries.models.delivery :as models.delivery]
            [deliveries.models.location :as models.location]))

(def settings {:schemas [models.delivery/delivery-skeleton
                         models.location/location-skeleton]
               :enums   [models.delivery/delivery-statuses]})
