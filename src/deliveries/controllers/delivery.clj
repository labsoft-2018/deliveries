(ns deliveries.controllers.delivery
  (:require [schema.core :as s]
            [deliveries.wire.order :as wire.order]
            [deliveries.logic.delivery :as logic.delivery]
            [deliveries.diplomat.producer :as diplomat.producer]
            [deliveries.db.datomic.delivery :as datomic.delivery]
            [common-labsoft.protocols.datomic :as protocols.datomic]
            [common-labsoft.protocols.sqs :as protocols.sqs]
            [deliveries.models.delivery :as models.delivery]
            [deliveries.models.location :as models.location]))

(s/defn new-order-delivery!
  [order :- wire.order/OrderDocument
   datomic :- protocols.datomic/IDatomic
   producer :- protocols.sqs/IProducer]
  (-> (logic.delivery/new-delivery-for-order order)
      (datomic.delivery/insert! datomic)
      (diplomat.producer/delivery-allocated! producer)))

(s/defn get-closer-open-delivery :- models.delivery/Delivery
  [location :- models.location/Location
   datomic :- protocols.datomic/IDatomic]
  (-> (datomic.delivery/open-deliveries! datomic)
      (logic.delivery/closer-for-location location)))
