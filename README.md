# shah

Secure one-way hashing

## Usage

```clojure
(use '[clojure.java.io :only [file]])
(use '[neatonk.shah :only [digest md5sum sha256sum]])

(digest "The quick brown fox jumps over the lazy dog" "MD5")
;; => #<byte[] [B@50d7c5>

(spit "path/to/somefile.txt" "The quick brown fox jumps over the lazy dog")
(digest "path/to/somefile.txt" "MD5")
;; => #<byte[] [B@22db19d3>

(md5sum "The quick brown fox jumps over the lazy dog")
(md5sum (file "path/to/somefile.txt"))
;; => "9e107d9d372bb6826bd81d3542a419d6"

(sha256sum "The quick brown fox jumps over the lazy dog")
(sha256sum (file "path/to/somefile.txt"))
;; => "d7a8fbb307d7809469ca9abcb0082e4f8d5651e46d3cdb762d02d0bf37c9e592"
```

## License

Copyright © 2012 Kevin Neaton

Distributed under the Eclipse Public License, the same as Clojure.
