(ns deliveries.routes
  (:require [common-labsoft.pedestal.interceptors.auth :as int-auth]
            [common-labsoft.pedestal.interceptors.error :as int-err]
            [common-labsoft.pedestal.interceptors.adapt :as int-adapt]
            [common-labsoft.pedestal.interceptors.schema :as int-schema]
            [io.pedestal.http.route.definition :refer [defroutes]]
            [io.pedestal.http.body-params :as body-params]
            [deliveries.wire.delivery :as wire.delivery]
            [deliveries.controllers.delivery :as controllers.delivery]
            [deliveries.adapters.location :as adapters.location]
            [deliveries.adapters.delivery :as adapters.delivery]))

(defn hello-world
  [request]
  {:status 200
   :body   {:res "Hello, World!"}})

(defn deliveries-for-carrier
  [{{:keys [datomic]} :components location :query-params}]
  {:status 200
   :schema wire.delivery/DeliveryDocument
   :body   (-> (adapters.location/wire->internal location)
               (controllers.delivery/get-closer-open-delivery datomic)
               (adapters.delivery/internal->document-wire))})


(defn accept-delivery
  []
  )

(defroutes routes
  [[["/api" ^:interceptors [int-err/catch!
                            (body-params/body-params)
                            int-adapt/coerce-body
                            int-adapt/content-neg-intc
                            int-auth/auth
                            int-schema/coerce-output]
     {:get [:hello-world hello-world]}

     ["/request-deliveries"
      {:get [:deliveries-for-carrier deliveries-for-carrier]}
      ["/:id" ^:interceptors [(int-adapt/path->uuid :id :delivery-id)]
       {:post [:accept-delivery accept-delivery]}]]]]])
