(ns deliveries.models.location
  (:require [schema.core :as s]
            [common-labsoft.schema :as schema]))

(def location-skeleton {:location/lat {:schema BigDecimal :required true}
                        :location/lng {:schema BigDecimal :required true}})
(s/defschema Location (schema/skel->schema location-skeleton))
