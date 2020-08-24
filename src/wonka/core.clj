(ns wonka.core
  (:gen-class))

(defn getContent [name]
  (with-open [reader (clojure.java.io/reader name)]
    (reduce conj [] (line-seq reader))))

(defn getHeights [content]
  (vec (map (fn [i] (Integer/parseInt i)) content)))

(defn calcChoc [walls]
  (let
    [numberOfWalls (count walls)
     nonEmpty (> numberOfWalls  0)
     lastWall (if nonEmpty (- numberOfWalls 1) 0)
     highestWall (if nonEmpty (first (apply max-key second (map-indexed vector walls))) 0)

     height
     (fn [wallIndex] (nth walls wallIndex))

     streak
     (fn [streakBeg next isLastWall]
       (letfn [(streakBegHeight [] (height streakBeg))
               (higherWall [wall] (>= (height wall) (streakBegHeight)))
               (endOfStreak [wall] (or (isLastWall wall) (higherWall wall)))
               (streakArea [wall accumulator] (* (min (streakBegHeight) (height wall)) (count accumulator)))
               (streakMap [wall accumulator] {:result (max 0 (reduce - (streakArea wall accumulator) accumulator)) :endIndex wall})
               (addWallToStreak [wall accumulator] (conj accumulator (height wall)))

               (iter [currWall streakAccumulator]
                 (if (endOfStreak currWall)
                   (streakMap currWall streakAccumulator)
                   (recur (next currWall) (addWallToStreak currWall streakAccumulator))))]
         (iter (next streakBeg) (vector))))

     calculate
     (fn [start next end]
       (letfn [(endOfWalls [wall] (= wall end))
               (streakFromCurrWall [wall] (streak wall next endOfWalls))
               (isPeak [wall] (> (height wall) 0))
               (iter [currWall total]
                 (if (endOfWalls currWall)
                   total
                   (if (isPeak currWall)
                     (let [{result :result streakEndIndex :endIndex} (streakFromCurrWall currWall)]
                       (recur streakEndIndex (+ total result)))
                     (recur (next currWall) total))))]
         (iter start 0)))

     forward
     (fn [wall] (+ wall 1))

     backwards
     (fn [wall] (- wall 1))

     firstHalf (calculate 0 forward highestWall)
     secondHalf (calculate lastWall backwards highestWall)]
    (+ firstHalf secondHalf)))

(def content (getContent "./resources/large.txt"))

(defn -main
  "Let's see what this thingy does"
  [& args]
  (println (calcChoc (getHeights content))))

