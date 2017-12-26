(ns dw.core)

(defprotocol Quiz
  (verify [question answer]))
