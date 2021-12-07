(ns advent-of-code.day-6
  (:require [clojure.string :as str])
     (:gen-class))

(defn parse-input
  []
  (map #(Integer/parseInt %) (str/split (str/trim-newline (slurp "input.txt")) #",")))

(defn collect-input
  [input]
  (reduce #(update %1 %2 inc) [0 0 0 0 0 0 0 0 0] input))

(defn handle-day
  [i arr days]
  (cond
    (= i 0) (-> arr
                (assoc 8 (get days i))
                (assoc 6 (get days i)))
    (= i 7) (update arr 6 #(+ % (get days i)))
    :else (assoc arr (dec i) (get days i))))

(defn do-1-day
  [days f]
  (reduce #(handle-day %2 %1 days) days (range 0 (count days))))

(defn do-x-days
  [x days]
  (reduce #(do-1-day %1 %2) days (range 0 x)))

(defn count-arr
  [arr]
  (reduce + arr))

(println (count-arr (do-x-days 80 (collect-input (parse-input)))))
(println (count-arr (do-x-days 256 (collect-input (parse-input)))))
