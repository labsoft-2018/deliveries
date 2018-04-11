(ns deliveries.components
  (:require [com.stuartsierra.component :as component]
            [deliveries.routes :refer [routes]]
            [deliveries.diplomat.sqs :as sqs]
            [deliveries.db.datomic.config :as datomic.config]
            [common-labsoft.components.webapp :as components.webapp]
            [common-labsoft.components.crypto :as components.crypto]
            [common-labsoft.components.token :as components.token]
            [common-labsoft.components.datomic :as components.datomic]
            [common-labsoft.components.pedestal :as components.pedestal]
            [common-labsoft.components.s3-client :as components.s3-client]
            [common-labsoft.components.http-client :as components.http]
            [common-labsoft.components.config :as components.config]
            [common-labsoft.components.sqs :as components.sqs]
            [common-labsoft.components.mock-sqs :as components.mock-sqs]))

(defn base-system [config-name]
  (component/system-map
    :config (component/using (components.config/new-config config-name) [])
    :s3-auth (component/using (components.s3-client/new-s3-client :s3-auth) [:config])
    :pedestal (component/using (components.pedestal/new-pedestal routes) [:config :webapp])
    :datomic (component/using (components.datomic/new-datomic datomic.config/settings) [:config])
    :token (component/using (components.token/new-token) [:config :s3-auth])
    :crypto (component/using (components.crypto/new-crypto) [:config])
    :sqs-producer (component/using (components.sqs/new-producer sqs/settings) [:config])
    :sqs-consumer (component/using (components.sqs/new-consumer sqs/settings) [:config :webapp])
    :http (component/using (components.http/new-http-client) [:config :token])
    :webapp (component/using (components.webapp/new-webapp) [:config :datomic :token :crypto :sqs-producer :http])))

(defn test-system [config-name]
  (merge
    (base-system config-name)
    (component/system-map
      :sqs-producer (component/using (components.mock-sqs/new-mock-producer sqs/settings) [:config])
      :sqs-consumer (component/using (components.mock-sqs/new-mock-consumer sqs/settings) [:config :webapp]))))
