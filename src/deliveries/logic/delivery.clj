(ns deliveries.logic.delivery
  (:require [schema.core :as s]
            [deliveries.models.delivery :as models.delivery]
            [deliveries.wire.order :as wire.order]
            [deliveries.wire.delivery :as wire.delivery]
            [geo.spatial :as spatial]
            [common-labsoft.misc :as misc]))

(s/defn new-delivery-for-order :- models.delivery/Delivery
  [order :- wire.order/Order]
  {:delivery/status      :delivery.status/open
   :delivery/orders      #{(:id order)}
   :delivery/origin      (misc/map-keys #(keyword "location" (name %)) (:source-location order))
   :delivery/destination (misc/map-keys #(keyword "location" (name %)) (:destination-location order))})

(s/defn ^:private location->spatial4j-point [{:keys [lat lng]}] (spatial/spatial4j-point lat lng))
(s/defn ^:private location-distance [location point]
  (-> (location->spatial4j-point location)
      (spatial/distance point)))

(s/defn closer-for-location :- models.delivery/Delivery
  [deliveries :- [models.delivery/Delivery]
   location :- wire.delivery/Location]
  (let [carrier-point (location->spatial4j-point location)]
    (->> (filter (fn [{origin :origin}]
                   (-> (location->spatial4j-point origin)
                       (spatial/intersects? (spatial/circle carrier-point 5000)))) deliveries)
         (sort (fn [{origin1 :origin} {origin2 :origin}]
                 (<
                   (location-distance origin1 carrier-point)
                   (location-distance origin2 carrier-point))))
         first)))