(ns deliveries.diplomat.sqs
  (:require [schema.core :as s]
            [deliveries.controllers.delivery :as controllers.delivery]
            [deliveries.wire.order :as wire.order]
            [deliveries.wire.delivery :as wire.delivery]))

(s/defn delivery-requested!
  [order :- wire.order/OrderDocument
   {:keys [datomic sqs-producer]}]
  (controllers.delivery/new-order-delivery! order datomic sqs-producer))

(def settings {:delivery-requested {:direction :consumer
                                    :schema    wire.order/OrderDocument
                                    :handler   delivery-requested!}
               :delivery-allocated {:direction :producer
                                    :schema    wire.delivery/DeliveryDocument}
               :delivery-closed    {:direction :producer
                                    :schema    wire.delivery/DeliveryDocument}
               :delivery-cancelled {:direction :producer
                                    :schema    wire.delivery/DeliveryDocument}})
