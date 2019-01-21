(ns karabiner-configurator.profiles
  (:require
   [karabiner-configurator.misc :refer :all]
   [karabiner-configurator.data :refer :all]))

(defn generate-profiles [profiles]
  (doseq [[profile-name {:keys [default held sim delay alone]} profile-params] profiles]
    (let [check-if-has-default-profile
          (if default (update-default-profile profile-name))
          sim (or sim (:sim default-profile))
          delay (or delay (:delay default-profile))
          alone (or alone (:alone default-profile))
          held (or held (:held default-profile))])))

(generate-profiles default-profile)

(defn parse-profiles [profiles]
  (massert (map? profiles) "Invalid profiles. Must be a map")
  (generate-profiles [profiles]))
