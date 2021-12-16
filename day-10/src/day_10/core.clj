(ns day-10.core
  (:require [clojure.string :as str]))

(defn process-line
  [line]
  (loop [pos 0
         queue []
         c ""]
    (cond
      (not= "" c) false
      (>= pos (count line)) queue
      :else (let [letter (str (get line pos))]
         (cond
           (str/includes? "{[(<" letter) (recur (inc pos) (concat queue letter) "")
           (and (not= "<" (str (last queue))) (= letter ">")) (recur (inc pos) queue letter)
           (and (not= "{" (str (last queue))) (= letter "}")) (recur (inc pos) queue letter)
           (and (not= "[" (str (last queue))) (= letter "]")) (recur (inc pos) queue letter)
           (and (not= "(" (str (last queue))) (= letter ")")) (recur (inc pos) queue letter)
           :else (recur (inc pos) (butlast queue) ""))))))

(defn part2
  [line]
  (reduce #(+ (* %1 5) (cond
                      (= "(" (str %2)) 1
                      (= "[" (str %2)) 2
                      (= "{" (str %2)) 3
                      (= "<" (str %2)) 4)) 0 (reverse line)))

(defn process-lines
  [lines]
  (->> lines
       (map process-line)
       (filter #(not= % false))
       (map part2)
       (sort)))

(let [res (-> "resources/input.txt"
              (slurp)
              (str/split-lines)
              (process-lines))]
  (println  (nth res (/ (dec (count res)) 2))))
