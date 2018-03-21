(ns deliveries.wire.order
  (:require [schema.core :as s]
            [common-labsoft.schema :as schema]
            [deliveries.wire.location :as wire.location]))

(def order-skeleton {:id                   {:schema s/Uuid :required true}
                     :source-location      {:schema wire.location/Location :required true}
                     :destination-location {:schema wire.location/Location :required true}})
(s/defschema Order (schema/skel->schema order-skeleton))

(def order-document {:order {:schema Order :required true}})
(s/defschema OrderDocument (schema/skel->schema order-document))
