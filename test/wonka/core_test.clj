(ns wonka.core-test
  (:require [clojure.test :refer :all]
            [wonka.core :refer :all]))

(def smallList1 (list 0 2 0 0 2 0))

(deftest getHeights-test
  (testing "Receives a list of strings and returns a list of heights (numbers)"
    (is (= smallList1 (getHeights (getContent "./resources/tiny.txt"))))))

(def emptyList (list))
(def smallList2 (list 0 2 0 0 1 0))
(def smallList3 (list 0 1 2 0 2 0))
(def smallList4 (list 0 2 1 0 2 0))
(def slightlyBiggerList (getHeights (getContent "./resources/small.txt")))
(def mediumList (getHeights (getContent "./resources/medium.txt")))
(def hugeList (getHeights (getContent "./resources/large.txt")))
(def increasingList (list 1 2 3 4 5 6 70))
(def decreasingList (reverse increasingList))
(def steadyList (list 4 4 4 4 4 4 4))
(def valleyAtEndList (list 5 2 3 1 4 1))

(deftest calcChoc-empty-list
  (testing "Returns 0 for empty list"
    (is (= 0 (calcChoc emptyList)))))

(deftest calcChoc-small-list-1
  (testing "Returns 4 for small list 1"
    (is (= 4 (calcChoc smallList1)))))

(deftest calcChoc-small-list-2
  (testing "Returns 2 for small list 2"
    (is (= 2 (calcChoc smallList2)))))

(deftest calcChoc-small-list-3
  (testing "Returns 2 for small list 3"
    (is (= 2 (calcChoc smallList3)))))

(deftest calcChoc-small-list-4
  (testing "Returns 3 for small list 4"
    (is (= 3 (calcChoc smallList4)))))

(deftest calcChoc-not-so-small-list
  (testing "Returns 12 for slightly bigger list"
    (is (= 12 (calcChoc slightlyBiggerList)))))

(deftest calcChoc-medium-list
  (testing "Does not Return 0 for medium list"
    (is (not (= 0 (calcChoc mediumList))))))

(deftest calcChoc-huge-list
  (testing "Does not Overflow Stack for huge list"
    (calcChoc hugeList)))

(deftest calcChoc-increasing-list
  (testing "Returns 0 for increasing-only list"
    (is (= 0 (calcChoc increasingList)))))

(deftest calcChoc-decreasing-list
  (testing "Returns 0 for decreasing-only list"
    (is (= 0 (calcChoc decreasingList)))))

(deftest calcChoc-steady-list
  (testing "Returns 0 for list with all the same values"
    (is (= 0 (calcChoc steadyList)))))

(deftest calcChoc-valley-at-list-end
  (testing "Returns 6 for list with a valley at the end of the list"
    (is (= 6 (calcChoc valleyAtEndList)))))

(run-tests 'wonka.core-test)