(ns shah.io-test
  (:require
   [clojure.test :refer [deftest is use-fixtures]]
   [clojure.test.generative :refer [defspec]]
   [shah.test-helper :refer [once-fixture run-generative-tests]]
   [shah.test-generators :refer
    [generated-file random-bytes random-string random-ascii-string
     random-binary-file random-text-file random-ascii-file]]
   [shah.io :refer [as-bytes]]))

(use-fixtures :once once-fixture)

;; a test to run all generative tests
(deftest generative-tests
  (is (run-generative-tests 'shah.io-test)))

;; testing bytes...
(defspec as-bytes-test-gen-bytes
  as-bytes
  [^{:tag `random-bytes} input]
  (is (= input %)))

;; testing strings...
(defspec as-bytes-test-gen-string
  as-bytes
  [^{:tag `random-string} input]
  (is (= (vec (.getBytes input)) (vec %))))

(defspec as-bytes-test-gen-ascii-string
  as-bytes
  [^{:tag `random-ascii-string} input]
  (is (= (vec (.getBytes input)) (vec %))))

;; testing files...
(defspec as-bytes-test-gen-binary-file
  as-bytes
  [^{:tag `random-binary-file} file]
  (is (= (vec (.getBytes (generated-file file)))
         (vec %))))

(defspec as-bytes-test-gen-text-file
  as-bytes
  [^{:tag `random-text-file} file]
  (is (= (vec (.getBytes (generated-file file)))
         (vec %))))

(defspec as-bytes-test-gen-ascii-file
  as-bytes
  [^{:tag `random-ascii-file} file]
  (is (= (vec (.getBytes (generated-file file)))
         (vec %))))