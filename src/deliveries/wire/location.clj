(ns deliveries.wire.location
  (:require [common-labsoft.schema :as schema]
            [schema.core :as s]))

(def location-skeleton {:lat {:schema BigDecimal :required true}
                        :lng {:schema BigDecimal :required true}})
(s/defschema Location (schema/skel->schema location-skeleton))
