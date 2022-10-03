(ns scramble.core)

(defn scramble?
  "Returns true if possible rearrange the given letters to form the word,
   otherwise returns false"
  [letters word]
  (and (>= (count letters) (count word))
       (let [letters-char-freq (frequencies letters)
             word-char-freq (frequencies word)]
         (every? (fn [[char freq]]
                   (>= (get letters-char-freq char 0)
                       freq))
                 word-char-freq))))
