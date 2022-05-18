(ns pulasan-bot.service.todoist
  (:require [cheshire.core :refer [generate-string]]
            [environ.core :refer [env]]
            [clj-http.client :as client]
            [clojure.string :as str]))

(def ^:private todoist-api-token (env :todoist-token))

(def ^:private todoist-endpiont "https://api.todoist.com/rest/v1/tasks")

(defn- generate-payload [task]
  (generate-string {:content task :description "Added by PulasanBot"}))

(defn save-todo [todo]
  {:pre [(not (str/blank? todoist-api-token))]}
  (client/post todoist-endpiont {:headers {"Authorization" (str "Bearer " todoist-api-token)}
                                 :content-type :json
                                 :body (generate-payload todo)}))