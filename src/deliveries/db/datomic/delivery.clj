(ns deliveries.db.datomic.delivery
  (:require [schema.core :as s]
            [deliveries.models.delivery :as models.delivery]
            [common-labsoft.protocols.datomic :as protocols.datomic]
            [common-labsoft.time :as time]
            [common-labsoft.datomic.api :as datomic]
            [common-labsoft.misc :as misc]))

(s/defn insert! :- models.delivery/Delivery
  [delivery :- models.delivery/Delivery, datomic :- protocols.datomic/IDatomic]
  (let [prepared-delivery (assoc delivery :delivery/created-at (time/now))]
    (datomic/insert! :delivery/id prepared-delivery datomic)))

(s/defn open-deliveries! :- [models.delivery/Delivery]
  [datomic :- protocols.datomic/IDatomic]
  (datomic/entities '{:find  [?e]
                      :in    [$]
                      :where [[?e :delivery/status :delivery.status/open]]} (datomic/db datomic)))

(s/defn lookup! :- models.delivery/Delivery
  [delivery-id :- s/Uuid, datomic :- protocols.datomic/IDatomic]
  (datomic/lookup! :delivery/id delivery-id (datomic/db datomic)))

(s/defn update! :- models.delivery/Delivery
  [delivery :- models.delivery/Delivery, datomic :- protocols.datomic/IDatomic]
  (datomic/update! :delivery-id delivery datomic))
