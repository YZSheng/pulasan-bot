(ns pulasan-bot.command.remind
  (:require [clojure.string :as str]
            [pulasan-bot.domain.todo :as todo]))

(defprotocol Todo
  (save [this todo]))

(defn handle-remind-command [command todo-service]
  (let [[_ _ what] (first (re-seq #"/remind (\w+) to (.+)" command))
        task (str/capitalize what)]
    (save todo-service {::todo/title task})))
