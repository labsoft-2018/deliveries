(ns deliveries.adapters.location
  (:require [schema.core :as s]
            [deliveries.models.location :as models.location]
            [deliveries.wire.location :as wire.location]))

(s/defn wire->internal :- models.location/Location
  [{:keys [lat lng]} :- wire.location/Location]
  {:location/lat (bigdec lat)
   :location/lng (bigdec lng)})

(s/defn internal->wire :- wire.location/Location
  [{:keys [location/lat location/lng]} :- models.location/Location]
  {:lat lat
   :lng lng})
