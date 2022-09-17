(ns pulasan-bot.message.baobao-test
  (:require [clojure.test :refer :all]
            [pulasan-bot.message.baobao :refer :all]))

(deftest baby-questions
  (is (= "肉!!!" (respond-to-baobao "宝宝肉不肉？")))
  (is (= "肉!!!" (respond-to-baobao "宝宝肉不肉？？？？")))
  (is (= "肉!!!" (respond-to-baobao "   宝宝肉不肉")))
  (is (nil? (respond-to-baobao "宝宝肉不香？"))))