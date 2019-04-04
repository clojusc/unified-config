(ns clojusc.config.unified.yaml
  (:require
   [clojure.string :as string]
   [clojusc.config.unified.common :as common]
   [clojusc.config.unified.util :as util]
   [taoensso.timbre :as log]
   [yaml.core :as yaml]))

(def config-file "config/clojusc-unified-config/config.yml")

(defn base-data
  ([]
    (base-data config-file))
  ([filename]
    (util/read-yaml-resource filename)))

(defn data
  ([]
    (data config-file))
  ([filename]
    (util/deep-merge (base-data filename)
                     (common/props-data)
                     (common/env-data))))

(defn data-str
  [yml-str]
  (util/deep-merge (yaml/parse-string yml-str)
                   (common/props-data)
                   (common/env-data)))

(def data-file #'data)
