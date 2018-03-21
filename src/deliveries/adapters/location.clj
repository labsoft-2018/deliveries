(ns deliveries.adapters.location
  (:require [schema.core :as s]
            [deliveries.models.delivery :as models.delivery]
            [deliveries.wire.delivery :as wire.delivery]))

(s/defn wire->internal :- models.delivery/Location
  [{:keys [lat lng]} :- wire.delivery/Location]
  {:location/lat lat
   :location/lng lng})

(s/defn internal->wire :- wire.delivery/Location
  [{:keys [location/lat location/lng]} :- models.delivery/Location]
  {:lat lat
   :lng lng})
