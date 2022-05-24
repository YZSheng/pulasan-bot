(ns pulasan-bot.service.todoist
  (:require [cheshire.core :refer [generate-string]]
            [environ.core :refer [env]]
            [clj-http.client :as client]
            [clojure.string :as str]
            [pulasan-bot.domain.todo :as todo]
            [pulasan-bot.command.remind :refer [Todo]]
            [clojure.spec.alpha :as s]))

(def ^:private todoist-api-token (env :todoist-token))

(def ^:private todoist-endpiont "https://api.todoist.com/rest/v1/tasks")

(defn- generate-payload [task]
  (generate-string {:content (::todo/title task) :description "Added by PulasanBot"}))

(def todoist (reify Todo
                 (save [_ todo]
                   {:pre [(not (str/blank? todoist-api-token))
                          (s/valid? ::todo/todo todo)]}
                   (client/post todoist-endpiont {:headers {"Authorization" (str "Bearer " todoist-api-token)}
                                                  :content-type :json
                                                  :body (generate-payload todo)}))))
