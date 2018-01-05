(ns org.bnb.dw.core)

(defrecord Question [question choices])

(defprotocol Quiz
  (verify [question answer]))
