(ns wonka.core
  (:gen-class))

(defn getContent [name]
  (with-open [reader (clojure.java.io/reader name)]
    (reduce conj [] (line-seq reader))))

(defn getHeights [content]
  (vec (map (fn [i] (Integer/parseInt i)) content)))

(defn calcChoc [walls]
  (let
    [indexed (map-indexed (fn [idx itm] [itm idx]) walls)
     peaks (vec (filter (fn [[itm]] (> itm 0)) indexed))
     lastPeak (second (last peaks))

     height
     (fn [wallIndex] (nth walls wallIndex))

     streak
     (fn [streakBegHeight currWall streakAccumulator]
       (letfn [(isLastPeak [] (>= currWall lastPeak))
               (higherWall [] (>= (height currWall) streakBegHeight))
               (endOfStreak [] (or (isLastPeak) (higherWall)))
               (streakHeight [] (* (min streakBegHeight (height currWall)) (count streakAccumulator)))
               (streakMap [] {:result (max 0 (reduce - (streakHeight) streakAccumulator)) :endIndex currWall})
               (addWallToStreak [] (conj streakAccumulator (height currWall)))]

         (if (endOfStreak)
           (streakMap)
           (recur streakBegHeight (+ currWall 1) (addWallToStreak)))))

     iter
     (fn [currWall, total]
       (letfn [(endOfWalls [] (>= currWall (- (count walls) 1)))
               (streakFromCurrWall [] (streak (height currWall) (+ currWall 1) (vector)))
               (isPeak [] (> (height currWall) 0))]

         (if (endOfWalls)
           total
           (if (isPeak)
             (let [{result :result streakEndIndex :endIndex} (streakFromCurrWall)]
               (recur streakEndIndex (+ total result)))
             (recur (+ currWall 1) total)))))]

    (iter 0 0)))

(def content (getContent "./resources/input.txt"))

(defn -main
  "Let's see what this thingy does"
  [& args]
  (println (calcChoc (getHeights content))))

