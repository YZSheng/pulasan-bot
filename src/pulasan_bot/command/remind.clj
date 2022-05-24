(ns pulasan-bot.command.remind
  (:require [clojure.string :as string]
            [pulasan-bot.domain.todo :as todo]))

(defprotocol Todo
  (save [this todo]))

(defn handle-remind-command [command todo-service]
  (let [[_ _ task] (first (re-seq #"/remind (\w+) to (.+)" command))]
    (if-let [t task]
      (->> t
           string/capitalize
           ((fn [task] {::todo/title task}))
           (save todo-service))
      (throw (IllegalArgumentException. (str "Cannot parse command " command))))))
