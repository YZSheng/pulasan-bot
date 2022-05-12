(ns pulasan-bot.test-util)

(defn rand-str
  ([]
   (rand-str 8))
  ([len]
   (apply str (take len (repeatedly #(char (+ (rand 26) 65)))))))