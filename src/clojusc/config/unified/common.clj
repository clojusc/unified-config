(ns clojusc.config.unified.common
  (:require
   [clojure.string :as string]
   [environ.core :as environ]
   [taoensso.timbre :as log]))

(defn parse-kv
  [k v splitter-regex]
  [(mapv keyword (string/split k splitter-regex))
   (try
    (Integer/parseInt v)
    (catch Exception _e
      v))])

(defn normalize-env
  [[k v]]
  (let [key-name (name k)]
    (when
      (or
        (string/starts-with? key-name "cmr-")
        (string/starts-with? key-name "httpd-")
        (string/starts-with? key-name "logging-"))
      (parse-kv key-name v #"-"))))

(defn normalize-prop
  [[k v]]
  (let [key-name (name k)]
    (when-not
      (or
        (string/starts-with? key-name "java")
        (string/ends-with? key-name "class-path"))
      (parse-kv (name k) v #"-"))))

(defn nest-vars
  [acc [ks v]]
  (try
    (assoc-in acc ks v)
    (catch Exception _e
      (log/warn (format (str "Had a problem adding config data key %s (of "
                             "type %s) and value %s (of type %s)")
                        ks
                        (type ks)
                        v (type v)))
      acc)))

(defn props-data
  []
  (->> (#'environ/read-system-props)
       (map normalize-prop)
       (remove nil?)
       (reduce nest-vars {})
       ((fn [x] (log/trace "props-data:" x) x))))

(defn env-data
  []
  (->> (#'environ/read-system-env)
       (map normalize-env)
       (remove nil?)
       (reduce nest-vars {})
       ((fn [x] (log/trace "env-data:" x) x))))
