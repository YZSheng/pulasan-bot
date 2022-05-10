(ns pulasan-bot.core-test
  (:require [clojure.test :refer :all]
    [pulasan-bot.core :refer :all]))

(deftest baby-questions
  (testing "Answer to baby questions"
    (is (= "臭!!!" (respond-to-baobao "宝宝臭不臭？")))
    (is (= "臭!!!" (respond-to-baobao "宝宝臭不臭？？？？")))
    (is (= "臭!!!" (respond-to-baobao "   宝宝臭不臭")))
    (is (nil? (respond-to-baobao "宝宝臭不香？")))))
