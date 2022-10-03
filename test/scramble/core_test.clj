(ns scramble.core-test
  (:require [clojure.test :refer [deftest testing is]]
            [scramble.core :as sut]))

(deftest scramble?-test
  (testing "possible to form word from letters 1"
    (is (true? (sut/scramble? "rekqodlw" "world"))))
  (testing "possible to form word from letters 2"
    (is (true? (sut/scramble? "cedewaraaossoqqyt" "codewars"))))
  (testing "impossible to form word from letters 1"
    (is (false? (sut/scramble? "katas"  "steak"))))
  (testing "fewer letters than necessary"
    (is (false? (sut/scramble? "kat"  "steak")))))
