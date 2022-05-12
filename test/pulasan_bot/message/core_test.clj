(ns pulasan-bot.message.core-test
  (:require [clojure.test :refer :all]
            [pulasan-bot.message.core :refer :all]
            [pulasan-bot.test-util :refer :all]))

(defn- make-message
  ([text]
   (make-message text false))
  ([text private]
   {:message_id 175,
    :from
    {:id 154483657, :is_bot false, :first_name "Yunzhou", :last_name "Sheng", :username "Yunzhou", :language_code "en"},
    :chat {:id 154483657, :first_name "Yunzhou", :last_name "Sheng", :username "Yunzhou", :type (if private "private" "group")},
    :date 1652342334,
    :text text}))

(deftest get-text-response
  (testing "Find the first response in private chat"
    (is (= "A!!!" (respond-text-to-chat (make-message "宝宝A不A?" true))))
    (is (= "I don't do a whole lot ... yet." (respond-text-to-chat (make-message "宝宝A不B?" true))))
    (is (= "I don't do a whole lot ... yet." (respond-text-to-chat (make-message (rand-str) true))))))
