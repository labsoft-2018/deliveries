(ns deliveries.service-test
  (:require [midje.sweet :refer :all]
            [deliveries.service :as ser]
            [common-labsoft.test-helpers :as th]
            [common-labsoft.components.mock-sqs :as mock-sqs]
            [matcher-combinators.midje :refer [match]]
            [deliveries.diplomat.sqs :as sqs]
            [common-labsoft.misc :as misc]))

(def order-id (misc/uuid))
(def order-request {:id                   order-id
                    :source-location      {:lat -2.12345678M
                                           :lng -3.87654321M}
                    :destination-location {:lat -4.12345678M
                                           :lng -5.87654321M}})

(defn get-deliveries [world]
  (th/with-token {:token/scopes #{"carrier"}}
    (th/request! world :get "/api/request-deliveries?lat=-2.12345678&lng=-3.87654321")))

(th/with-world [world ser/restart!]
  (sqs/delivery-requested! order-request (th/get-system world))
  (fact "we have produced a delivery-allocated message"
    (mock-sqs/get-produced-messages (-> world (th/get-system) :sqs-producer) :delivery-allocated)
    => (match [{:delivery {:destination {:lat -4.12345678M
                                         :lng -5.87654321M}
                           :orders      #{order-id}
                           :origin      {:lat -2.12345678M
                                         :lng -3.87654321M}
                           :status      :open}}]))

  (get-deliveries world))
