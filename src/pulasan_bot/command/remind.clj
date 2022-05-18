(ns pulasan-bot.command.remind
  (:require [clojure.string :as str]
            [pulasan-bot.domain.todo :as todo]
            [pulasan-bot.service.todoist :refer [save-todo]]))

(defn handle-remind-command [command]
  (let [[_ _ what] (first (re-seq #"/remind (\w+) to (.+)" command))
        task (str/capitalize what)]
    (save-todo {::todo/title task})))

(comment
  (str/capitalize "pick up laundry")
  (handle-remind-command "/remind me to pick up the laundry."))