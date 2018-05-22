(ns mockazonica.core
  (:require [clojure.java.classpath :as cp]
            [clojure.tools.namespace.find :as ns-find]))

(def ^:private mocked? (atom false))

(defn mock-amazonica!
  "Replace all symbols in amazonica with replacement functions in REDEFS, or with DEFAULT-FN.
  If given, DEFAULT-FN should be a function that accepts a symbol (the symbol of the
  function being called) and which returns a function that accepts (and possibly ignores)
  any number of arguments.

  Returns a map of symbol->previous function."
  [& {:keys [redefs default-fn] :or {default-fn (fn [sym] (fn default-mock [& _] (throw (Error. (str "amazonica function " sym " called but not mocked out")))))}}]
  (when (compare-and-set! mocked? false true)
    (let [classpath (cp/classpath)
          nses (filter #(.startsWith (name %) "amazonica.aws") (ns-find/find-namespaces classpath))
          old-vals (volatile! (transient {}))]
      (doseq [ns nses]
        (try
          (require ns)
          (doseq [[sym value] (ns-publics ns)]
            (let [sym (symbol (name ns) (name sym))]
              (vswap! old-vals (fn [v] (assoc! v sym (var-get value))))
              (alter-var-root value (constantly (or (get redefs sym) (default-fn sym))))))
          (catch Throwable t
            (println "could not mock out" (str ns ":") t))))

      (persistent! @old-vals))))

(defn restore-functions!
  "Restore all functions given in VALS (the return value from mock-amazonica!)."
  [vals]
  (when (compare-and-set! mocked? true false)
    (doseq [[sym value] vals]
      (if-let [v (find-var sym)]
        (alter-var-root v (constantly value))))))