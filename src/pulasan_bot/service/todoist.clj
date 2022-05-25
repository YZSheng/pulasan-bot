(ns pulasan-bot.service.todoist
  (:require [cheshire.core :refer [generate-string]]
            [clj-http.client :as client]
            [clojure.string :as str]
            [pulasan-bot.domain.todo :as todo]
            [pulasan-bot.command.remind :refer [Todo]]
            [clojure.spec.alpha :as s]))

(def ^:private todoist-endpiont "https://api.todoist.com/rest/v1/tasks")

(defn- generate-payload [task]
  (generate-string {:content (::todo/title task) :description "Added by PulasanBot"}))

(defn- validate-api-token [[todo todoist-api-token]]
  (if (not (str/blank? todoist-api-token))
    [todo todoist-api-token]
    (throw (IllegalStateException. "Todo-ist API token is missing."))))

(defn- validate-payload [[todo todoist-api-token]]
  (if (s/valid? ::todo/todo todo)
    [todo todoist-api-token]
    (throw (IllegalArgumentException. (str "Invalid todo object received" todo)))))

(defn- save-todo [[todo todoist-api-token]]
  (client/post todoist-endpiont {:headers {"Authorization" (str "Bearer " todoist-api-token)}
                                 :content-type :json
                                 :body (generate-payload todo)}))

(defn get-todoist [todoist-api-token]
  (reify Todo
    (save [_ todo]
      (->> [todo todoist-api-token]
           validate-api-token
           validate-payload
           save-todo))))
