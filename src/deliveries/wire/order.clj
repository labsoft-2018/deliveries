(ns deliveries.wire.order
  (:require [schema.core :as s]
            [common-labsoft.schema :as schema]))

(def location-skeleton {:lat {:schema s/Num :required true}
                        :lng {:schema s/Num :required true}})
(s/defschema Location (schema/skel->schema location-skeleton))

(def order-skeleton {:id                   {:schema s/Uuid :required true}
                     :source-location      {:schema Location :required true}
                     :destination-location {:schema Location :required true}})
(s/defschema Order (schema/skel->schema order-skeleton))

(def order-document {:order {:schema Order :required true}})
(s/defschema OrderDocument (schema/skel->schema order-document))
