(ns advent-of-code.day-7
  (:gen-class)
  (:require
    [clojure.string :as str]))


(defn parse-input
  []
  (map #(Integer/parseInt %) (str/split (str/trim-newline (slurp "input.txt")) #",")))


(defn abs
  [n]
  (max n (- n)))


(defn average
  [input func]
  (func (double (/ (reduce + input) (count input)))))


(defn triangle-number
  [num]
  (/ (* num (inc num)) 2))


(defn part-1
  [input]
  (let [median (nth (sort input) (/ (count input) 2))]
    (reduce #(+ %1 (abs (- %2 median))) 0 input)))


(defn find-fuel-triangle
  [input average]
  (reduce #(+ %1 (triangle-number (abs (- average %2)))) 0 input))


(defn part-2
  [input]
  (int (min (find-fuel-triangle input (average input #(Math/floor %)))
            (find-fuel-triangle input (average input #(Math/ceil %))))))


(let [input (parse-input)]
  (println (part-1 input) (part-2 input)))
