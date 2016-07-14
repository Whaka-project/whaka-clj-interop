(ns org.whaka.clj.fn.uberfn-generator)

(def package "org.whaka.clj.fn")
(def methods-in-ifn 21)

(defn ifn-arg-name [idx]
  (symbol (str "arg" (inc idx))))

(defn ifn-call-arglist [arity]
  (->> (range arity)
       (map ifn-arg-name)
       (clojure.string/join ", ")
       (#(str "(" % ")"))))

(defn generic-ifn-args [arity]
  (for [i (range arity)]
    (if (= (inc i) methods-in-ifn)
      (str "java.lang.Object... " (ifn-arg-name i))
      (str "java.lang.Object " (ifn-arg-name i)))))

(defn generic-ifn-body [arity]
  (str "return (T) invoke" (ifn-call-arglist arity) ";"))

(defn generic-ifn-method [arity]
  {:jelement :method
   :default true
   :generics ['T]
   :return 'T
   :name (str "_invoke" arity)
   :args (generic-ifn-args arity)
   :body (generic-ifn-body arity)
   :suppress "unchecked"})

(def generic-ifn-methods
  (->> (range (inc methods-in-ifn))
       (map generic-ifn-method)))
    
(def generic-ifn-interface
  {:jelement :interface
   :package package
   :name "GenericIFn"
   :extends [clojure.lang.IFn]
   :methods generic-ifn-methods})