(ns clojusc.config.unified.util
  (:require
   [clojure.edn :as edn]
   [clojure.java.io :as io]
   [clojure.string :as string]
   [taoensso.timbre :as log]))

(defn read-edn-resource
  [filename]
  (with-open [rdr (io/reader (io/resource filename))]
    (edn/read (new java.io.PushbackReader rdr))))

(defn deep-merge
  "Merge maps recursively."
  [& maps]
  (if (every? #(or (map? %) (nil? %)) maps)
    (apply merge-with deep-merge maps)
    (last maps)))
