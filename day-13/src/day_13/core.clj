(ns day-13.core
  (:require [clojure.string :as str])
  (:gen-class))

(defn printer [grid]
  (println)
  (println)
  (doseq [line grid]
    (println line))
  (println)
  (println))

(defn grab-input
  []
  (-> (slurp "resources/test-input.txt")
      (str/split #"\n\n")
      (#(map str/split-lines %))))


(defn get-x-y-coords
  [input]
  (-> (map #(str/split % #",") input)
      (#(map (fn [pair]
               (map (fn [number]
                      (Integer/parseInt number))
                    pair))
             %))))

(defn get-max
  [x-y-coords]
  [(reduce #(max %1 (nth %2 0)) 0 x-y-coords)
   (reduce #(max %1 (nth %2 1)) 0 x-y-coords)])


(defn generate-grid
  [x-y-coords]
  (let [[max-x, max-y] (get-max x-y-coords)]
    (into [] (for [y (range 0 (inc max-y))]
               (into [] (for [x (range 0 (inc max-x))]
                          "."))))))

(defn shrink-x
  [grid x]
  (into [] (map #(take x %) grid)))

(defn shrink-y
  [grid y]
  (take y grid))

(defn add-squares
  [x-y-coords grid]
  (loop [coord 0
         grid grid]
    (if (= coord (count x-y-coords))
      grid
      (let [[x y] (into [] (nth x-y-coords coord))]
        (recur (inc coord) (assoc-in grid [y x] "#"))))))

(defn get-folds
  [fold-input]
  (map #(let [[x y] (str/split % #"=")]
          [(str (last x)) (Integer/parseInt y)])
       fold-input))

(defn handle-fold-y
  [grid fold-y]
  (let [new-grid (into [] (for [y (range 0 (count grid))]
                   (into []  (for [x (range 0 (count (get grid y)))]
                              (if (or
                                   (= "#" (get-in grid [(dec (- (count grid) y)) x]))
                                   (= "#" (get-in grid [y x])))
                                "#" ".")))))]
    (into [] (shrink-y new-grid fold-y))))

(defn handle-fold-x
  [grid fold-x]
  (let [new-grid (into [] (for [y (range 0 (count grid))]
                            (into [] (for [x (range 0 (count (get grid y)))]
                                       (if (or
                                            (= "#" (get-in grid [y (dec (- (count (get grid y)) x))]))
                                            (= "#" (get-in grid [y x])))
                                         "#" ".")))))]
    (into [] (shrink-x new-grid fold-x))))

(defn turn-to-vector
  [grid]
  (into [] (map #(into [] %) grid)))

(defn handle-fold
  [grid fold]
  (if (= "y" (first fold))
    (turn-to-vector (handle-fold-y grid (second fold)))
    (turn-to-vector (handle-fold-x grid (second fold)))))

(defn count-dot
  [grid]
  (reduce (fn
            [acc row]
            (+ acc (reduce
                    #(+ (if (= %2 "#") 1 0) %1)
                    0 row)))
          0 grid))


(defn handle-folds
  [grid folds]
  (loop [folds folds
         grid grid]
    (printer grid)
    (if (= 0 (count folds))
      grid
      (recur (drop 1 folds) (handle-fold grid (first folds))))))


(defn -main
  []
(let [input (grab-input)
      grid-input (nth input 0)
      fold-input (nth input 1)
      x-y-coords (get-x-y-coords grid-input)
      grid  (add-squares x-y-coords (generate-grid x-y-coords))
      folds (get-folds fold-input)]
  (printer (handle-folds grid folds))))
