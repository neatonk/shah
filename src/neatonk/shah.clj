(ns neatonk.shah
  (:use [neatonk.shah.io :only [as-bytes]])
  (:import [java.security MessageDigest]))

(defn digest
  "Return a byte-array representing the hash-value of bytes using the
  hashing algorithm named."
  [message algo]
  (.digest (MessageDigest/getInstance algo)
           (as-bytes message)))

(defn hex-encode
  "Returns a zero-padded, unsigned, hex-encoded string of no less then
  'width' chars given the input 'x'. Width defaults to the number of
  bytes in x."
  ([x]
     (let [width (* 2 (count x))]
       (hex-encode x width)))
  ([x width]
     (format (str "%0" (int width) "x")
             (BigInteger. 1 (as-bytes x)))))

(defn hash-string
  "Hash the input 'x' using the algorithm indicated. Returns a
  zero-padded, hex-encoded hash string. Input must be a byte-array,
  String, or java.io.File. See java.security.MessageDigest for
  supported algorithms"
  [x algo]
  (hex-encode (digest x algo)))


;; for your convenience...
;; TODO: generate convenience functions for supported algorithms.

(defn md5sum
  "Compute the MD5 message digest of the input 'x'. Input can be a
  byte-array, String, or java.io.File. Returns a zero-padded,
  32-character, hex-encoded string."
  [x] (hash-string x "MD5"))

(defn sha256sum
  "Compute the SHA-256 message digest of the input 'x'. Input can be a
  byte-array, String, or java.io.File. Returns a zero-padded,
  64-character, hex-encoded string."
  [x] (hash-string x "SHA-256"))
