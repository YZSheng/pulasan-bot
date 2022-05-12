(ns pulasan-bot.command.remind
  (:require [cheshire.core :refer [generate-string]]
            [environ.core :refer [env]]
            [clj-http.client :as client]
            [clojure.string :as str]))

(def ^:private todoist-api-token (env :todoist-token))

(def ^:private todoist-endpiont "https://api.todoist.com/rest/v1/tasks")

(defn- generate-payload [task]
  (generate-string {:content task}))

(defn- save-todo [what]
  {:pre [(not (str/blank? todoist-api-token))]}
  (client/post todoist-endpiont {:headers {"Authorization" (str "Bearer " todoist-api-token)}
                                 :content-type :json
                                 :body (generate-payload what)}))

(defn handle-remind-command [command]
  (let [[_ _ what] (first (re-seq #"/remind (\w+) to (.+)" command))
        task (str/capitalize what)]
    (save-todo task)))

(comment
  (str/capitalize "pick up laundry")
  (handle-remind-command "/remind me to pick up the laundry."))