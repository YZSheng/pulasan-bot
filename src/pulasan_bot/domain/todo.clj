(ns pulasan-bot.domain.todo
  (:require [clojure.spec.alpha :as s]))

(s/def ::title string?)
(s/def ::deadline #(instance? java.time.LocalDate %))
(s/def ::todo (s/keys :req [::title]
                      :opt [::deadline]))
