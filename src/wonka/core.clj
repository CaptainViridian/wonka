(ns wonka.core
  (:gen-class))

(defn getContent []
  (with-open [reader (clojure.java.io/reader "./resources/input.txt")]
    (reduce conj [] (line-seq reader))))

(defn getHeights [content]
  (vec (map (fn [i] (Integer/parseInt i)) content)))

(defn calcChoc [heights]
  (let
    [indexed (map-indexed (fn [idx itm] [itm idx]) heights)
     peaks (vec (filter (fn [[itm]] (> itm 0)) indexed))
     lastPeak (second (last peaks))

     streak
     (fn [i k acc]
       (letfn [(endOfStreak [] (or (>= k lastPeak) (>= (nth heights k) (nth heights i))))
               (streakHeight [] (* (min (nth heights i) (nth heights k)) (count acc)))]
         (if (endOfStreak)
           (list (reduce - (streakHeight) acc) k)
           (recur i (+ k 1) (conj acc (nth heights k))))))

     iter
     (fn [i, total]
       (letfn [(endOfWalls [] (>= i (- (count heights) 1)))
               (streakFromI [] (streak i (+ i 1) (vector)))
               (isPeak [] (> (nth heights i) 0))]
         (if (endOfWalls)
           total
           (if (isPeak)
             (let [[result streakEndIndex] (streakFromI)]
               (recur streakEndIndex (+ total result)))
             (recur (+ i 1) total)))))]

    (iter 0 0)))

(defn -main
  "Let's see what this thingy does"
  [& args]
  (println (calcChoc (getHeights (getContent)))))

