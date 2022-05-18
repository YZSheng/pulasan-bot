(ns pulasan-bot.message.core
    (:require [pulasan-bot.message.baobao :refer [respond-to-baobao]]))

(def ^:private default-answer "I don't do a whole lot ... yet.")

(defn- find-first-response [text-message]
  (respond-to-baobao text-message))

(defn respond-text-to-chat [{{type :type} :chat :as message}]
  (if-let [res (find-first-response (:text message))]
    res
    (when (= type "private") default-answer)))
