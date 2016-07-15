(ns org.whaka.clj.fn.jenerator
  (:require [clojure.string :as s]))

(defmulti jenerate :jenerate)

(defn- error [& parts]
  (throw (RuntimeException. (apply str parts))))

(defn- join-fn
  ([separator] (join-fn separator "" ""))
  ([separator pre post]
    #(str pre (s/join separator %) post)))

(def join-space
  (join-fn " "))

(def join-comma
  (join-fn ", "))

(def join-generics
  (join-fn ", " "<" ">"))

(def join-arglist
  (join-fn ", " "(" ")"))

(defn generate-type [element]
  (cond
    (string? element) element
    (class? element) (.getName element)
    (sequential? element)
      (str (generate-type (first element))
           (join-generics (map generate-type (rest element))))
    :else
      (error "Illegal type representation: " element)))

(defn generate-modifier [modifier]
  (if (keyword? modifier)
    (name modifier)
    (error "Modifier expected to be a keyword! Actual: " modifier)))

(defn modifiers-str [modifiers]
  (if (empty? modifiers) ""
    (str (->> modifiers (map generate-modifier) join-space) " ")))

(defn extend-str [extends]
  (if (empty? extends) ""
    (str " extends "
      (->> extends (map generate-type) join-comma))))

(defn implements-str [implements]
  (if (empty? implements) ""
    (str " implements "
      (->> implements (map generate-type) join-comma))))

(defn- generate-source-header [package]
  (if (empty? package) ""
    (str
"package " package ";

")))

(defn- generate-classifier-header [modifiers type name extends implements]
  {:pre [(not-empty name)]}
  (str (modifiers-str modifiers)
       type " " name
       (extend-str extends)
       (implements-str implements)))

(defmethod jenerate :interface
  [{:keys [package modifiers name extends methods]
    :or {package "" modifiers [:public]}}]
  (str (generate-source-header package)
       (generate-classifier-header modifiers "interface" name extends [])))