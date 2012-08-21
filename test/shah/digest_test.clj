(ns shah.digest-test
  (:require
   [clojure.test :refer [deftest testing is are use-fixtures]]
   [clojure.test.generative :refer [defspec]]
   [shah.test-helper :refer
    [once-fixture run-generative-tests hexchar?]]
   [shah.test-generators :refer [random-bytes]]
   [shah.digest :refer [md5sum sha256sum]]))

(use-fixtures :once once-fixture)

;; a test to run all generative tests
(deftest generative-tests
  (is (run-generative-tests 'shah.digest-test)))

;; md5sum tests

(deftest md5sum-test
  (testing "md5sum - selected inputs..."
    (are [input hash] (= (md5sum input) hash)
         ;;empty byte-array
         (byte-array 0)
         "d41d8cd98f00b204e9800998ecf8427e"

         ;;one byte with a value of zero
         (byte-array 1 (byte 0))
         "93b885adfe0da089cdf634904fd59f71"

         ;;one byte with a value the maximum value
         (byte-array 1 (byte (Byte/MAX_VALUE)))
         "83acb6e67e50e31db6ed341dd2de1595"

         ;;one byte with a value the minimum value
         (byte-array 1 (byte (Byte/MIN_VALUE)))
         "8d39dd7eef115ea6975446ef4082951f"

         ;;sixteen bytes evenly from min to max
         (byte-array 16 (map byte (range -128 127 (/ 255 16))))
         "bae2506ee8bfdf45c87a119e4f31d988"

         ;;one thousand bytes with the same value
         (byte-array 1000 (byte 42))
         "793e21e2f9447906af9c7ad6ec964128")))

(defspec md5sum-test-gen
  md5sum
  [^{:tag `random-bytes} bytes]
  (is (string? %)
      "is a string")
  (is (every? hexchar? %)
      "is hex-encoded")
  (is (= 32 (count %))
      "is 32 characters wide")
  (is (not (.startsWith % "-"))
      "is unsigned"))


;; sha256sum tests

(deftest sha256sum-test
  (testing "sha256sum - selected inputs..."
    (are [input hash] (= (sha256sum input) hash)
         ;;empty byte-array
         (byte-array 0)
         (str "e3b0c44298fc1c149afbf4c8996fb924"
              "27ae41e4649b934ca495991b7852b855")

         ;;one byte with a value of zero
         (byte-array 1 (byte 0))
         (str "6e340b9cffb37a989ca544e6bb780a2c"
              "78901d3fb33738768511a30617afa01d")

         ;;one byte with the maximum value
         (byte-array 1 (byte (Byte/MAX_VALUE)))
         (str "620bfdaa346b088fb49998d92f19a7ea"
              "f6bfc2fb0aee015753966da1028cb731")

         ;;one byte with the minimum value
         (byte-array 1 (byte (Byte/MIN_VALUE)))
         (str "76be8b528d0075f7aae98d6fa57a6d3c"
              "83ae480a8469e668d7b0af968995ac71")

         ;;sixteen bytes evenly from min to max
         (byte-array 16 (map byte (range -128 127 (/ 255 16))))
         (str "8fe0370a4455db6d3cf97cb2da420058"
              "1ffe3f197c83832294e27411c2321ebd")

         ;;one thousand bytes with the same value
         (byte-array 1000 (byte 42))
         (str "86f88b283dd6aa52d173fcbed559fda6"
              "cafc29b4b631597ca1ab0580764b162a"))))


(defspec sha256sum-test-gen
  sha256sum
  [^{:tag `random-bytes} bytes]
  (is (string? %)
      "is a string")
  (is (every? hexchar? %)
      "is hex-encoded")
  (is (= 64 (count %))
      "is 64 characters wide")
  (is (not (.startsWith % "-"))
      "is unsigned"))