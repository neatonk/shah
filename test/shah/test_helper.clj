(ns shah.test-helper
  (:require [clojure.java.io :refer [file]]
            [clojure.test.generative :refer [test-namespaces]]))

;; helpers

(def tmpdir (file "tmp"))

(defn mktemp []
  (.mkdirs tmpdir)
  (file tmpdir (str (gensym))))

(defn mktempd []
  (.mkdirs (file tmpdir (str (gensym)))))

(defn delete-tmpdir []
  (when (.exists tmpdir)
    (doseq [f (reverse (file-seq tmpdir))]
      (.delete f))))

(def hexchar?
  #{\0 \1 \2 \3 \4 \5 \6 \7 \8 \9
    \a \b \c \d \e \f})


;; fixtures

(defn once-fixture [run-tests]
  (do (delete-tmpdir)
      (println "running tests...")
      (run-tests)
      (println "deleting temp directory....")
      (delete-tmpdir)))

(defn run-generative-tests
  "Runs all generative tests in the ns given. Returns true unless one
  of the tests throws an error."
  [ns]
  (let [fs (doall (map deref (test-namespaces ns)))]
    (every? #(not (instance? Throwable %))
            fs)))