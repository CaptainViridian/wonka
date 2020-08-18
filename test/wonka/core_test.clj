(ns wonka.core-test
  (:require [clojure.test :refer :all]
            [wonka.core :refer :all]))

(def smallList1 [(list 0 2 0 0 2 0) 4])

(deftest getHeights-test
  (testing "Receives a list of strings and returns a list of heights (numbers)")
  (is (= (first smallList1) (getHeights (getContent "./resources/tiny.txt")))))


(def smallList2 [(list 0 2 0 0 1 0) 2])
(def smallList3 [(list 0 1 2 0 2 0) 2])
(def smallList4 [(list 0 2 1 0 2 0) 3])
(def slightlyBiggerList [(getHeights (getContent "./resources/small.txt")) 12])
(def mediumList [(getHeights (getContent "./resources/medium.txt")) 0])

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
  (testing "Returns 0 for medium list")
  (is (not (= (second mediumList) (calcChoc (first mediumList))))))


(run-tests 'wonka.core-test)