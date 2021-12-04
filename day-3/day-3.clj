(ns clojure.examples.hello
  (:require [clojure.string :as str])
     (:gen-class))

(defn get-digit
  [line index]
  (Character/digit (get line index) 10))

(defn most-common-at-index
  [lines index]
  (reduce #(+ %1 (get-digit %2 index)) 0 lines))

(defn parse-input
  []
  (str/split (slurp "input.txt") #"\n"))

(defn get-most-common-array
  [lines]
  (reduce #(conj %1 (most-common-at-index lines %2)) [] (range (count (get lines 0)))))

(defn gamma-parser
  [acc lines]
  (if (> acc (/ (count lines) 2)) 1 0))

(defn eps-parser
  [num lines]
  (if (< num (/ (count lines) 2)) 1 0))

(defn get-most-common-bits
  [lines parser]
  (let [one-count (get-most-common-array lines)]
  (reduce #(str %1 (parser %2 lines)) "" one-count)))

(defn day-3-part-1
  []
  (let [input (parse-input)]
    (println (* (Integer/parseInt (get-most-common-bits input gamma-parser) 2)
                (Integer/parseInt (get-most-common-bits input eps-parser) 2)))))

(defn o2-condition
  [line common-arr index total-lines]
  (if (>= (get common-arr index) (/ total-lines 2))
      (= (get line index) \1)
      (= (get line index) \0)))

(defn co2-condition
  [line common-arr index total-lines]
  (if (>= (get common-arr index) (/ total-lines 2))
      (= (get line index) \0)
      (= (get line index) \1)))

(defn filter-lines
  [condition lines index]
  (let [common-arr (get-most-common-array lines)
        total-lines (count lines)]
    (filter #(condition % common-arr index total-lines) lines)))

(defn calc-rating
  [input column-count condition]
    (loop [lines input
           index 0]
      (if (not (= (count lines) 1))
          (recur (vec (filter-lines condition lines index))
                 (mod (+ index 1) column-count))
          (get lines 0))))

(defn calc-o2
  [input column-count]
  (calc-rating input column-count o2-condition))

(defn calc-co2
  [input column-count]
  (calc-rating input column-count co2-condition))

(defn day-3-part-2
  []
  (let [input (parse-input)
        column-count (count (get input 0))]
    (println (* (Integer/parseInt (calc-o2 input column-count) 2)
                (Integer/parseInt (calc-co2 input column-count) 2)))))

(day-3-part-1)
(day-3-part-2)
