(ns com.vsubhuman.clj.test-ns)

(def test-str-qwe "qwe")
(def test-long-42 42)
(def test-int-42 (int 42))
(def test-nil nil)

(def test-ref-str-qwe (ref "qwe"))
(def test-atom-str-qwe (atom "qwe"))
(def test-agent-str-qwe (agent "qwe"))

(defn test-fn-pow2 [x] (* x x))