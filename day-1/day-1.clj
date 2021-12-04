(ns advent-of-code.day-1
  (:require [clojure.string :as str])
  (:gen-class))

(defn parse-input
  []
  (str/split (slurp "input.txt") #"\n"))

(defn reduce-func
  [old comp]
  (let [v       (vec comp)
        current (get v 1)
        prev    (get v 0)]
    (+ old (if (< (Integer/parseInt prev) (Integer/parseInt current)) 1 0))))

(defn part-1
  [lines]
  (let [temp []]
    (reduce reduce-func 0 (map list lines (rest lines)))))

(defn part-2
  [lines]
  (let [temp []]
    (reduce reduce-func 0 (map list lines (drop 3 lines)))))

(defn day-1
  []
  (let [input (parse-input)]
    (println (part-1 input) (part-2 input))))
