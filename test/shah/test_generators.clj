(ns shah.test-generators
  (:require [shah.test-helper :refer [mktemp]]
            [clojure.test.generative.generators :as gen]))


;; strings/bytes

(defn random-bytes []
  (gen/byte-array gen/byte))

(defn random-string []
  (gen/string gen/char))

(defn random-ascii-string []
  (gen/string gen/printable-ascii-char))


;; files

(def generated-files (atom {}))

(defn generated-file [^java.io.File f]
  (let [n (.getName f)]
    (when-let [s (get @generated-files n)]
      (swap! generated-files dissoc n)
      s)))

(defn spit-temp
  "Create a temporary file with the given string as it's contents for
  testing i/o features. Returns a java.io.file object for the new temp
  file. The original contents of the file are cached locally and can
  be retrieved for comparison using #'generated-file."
  [^String s]
  (let [f (mktemp)
        n (.getName f)]
    (swap! generated-files assoc n s)
    (spit f s)
    f))

(defn random-binary-file []
  (let [bytes (random-bytes)]
    (spit-temp (String. bytes))))

(defn random-text-file []
  (spit-temp (random-string)))

(defn random-ascii-file []
  (spit-temp (random-ascii-string)))