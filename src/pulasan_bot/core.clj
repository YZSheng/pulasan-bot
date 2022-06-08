(ns pulasan-bot.core
  (:require [clojure.core.async :refer [<!!]]
            [clojure.string :as str]
            [environ.core :refer [env]]
            [morse.handlers :as h]
            [morse.polling :as p]
            [morse.api :as t]
            [pulasan-bot.message.core :refer [respond-text-to-chat]]
            [pulasan-bot.service.todoist :refer [get-todoist]]
            [pulasan-bot.command.remind :refer [handle-remind-command]])
  (:gen-class))

(def token (env :telegram-token))
(def todoist-api-token (env :todoist-token))

(def c (atom nil))

#_{:clj-kondo/ignore [:unresolved-symbol]}
(h/defhandler handler

  (h/command-fn "start"
                (fn [{{id :id :as chat} :chat}]
                  (println "Bot joined new chat: " chat)
                  (t/send-text token id "Welcome to pulasan-bot!")))

  (h/command-fn "help"
                (fn [{{id :id :as chat} :chat}]
                  (println "Help was requested in " chat)
                  (t/send-text token id "Help is on the way!")))

  (h/command-fn "remind"
                (fn [{{id :id} :chat text :text}]
                  (println "Remind was requested in" text)
                  (try
                    (handle-remind-command text (get-todoist todoist-api-token))
                    (t/send-text token id "Reminder is added to your Todo-ist list.")
                    (catch IllegalArgumentException e (do
                                                        (t/send-text token id "Sorry but I didn't understand your remind command.")
                                                        (println (str "remind failed for id " id " with message " (.getMessage e)))))
                    (catch Exception e (do
                                         (t/send-text token id "Saving reminder failed.")
                                         (println (str "remind failed for id " id " with error " (.getMessage e))))))))
  (h/message-fn
   (fn [{{id :id} :chat :as message}]
     (try
       (reset! c message)
       (println "Intercepted message: " message)
       (when-let [res (respond-text-to-chat message)]
         (t/send-text token id res))
       (catch Exception e (str "caught exception: " (.getMessage e)))))))

;; start bot in repl
#_{:clj-kondo/ignore [:unresolved-symbol]}
(def channel (p/start token #'handler))

(defn -main
  [& args]
  (when (str/blank? token)
    (println "Please provde token in TELEGRAM_TOKEN environment variable!")
    (System/exit 1))

  (println "Starting the pulasan-bot")
  (<!! channel))

(comment
  ;; stop bot in repl
  (p/stop channel)
  (respond-text-to-chat @c)
  @c)