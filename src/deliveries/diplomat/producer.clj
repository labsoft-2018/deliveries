(ns deliveries.diplomat.producer
  (:require [schema.core :as s]
            [deliveries.models.delivery :as models.delivery]
            [deliveries.adapters.delivery :as adapters.delivery]
            [common-labsoft.protocols.sqs :as protocols.sqs]))

(s/defn delivery-allocated!
  [delivery :- models.delivery/Delivery
   producer :- protocols.sqs/IProducer]
  (protocols.sqs/produce! producer {:queue   :delivery-allocated
                                    :message (adapters.delivery/internal->document-wire delivery)}))

(s/defn delivery-closed!
  [delivery :- models.delivery/Delivery
   producer :- protocols.sqs/IProducer]
  (protocols.sqs/produce! producer {:queue   :delivery-closed
                                    :message (adapters.delivery/internal->document-wire delivery)}))
