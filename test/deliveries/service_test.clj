(ns deliveries.service-test
  (:require [midje.sweet :refer :all]
            [deliveries.service :as ser]
            [common-labsoft.test-helpers :as th]))

(th/with-service [ser/start! ser/stop!] [system service]
 (fact "Http Test"
   (th/request! service :get "/api") => {:res "Hello, World!"}))
