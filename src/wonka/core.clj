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

(defn iter [i, total]
  (letfn
    [(streak [k acc]
       (if (or (>= k lastPeak) (>= (nth heights k) (nth heights i)))
         (list (reduce - (* (min (nth heights i) (nth heights k)) (count acc)) acc) k)
         (recur (+ k 1) (conj acc (nth heights k)))))]
    (if (>= i (- (count heights) 1))
      total
      (if (> (nth heights i) 0)
        (let [[result index] (streak (+ i 1) (vector))]
          (recur index (+ total result)))
        (recur (+ i 1) total)))))

(defn -main
  "Let's see what this thingy does"
  [& args]
  (println (iter 0 0)))

