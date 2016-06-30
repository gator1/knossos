(ns knossos.op-test
  (:require [clojure.test :refer :all]
            [clojure.java.io :as io]
            [clojure.edn :as edn]
            [knossos.op :refer :all]
            [knossos.linear :as linear]
            [knossos.linear.report :refer :all]
            [knossos.model :refer [register cas-register]]
            [knossos.core-test :as ct])
            (:import (java.io PushbackReader))
            )

(comment
(deftest op-test
  (testing "op test"
    (is (= [] [])))))

       (comment println "GatorsHistory type: " (type history))

(comment
(deftest cas-failure
  (testing "op  Gators test"
    (println "cas-failure starts")
    (let [history  (ct/read-history "history.edn")
          model    (cas-register nil)
          analysis (linear/analysis model history)]
      (println "GatorsModel:\n" model
            println "\nGatorsHistory:\n" history
            render-analysis! history analysis "cas-history.svg")))))

(def sample-map {:foo "bar" :bar "foo"})

(defn convert-sample-map-to-edn
  "Converting a Map to EDN"
  []
  ;; yep, converting a map to EDN is that simple"
  (prn-str sample-map))

(defn read-history
  "Reads a history file of [process type f value] tuples, or maps."
  [f]
  ( with-open [r (PushbackReader. (io/reader f))]
       ( let [h (->> (repeatedly #(edn/read {:eof nil} r))
                   (take-while identity)
                   (println)
                   (map (fn [op]
                      (;( println "map loop" op)
                       ( if ( map? op)
                            op
                            (;( println "op" op)
                            (let [[process type f value] op]
                            {:process process
                            :type    type
                            :f       f
                            :value   value}))))))
         vec
       )]
       ( println "h" h (type h))
       h
       ))
  )

(deftest cas-failure
  (testing "op test"
    (println "op dummy starts")
    (println "Let's convert a map to EDN: " (convert-sample-map-to-edn))
    (println "Now let's covert the map back: " (edn/read-string (convert-sample-map-to-edn)))

    ( let  [ history (read-history "foobar.edn")]
      ( println history)
      )
    (is (= [] [])))

  (comment (testing "op  cas test"
    ((println "cas-failure starts")
      (let [history (ct/read-history "history.edn")
            model (cas-register nil)
            analysis (linear/analysis model history)]
             (println "GatorsModel:\n" model
                  println "\nGatorsHistory:\n" history))
      ))))

