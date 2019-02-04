(ns clojusc.config.unified.edn
  (:require
   [clojure.string :as string]
   [clojusc.config.unified.common :as common]
   [clojusc.config.unified.util :as util]
   [taoensso.timbre :as log]))

(def config-file "config/clojusc-unified-config/config.edn")

(defn base-data
  ([]
    (base-data config-file))
  ([filename]
    (util/read-edn-resource filename)))

(defn data
  ([]
    (data config-file))
  ([filename]
    (util/deep-merge (base-data filename)
                     (common/props-data)
                     (common/env-data))))
