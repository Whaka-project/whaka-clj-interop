(ns org.whaka.clj.fn.jenerator)

(defmulti generate :jelement)

(defn generate-type [element]
  (cond
    (string? element) element
    (class? element) (.getName element)
    (sequential? element)
      (str (generate-type (first element))
           "<"
           (->> (rest element)
                (map generate-type)
                (clojure.string/join ", "))
           ">")))

(defn modifiers-str [modifiers]
  (->> modifiers
       (map name)
       (clojure.string/join " ")))

(defn extend-str [extends]
  (if (empty? extends) ""
    (str "extends "
      (->> extends (map generate-type) (clojure.string/join ", ")))))

(defmethod generate :interface
  [{:keys [package modifiers name extends methods]
    :or {package "" modifiers [:public]}}]
  {:pre [(not-empty name)]}
  (str
"package " package ";

" (modifiers-str modifiers) " interface " name " " (extend-str extends) " {
}"))