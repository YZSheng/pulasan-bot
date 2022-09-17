(ns pulasan-bot.service.todoist-test
  (:require [clj-http.client :as client]
            [clojure.test :refer :all]
            [pulasan-bot.command.remind :refer :all]
            [pulasan-bot.domain.todo :as todo]
            [pulasan-bot.service.todoist :refer [get-todoist]]
            [pulasan-bot.test-util :refer [rand-str]]))

(def mock-api-token (rand-str))
(def mock-title (rand-str))

(deftest throws-exception-when-api-token-is-missing
  (is (thrown? IllegalStateException (save (get-todoist nil) 1))))

(deftest throws-exception-when-payload-is-invalid
  (is (thrown? IllegalArgumentException (save (get-todoist mock-api-token) 1))))
(is (thrown? IllegalArgumentException (save (get-todoist mock-api-token) {:name (rand-str)})))


(deftest send-todoist-request-payload
  (with-redefs [client/post (fn [& args] (vec args))]
    (let [expected ["https://api.todoist.com/rest/v1/tasks" {:headers {"Authorization" (str "Bearer " mock-api-token)}
                                                             :content-type :json
                                                             :body (str "{\"content\":\"" mock-title "\",\"description\":\"Added by PulasanBot\",\"due_string\":\"tomorrow\"}")}]]
      (is (= expected (save (get-todoist mock-api-token) {::todo/title mock-title}))))))
