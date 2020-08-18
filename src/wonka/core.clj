(ns wonka.core
  (:gen-class))

(defn getContent []
  (with-open [reader (clojure.java.io/reader "./resources/input.txt")]
    (reduce conj [] (line-seq reader))))

(def heights
  (vec (map (fn [i] (Integer/parseInt i)) (getContent))))

(def indexed
  (map-indexed
    (fn [idx itm] [itm idx]) heights))

(def peaks
  (vec (filter (fn [[itm]] (> itm 0)) indexed)))

(def lastPeak (second (last peaks)))

(defn streak
  [i k acc]
  (letfn
    [(endOfStreak [] (or (>= k lastPeak) (>= (nth heights k) (nth heights i))))
     (streakHeight [] (* (min (nth heights i) (nth heights k)) (count acc)))]
    (if (endOfStreak)
      (list (reduce - (streakHeight) acc) k)
      (recur i (+ k 1) (conj acc (nth heights k))))))

(defn iter [i, total]
  (letfn
    [(endOfWalls [] (>= i (- (count heights) 1)))
     (streakFromI [] (streak i (+ i 1) (vector)))
     (isPeak [] (> (nth heights i) 0))]
    (if (endOfWalls)
      total
      (if (isPeak)
        (let [[result streakEndIndex] (streakFromI)]
          (recur streakEndIndex (+ total result)))
        (recur (+ i 1) total)))))

(defn -main
  "Let's see what this thingy does"
  [& args]
  (println (iter 0 0)))

