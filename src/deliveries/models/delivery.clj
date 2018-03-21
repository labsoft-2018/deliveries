(ns deliveries.models.delivery
  (:require [schema.core :as s]
            [common-labsoft.time :as time]
            [common-labsoft.schema :as schema]))

(def delivery-statuses #{:delivery.status/open
                         :delivery.status/closed
                         :delivery.status/cancelled})
(s/defschema DeliveryStatus (apply s/enum delivery-statuses))

(def location-skeleton {:location/lat {:schema s/Num :required true}
                        :location/lng {:schema s/Num :required true}})
(s/defschema Location (schema/skel->schema location-skeleton))

(def delivery-skeleton {:id          {:schema s/Uuid :id true}
                        :carrier-id  {:schema s/Uuid :required true}
                        :orders      {:schema #{s/Uuid} :required true}
                        :status      {:schema DeliveryStatus :required true}
                        :origin      {:schema Location :required true :component true}
                        :destination {:schema Location :required true :component true}
                        :created-at  {:schema time/LocalDateTime :required true}})
(s/defschema Delivery (schema/skel->schema delivery-skeleton))
