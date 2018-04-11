(ns deliveries.wire.delivery
  (:require [schema.core :as s]
            [deliveries.wire.location :as wire.location]
            [common-labsoft.time :as time]
            [common-labsoft.schema :as schema]))

(def statuses #{:open :closed :cancelled})
(s/defschema DeliveryStatus (apply s/enum statuses))

(def delivery-skeleton {:id          {:schema s/Uuid :required true}
                        :carrier-id  {:schema s/Uuid :required true}
                        :orders      {:schema #{s/Uuid} :required true}
                        :status      {:schema DeliveryStatus :required true}
                        :origin      {:schema wire.location/location-skeleton :required true}
                        :destination {:schema wire.location/location-skeleton :required true}
                        :created-at  {:schema time/LocalDateTime :required true}})
(s/defschema Delivery (schema/skel->schema delivery-skeleton))

(def delivery-document-skeleton {:delivery {:schema Delivery :required true}})
(s/defschema DeliveryDocument (schema/skel->schema delivery-document-skeleton))
