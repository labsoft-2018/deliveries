(ns deliveries.models.delivery
  (:require [schema.core :as s]
            [common-labsoft.time :as time]
            [common-labsoft.schema :as schema]
            [deliveries.models.location :as models.location]))

(def delivery-statuses #{:delivery.status/open
                         :delivery.status/closed
                         :delivery.status/cancelled})
(s/defschema DeliveryStatus (apply s/enum delivery-statuses))

(def delivery-skeleton {:delivery/id          {:schema s/Uuid :id true}
                        :delivery/carrier-id  {:schema s/Uuid :required false}
                        :delivery/orders      {:schema #{s/Uuid} :required true}
                        :delivery/status      {:schema DeliveryStatus :required true}
                        :delivery/origin      {:schema models.location/Location :required true :component true}
                        :delivery/destination {:schema models.location/Location :required true :component true}
                        :delivery/created-at  {:schema time/LocalDateTime :required true}})
(s/defschema Delivery (schema/skel->schema delivery-skeleton))
