(ns advent-of-code.day-5
  (:gen-class)
  (:require
    [clojure.string :as str]))


(defn handle-cord
  [[x y]]
  {:x (Integer/parseInt x) :y (Integer/parseInt y)})


(defn process-split
  [row]
  (let [[from to] row]
    {:from (handle-cord (str/split to #",")) :to (handle-cord (str/split from #","))}))


(defn split-line
  [row]
  (let [split (str/split row #" -> ")]
    (process-split split)))


(defn parse-input
  []
  (let [lines (str/split (slurp "input.txt") #"\n")]
    (map split-line lines)))


(defn get-max
  [current row]
  {:x (max (:x current) (get-in row [:to :x]) (get-in row [:from :x])) :y (max (:y current) (get-in row [:to :y]) (get-in row [:from :y]))})


(defn create-grid
  [input]
  (let [{x :x y :y} (reduce get-max {:x 0 :y 0} input)]
    (vec (for [i (range 0 (+ 2 y))]
           (vec (for [j (range 0 (+ 2 x))]
                  0))))))


(defn update-position
  [x y grid]
  (update-in grid [y x] inc))


(defn handle-x
  [row grid y]
  (let [to-x (get-in row [:to :x])
        from-x (get-in row [:from :x])]
    (if (< to-x from-x)
      (reduce #(update-position %2 y %1) grid (range to-x (inc from-x)))
      (reduce #(update-position %2 y %1) grid (range from-x (inc to-x))))))


(defn handle-y
  [row grid x]
  (let [to-y (get-in row [:to :y])
        from-y (get-in row [:from :y])]
    (if (< to-y from-y)
      (reduce #(update-position x %2 %1) grid (range to-y (inc from-y)))
      (reduce #(update-position x %2 %1) grid (range from-y (inc to-y))))))


(defn dia-right
  [from to grid]
  (reduce #(update-position (+ (:x from) %2) (+ (:y from) %2) %1) grid (range 0 (inc (- (:y to) (:y from))))))


(defn dia-left
  [from to grid]
  (reduce #(update-position (+ (:x from) %2) (- (:y from) %2) %1) grid (range 0 (inc (- (:x to) (:x from))))))


(defn process-row
  [row grid]
  (cond
    (= (get-in row [:to :x]) (get-in row [:from :x])) (handle-y row grid (get-in row [:to :x]))
    (= (get-in row [:to :y]) (get-in row [:from :y])) (handle-x row grid (get-in row [:to :y]))
    (and (< (get-in row [:to :y]) (get-in row [:from :y])) (< (get-in row [:to :x]) (get-in row [:from :x]))) (dia-right (:to row) (:from row) grid)
    (and (> (get-in row [:to :y]) (get-in row [:from :y])) (> (get-in row [:to :x]) (get-in row [:from :x]))) (dia-right (:from row) (:to row) grid)
    (and (> (get-in row [:to :y]) (get-in row [:from :y])) (< (get-in row [:to :x]) (get-in row [:from :x]))) (dia-left (:to row) (:from row) grid)
    (and (< (get-in row [:to :y]) (get-in row [:from :y])) (> (get-in row [:to :x]) (get-in row [:from :x]))) (dia-left (:from row) (:to row) grid)
    :else grid))


(defn process-input
  [input]
  (reduce #(process-row %2 %1) (create-grid input) input))


(defn count-twos-row
  [row]
  (reduce #(if (<= 2 (get row %2)) (inc %1) %1) 0 (range 0 (count row))))


(defn count-twos-grid
  [grid]
  (reduce #(+ %1 (count-twos-row (get grid %2))) 0 (range 0 (count grid))))


(clojure.pprint/pprint (count-twos-grid (process-input (parse-input))))
