(ns advent-of-code.day-4
  (:require [clojure.string :as str])
     (:gen-class))

(defn create-number
  [num]
  {:value (Integer/parseInt num)
   :seen  false})
(defn process-row
  [row]
  (let [split (str/split row #"  *")]
    (->> split
        (filter #(not= % ""))
        (map create-number))))

(defn process-board
  [board]
  (reduce #(conj %1 (process-row %2)) [] board))

(defn process-boards
  [boards]
  (loop [boards boards
         parsed []]
    (if (= (count boards) 0)
      parsed
      (recur (drop 6 boards)
             (conj parsed (process-board (take 5 boards)))))))

(defn parse-input
  []
  (let [lines (str/split (slurp "input.txt") #"\n")
        input (first lines)
        boards (vec (drop 2 lines))]
    {:input (str/split input #",")
     :boards (process-boards boards)}))

(defn check-value
  [input value]
  (if (= (:value value) input)
    (assoc value :seen true)
    value))

(defn check-row
  [input row]
  (map #(check-value input %) row))

(defn check-board
  [input board]
  (map #(check-row input %) board))

(defn validate-row
  [row]
  (reduce #(if %1 (:seen %2) false) true row))

(defn validate-column-reducer
  [previous row i]
  (if previous (:seen (get (vec row) i)) false))

(defn validate-column
  [board i]
  (reduce #(validate-column-reducer %1 %2 i) true board))


(defn get-diagnal
 [board i]
 (get-in board [i i :seen]))


(defn winning-diagnal
  [board]
  (reduce #(if %1 (get-diagnal board %2) false) true (range 2)))

(defn winning-column
  [board]
  (reduce #(if %1 true (validate-column board %2)) false (range 5)))

(defn winning-row
  [board]
  (reduce #(if %1 true (validate-row %2)) false board))

(defn filter-fn
  [board]
  (or (winning-row board) (winning-column board) (winning-diagnal board)))

(defn find-winner
  [boards]
  (filter filter-fn  boards))

(defn find-loser
  [boards]
  (filter #(not (or (winning-row %) (winning-column %) (winning-diagnal %)))  boards))

(defn process-input
  [input boards]
  (map #(check-board input %) boards))

(defn get-row-value
  [row]
  (reduce #(+ %1 (if (:seen %2) 0 (:value %2))) 0 row))

(defn get-uncalled
  [board]
  (reduce #(+ %1 (get-row-value %2)) 0 board))


(defn part-1
  [input boards]
  (loop [input input
         boards boards
         winner-found false
         winning-board {}]
    (if winner-found
      winning-board
      (let [i (Integer/parseInt (first input))
            new-boards (process-input i boards)
            winner? (find-winner new-boards)]
      (recur (rest input) new-boards (= 1 (count winner?)) {:board (first winner?) :number i})))))

(defn part-2
  [input boards]
  (loop [input input
         boards boards
         loser-found false
         losing-board {}]
    (if loser-found
      losing-board
      (let [i (Integer/parseInt (first input))
            new-boards (process-input i boards)
            losers? (find-loser new-boards)]
      (recur (rest input) losers? (= 1 (count losers?)) {:board (first losers?) :number i})))))

(clojure.pprint/pprint
 (let [parsed (parse-input)
       boards (:boards parsed)
       input (:input parsed)
       winner (part-1 input boards)
       loser (part-2 input boards)
       l (part-1 input [(:board loser)])
       l-board (:board l)
       l-number (:number l)
       board (:board winner)
       number (:number winner)]
   (println (* l-number (get-uncalled l-board)))
   (* number (get-uncalled board))))
