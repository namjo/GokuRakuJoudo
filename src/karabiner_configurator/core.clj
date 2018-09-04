(ns karabiner-configurator.core
  (:require
   [schema.core :as s]
   [cheshire.core :as json]
   [karabiner-configurator.modifiers :refer :all]
   [karabiner-configurator.misc :refer :all]
   [karabiner-configurator.data :refer :all]
   [karabiner-configurator.layers :as layers]
   [karabiner-configurator.froms :as froms]
   [karabiner-configurator.tos :as tos]
   [karabiner-configurator.rules :as rules]
   [clojure.edn :as edn]))

(def config (load-edn "resources/configurations/test/keytokey.edn"))

(defn update-static-conf
  "update conf-data from reading rules"
  [key conf]
  (if (nn? conf)
    (update-conf-data (assoc conf-data key conf))))

(defn parse
  "parse configuration"
  [conf]
  (let [{:keys [applications devices keyboard-type input-source tos froms modifiers layers simlayers raws main simlayer-threshold]} conf]
    (update-static-conf :applications applications)
    (update-static-conf :devices devices)
    (update-static-conf :keyboard-type keyboard-type)
    (update-static-conf :input-source tos)
    (if (number? simlayer-threshold)
      (update-static-conf :simlayer-threshold simlayer-threshold))
    (parse-modifiers modifiers)
    (layers/parse-layers layers)
    (layers/parse-simlayers simlayers)
    (froms/parse-froms froms)
    (tos/parse-tos tos)
    (json/encode (rules/parse-mains main))))


;; (parse config)

(defn -main
  [])