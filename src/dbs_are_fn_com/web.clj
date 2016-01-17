(ns dbs-are-fn-com.web
  (:require [optimus.assets :as assets]
            [hiccup.page :refer [html5]]
            [augusts-fancy-blog-post-parser.core :as post-parser]))

(def base-title "DB's are fn")

(defn layout-page
  ([page] (layout-page page nil))
  ([page title]
     (html5
      [:head
       [:meta {:charset "utf-8"}]
       [:meta {:name "viewport" :content "width=device-width, initial-scale=1"}]
       [:title (if (nil? title) base-title (str title " (" base-title ")"))]
       [:link {:href "/stylesheets/reset.css" :media "screen" :rel "stylesheet" :type "text/css"}]
       [:link {:href "/stylesheets/screen.css" :media "screen" :rel "stylesheet" :type "text/css"}]]
      [:body
       [:div {} page]])))

(defn get-home-page [req]
  (layout-page
   [:div {:class "site-content"}
    [:p "The blog has been decommisioned. All posts are available on my personal blog at " [:a {:href "http://augustl.com"} "augustl.com"] "."]]))


(defn get-assets []
  (assets/load-assets "public" [#".*"]))

(defn get-pages []
  (merge
    {"/" (partial get-home-page)}))
