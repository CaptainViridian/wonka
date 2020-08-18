(ns wonka.core-test
  (:require [clojure.test :refer :all]
            [wonka.core :refer :all]))

(def smallList1 [(list 0 2 0 0 2 0) 4])

(deftest getHeights-test
  (testing "Receives a list of strings and returns a list of heights (numbers)")
  (is (= (first smallList1) (getHeights (getContent "./resources/tiny.txt")))))

(def emptyList [(list) 0])
(def smallList2 [(list 0 2 0 0 1 0) 2])
(def smallList3 [(list 0 1 2 0 2 0) 2])
(def smallList4 [(list 0 2 1 0 2 0) 3])
(def slightlyBiggerList [(getHeights (getContent "./resources/small.txt")) 12])
(def mediumList (getHeights (getContent "./resources/medium.txt")))
(def hugeList (getHeights (getContent "./resources/large.txt")))
(def increasingList [(list 1 2 3 4 5 6 70) 0])
(def decreasingList [(reverse (first increasingList)) 0])

(deftest calcChoc-empty-list
  (testing "Returns 0 for empty list")
  (is (= (second emptyList) (calcChoc (first emptyList)))))

(deftest calcChoc-small-list-1
  (testing "Returns 4 for small list 1")
  (is (= (second smallList1) (calcChoc (first smallList1)))))

(deftest calcChoc-small-list-2
  (testing "Returns 2 for small list 2")
  (is (= (second smallList2) (calcChoc (first smallList2)))))

(deftest calcChoc-small-list-3
  (testing "Returns 2 for small list 3")
  (is (= (second smallList3) (calcChoc (first smallList3)))))

(deftest calcChoc-small-list-4
  (testing "Returns 3 for small list 4")
  (is (= (second smallList4) (calcChoc (first smallList4)))))

(deftest calcChoc-not-so-small-list
  (testing "Returns 12 for slightly bigger list")
  (is (= (second slightlyBiggerList) (calcChoc (first slightlyBiggerList)))))

(deftest calcChoc-medium-list
  (testing "Does not Return 0 for medium list")
  (is (not (= 0 (calcChoc mediumList)))))

(deftest calcChoc-huge-list
  (testing "Does not Overflow Stack for huge list")
  (calcChoc hugeList))

(deftest calcChoc-increasing-list
  (testing "Returns 0 for increasing-only list")
  (is (= (second increasingList) (calcChoc (first increasingList)))))

(deftest calcChoc-decreasing-list
  (testing "Returns 0 for decreasing-only list")
  (is (= (second decreasingList) (calcChoc (first decreasingList)))))

(run-tests 'wonka.core-test)