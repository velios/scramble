(ns scramble.server
  (:require [ring.adapter.jetty :as jetty]
            [compojure.core :as compojure]
            [compojure.route :as route]
            [ring.util.response :as response]
            [ring.middleware.params :as params]
            [scramble.core :as scramble]
            [jumblerg.middleware.cors :refer [wrap-cors]]))

(defn handle-scramble-response [letters word]
  (cond
    (empty? letters) "Characters is required"
    (empty? word) "Sample is required"
    :else (if (scramble/scramble? letters word)
            (str "Portion of the characters '" letters "' matches the word '" word "'!")
            (str "No luck! Portion of the characters '" letters "' doesn't match the word '" word "'."))))

(compojure/defroutes routes
  (compojure/GET "/scramble" [letters word]
    (response/response (handle-scramble-response letters word)))
  (compojure/GET "/" []
    (->
     (response/resource-response "public/index.html")
     (response/content-type "text/html")))
  (route/resources "/")
  (route/not-found "Page not found"))

(def handler
  (wrap-cors (params/wrap-params routes) #".*"))

(defn run
  [_opts]
  (jetty/run-jetty handler {:port 3000}))


