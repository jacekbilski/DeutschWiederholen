(ns dw.core-test
  (:require [clojure.test :refer :all]
            [dw.core :refer :all]))

(deftest a-test
  (testing "FIXME, I fail."
    (is (= 1 1))))

(deftest run-cukes
  (. cucumber.api.cli.Main (main (into-array ["--plugin" "pretty" "--glue" "test" "test/features"]))))
