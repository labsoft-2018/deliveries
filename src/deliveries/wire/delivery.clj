(ns deliveries.wire.delivery
  (:require [schema.core :as s]
            [common-labsoft.time :as time]
            [common-labsoft.schema :as schema]))

(def statuses #{:open :closed :cancelled})
(s/defschema DeliveryStatus (apply s/enum statuses))

(def location-skeleton {:lat {:schema s/Num :required true}
                        :lng {:schema s/Num :required true}})
(s/defschema Location (schema/skel->schema location-skeleton))

(def delivery-skeleton {:id          {:schema s/Uuid :required true}
                        :carrier-id  {:schema s/Uuid :required true}
                        :orders      {:schema #{s/Uuid} :required true}
                        :status      {:schema DeliveryStatus :required true}
                        :origin      {:schema Location :required true :component true}
                        :destination {:schema Location :required true :component true}
                        :created-at  {:schema time/LocalDateTime :required true}})
(s/defschema Delivery (schema/skel->schema delivery-skeleton))

(def delivery-document-skeleton {:delivery {:schema Delivery :required true}})
(s/defschema DeliveryDocument (schema/skel->schema delivery-document-skeleton))
