(defn get-banner
  []
  (try
    (str
      (slurp "resources/text/banner.txt")
      ;(slurp "resources/text/loading.txt")
      )
    ;; If another project can't find the banner, just skip it.
    (catch Exception _ "")))

(defn get-prompt
  [ns]
  (str "\u001B[35m[\u001B[34m"
       ns
       "\u001B[35m]\u001B[33m λ\u001B[m=> "))

(defproject clojusc/unified-config "0.5.0-SNAPSHOT"
  :description "Unifies configruation from the environment, Java system properties, and/or edn files"
  :url "https://github.com/clojusc/unified-config"
  :license {
    :name "Apache License, Version 2.0"
    :url "http://www.apache.org/licenses/LICENSE-2.0"}
  :exclusions [
    [args4j]
    [io.aviso/pretty]]
  :dependencies [
    [args4j "2.33"]
    [clojusc/results "0.1.0"]
    [clojusc/trifl "0.4.2"]
    [clojusc/twig "0.4.1"]
    [environ "1.1.0"]
    [io.aviso/pretty "0.1.37"]
    [io.forward/yaml "1.0.9"]
    [org.clojure/clojure "1.10.0"]
    [org.clojure/core.rrb-vector "0.0.14"]]
  :aot [clojure.tools.logging.impl]
  :profiles {
    :ubercompile {
      :aot :all
      :source-paths ["test"]}
    :security {
      :plugins [
        [lein-nvd "1.0.0"]]
      :source-paths ^:replace ["src"]
      :nvd {
        :suppression-file "resources/security/false-positives.xml"}
      :exclusions [
        ;; The following are excluded due to their being flagged as a CVE
        [com.google.protobuf/protobuf-java]
        [com.google.javascript/closure-compiler-unshaded]]}
    :dev {
      :dependencies [
        [clojusc/system-manager "0.3.0"]
        [org.clojure/java.classpath "0.3.0"]
        [org.clojure/tools.namespace "0.2.11"]]
      :middleware [
        ultra.plugin/middleware]
      :plugins [
        [oubiwann/venantius-ultra "0.5.4-SNAPSHOT"]]
      :source-paths ["dev-resources/src"]
      :repl-options {
        :init-ns clojusc.config.unified.repl
        :prompt ~get-prompt
        :init ~(println (get-banner))}}
    :lint {
      :source-paths ^:replace ["src"]
      :test-paths ^:replace []
      :plugins [
        [jonase/eastwood "0.3.5"]
        [lein-ancient "0.6.15"]
        [lein-kibit "0.1.6"]]}
    :test {
      :dependencies [
        [clojusc/ltest "0.3.0"]]
      :plugins [
        [lein-ltest "0.3.0"]]
      :test-selectors {
        :unit #(not (or (:integration %) (:system %)))
        :integration :integration
        :system :system
        :default (complement :system)}}}
  :aliases {
    ;; Dev & Testing Aliases
    "repl" ["do"
      ["clean"]
      ["repl"]]
    "ubercompile" ["with-profile" "+ubercompile,+security" "compile"]
    "check-vers" ["with-profile" "+lint" "ancient" "check" ":all"]
    "check-jars" ["with-profile" "+lint" "do"
      ["deps" ":tree"]
      ["deps" ":plugin-tree"]]
    "check-deps" ["do"
      ["check-jars"]
      ["check-vers"]]
    "ltest" ["with-profile" "+test,+system,+security" "ltest"]
    ;; Linting
    "kibit" ["with-profile" "+lint" "kibit"]
    "eastwood" ["with-profile" "+lint" "eastwood" "{:namespaces [:source-paths]}"]
    "lint" ["do"
      ["kibit"]
      ;["eastwood"]
      ]
    ;; Security
    "check-sec" ["with-profile" "+security" "do"
      ["clean"]
      ["nvd" "check"]]
    ;; Build tasks
    "build-jar" ["with-profile" "+security" "jar"]
    "build-uberjar" ["with-profile" "+security" "uberjar"]
    "build-lite" ["do"
      ["ltest" ":unit"]]
    "build" ["do"
      ["clean"]
      ["check-vers"]
      ; ["check-sec"]
      ["ltest" ":unit"]
      ["ubercompile"]
      ["build-uberjar"]]
    ;; Installing
    "install" ["do"
      ["clean"]
      ["ubercompile"]
      ["clean"]
      ["install"]]
    ;; Publishing
    "publish" ["with-profile" "+security" "do"
      ["clean"]
      ["build-jar"]
      ["deploy" "clojars"]]})
