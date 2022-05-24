(ns pulasan-bot.command.remind-test
  (:require [clojure.test :refer :all]
            [pulasan-bot.test-util :refer [rand-str]]
            [pulasan-bot.domain.todo :as todo]
            [pulasan-bot.command.remind :refer [handle-remind-command Todo]]
            [clojure.string :as string]))

(def mock (reify Todo
            (save [_ todo]
              todo)))

(deftest remind-command-handler
  (testing "Extracts content from valid remind command"
    (let [title (rand-str)
          command (str "/remind me to " title)
          expected-todo {::todo/title (string/capitalize title)}]
      (is (= expected-todo (handle-remind-command command mock)))))
  (testing "Handle invalid remind command"
    (let [title (rand-str)
          command (str "/remind" (rand-str) title)]
      (is (thrown? IllegalArgumentException (handle-remind-command command mock))))))

(comment
  (remind-command-handler))