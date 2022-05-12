(ns pulasan-bot.message.baobao)


(defn respond-to-baobao [text]
  (when-let [[match] (re-seq #"宝宝(.+)不\1" text)]
    (str (second match) "!!!")))