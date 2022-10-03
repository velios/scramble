(ns scramble.core
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [reagent.core :as r]
            [reagent.dom :as rd]
            [oops.core :as oops]
            [cljs-http.client :as http]
            [cljs.core.async :refer [<!]]))

(defonce *app-state (r/atom {:msg "Check that a portion of the characters can be rearranged to match the sample"}))

(defn get-node
  [id]
  (. js/document (getElementById id)))

(defn get-request
  [letters word]
  (go (<! (http/get "http://localhost:3000/scramble"
                    {:query-params {"letters" letters
                                    "word" word}}))))

(defn read-response
  [response-chan]
  (go (let [resp (<! response-chan)]
        (reset! *app-state {:msg (:body resp)}))))

(defn- check-action
  [_]
  (let [letters (oops/oget (get-node "letters") "value")
        word (oops/oget (get-node "word") "value")]
    (read-response (get-request letters word))))

(defn input-block
  [id label]
  [:<>
   [:label {:for id} label]
   [:br]
   [:input {:type "text"
            :id id
            :name "id"}]
   [:br]])

(defn app
  []
  [:div.wrapper
   [:div.card
    [:h1 "Scramble?"]
    [:hr]
    [:div
     [input-block "letters" "Letters:"]
     [input-block "word" "Word:"]
     [:br]
     [:button {:on-click check-action}
      "Check"]]
    [:hr]
    [:div (:msg @*app-state)]]])

(defn render
  []
  (rd/render
   [app]
   (get-node "root")))

(defn init
  []
  (render))

(defn reload
  []
  (render))