(ns pulasan-bot.core
  (:require [clojure.core.async :refer [<!!]]
            [clojure.string :as str]
            [environ.core :refer [env]]
            [morse.handlers :as h]
            [morse.polling :as p]
            [morse.api :as t]
            [pulasan-bot.message.baobao :refer [respond-to-baobao]])
  (:gen-class))

(def token (env :telegram-token))

(def c (atom nil))

(h/defhandler handler

  (h/command-fn "start"
                (fn [{{id :id :as chat} :chat}]
                  (println "Bot joined new chat: " chat)
                  (t/send-text token id "Welcome to pulasan-bot!")))

  (h/command-fn "help"
                (fn [{{id :id :as chat} :chat}]
                  (println "Help was requested in " chat)
                  (t/send-text token id "Help is on the way")))

  (h/message-fn
   (fn [{{id :id} :chat :as message}]
     (try
       (reset! c message)
       (println "Intercepted message: " message)
       (t/send-text token id (or (respond-to-baobao (:text message)) "I don't do a whole lot ... yet."))
       (catch Exception e (str "caught exception: " (.getMessage e)))))))

;; start bot in repl
(def channel (p/start token handler))

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
  @c)