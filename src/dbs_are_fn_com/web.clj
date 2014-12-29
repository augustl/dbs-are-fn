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
       [:link {:href "/stylesheets/screen.css" :media "screen" :rel "stylesheet" :type "text/css"}]
       [:link {:href "/atom.xml" :rel "alternate" :title base-title :type "application/atom+xml"}]]
      [:body
       [:div {:class "site-header"}
        [:div {:class "site-content"}
         [:a.site-title {:href "/"} base-title]
         [:a.site-subtitle {:href "/"} "A blog about functional databases"]]]
       [:div {} page]])))

(defn get-home-page [posts req]
  (layout-page
   [:div {:class "site-content"}
    (list
     (map
      (fn [{:keys [url headers pretty-date]}]
        [:div.listed-post
         [:a {:href url} (:title headers)]
         " "
         [:span.listing-date pretty-date]])
      posts)
     [:p "This blog is served as static HTML, generated using " [:a {:href "https://github.com/magnars/stasis"} "Stasis"] ", a Clojure library. The site is " [:a {:href "https://github.com/augustl/dbs-are-fn.com"} "open source"] "."])]))

(defn layout-post
  [{:keys [get-body headers pretty-date]}]
  (layout-page
   (list
    [:div {:class "post-title"}
     [:div {:class "site-content"}
      [:h1 (:title headers)]]]
    [:div {:class "site-content"}
     [:p.post-timestamp pretty-date]
     [:div.post-body (get-body)]])
   (:title headers)))

(defn get-assets []
  (assets/load-assets "public" [#".*"]))

(defn get-pages []
  (let [posts (->> (post-parser/get-posts "posts")
                   (remove #(contains? (:headers %) :unlistable)))]
    (merge
     {"/" (partial get-home-page posts)}
     (into {} (map (fn [post] [(:url post) (fn [req] (layout-post post))]) posts)))))
