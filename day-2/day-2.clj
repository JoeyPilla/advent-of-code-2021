(ns advent-of-code.day-2
  (:require [clojure.string :as str])
  (:gen-class))

(defn parse-input
  []
  (str/split (slurp "input.txt") #"\n"))

(defn reducer-1
  [acc row]
  (let [split       (str/split row #" ")
        instruction (first split)
        value       (second split)]
    (cond
      (= instruction "forward") (update acc :horizontal + (Integer/parseInt value))
      (= instruction "up")      (update acc :vertical - (Integer/parseInt value))
      (= instruction "down")    (update acc :vertical + (Integer/parseInt value)))))

(defn part-1
  [lines]
    (reduce #(reducer-1 %1 %2) {:horizontal 0 :vertical 0} lines))

(defn get-value
  [sub]
  (* (get sub :horizontal) (get sub :vertical)))

(defn reducer-2
  [acc row]
  (let [split       (str/split row #" ")
        instruction (first split)
        value       (second split)]
    (cond
      (= instruction "forward") (-> acc
                                    (update :horizontal + (Integer/parseInt value))
                                    (update :vertical + (* (get acc :aim) (Integer/parseInt value))))
      (= instruction "up")      (update acc :aim - (Integer/parseInt value))
      (= instruction "down")    (update acc :aim + (Integer/parseInt value)))))

(defn part-2
  [lines]
    (reduce #(reducer-2 %1 %2) {:horizontal 0 :vertical 0 :aim 0} lines))

(defn day-2
  []
  (let [input (parse-input)]
    (println (get-value (part-1 input)))
    (println (get-value (part-2 input)))))
