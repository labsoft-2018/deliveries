(ns deliveries.adapters.delivery
  (:require [schema.core :as s]
            [deliveries.wire.delivery :as wire.delivery]
            [deliveries.models.delivery :as models.delivery]
            [common-labsoft.misc :as misc]))

(s/defn internal->document-wire :- wire.delivery/DeliveryDocument
  [delivery :- models.delivery/Delivery]
  {:delivery (-> (misc/map-keys (comp keyword name) delivery)
                 (update :origin #(misc/map-keys (comp keyword name) %))
                 (update :destination #(misc/map-keys (comp keyword name) %)))})
