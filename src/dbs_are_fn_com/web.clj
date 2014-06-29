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
       [:title (if (nil? title) base-title (str title " (" base-title ")"))]
       [:link {:href "/stylesheets/screen.css" :media "screen" :rel "stylesheet" :type "text/css"}]
       [:link {:href "/atom.xml" :rel "alternate" :title base-title :type "application/atom+xml"}]]
      [:body
       [:div {:class "site-header"}
        [:div {:class "site-content"}
         [:ul {:class "inline-list"}
          [:li [:span.titlelol base-title]]
          [:li [:span.logolol "fn(world_state" [:sub "1"] ") -> world_state" [:sub "2"]]]
          [:li [:a {:href "/"} "Home"]]
          [:li [:a {:href "/about"} "About"]]]]]
       [:div {:class "site-content site-content-main"} page]])))

(defn get-home-page [posts req]
  (layout-page
   (list
    [:p "A blog about functional databases."]
    (map
     (fn [{:keys [url headers pretty-date]}]
       [:div
        [:a {:href url} (:title headers)]
        " "
        [:span.listing-date pretty-date]])
     posts)
    [:hr]
    [:p "This blog is served as static HTML, generated using " [:a {:href "https://github.com/magnars/stasis"} "Stasis"] ", a Clojure library. The site is " [:a {:href "https://github.com/augustl/dbs-are-fn.com"} "open source"] "."])))

(defn get-about-page [req]
  (layout-page
   (list
    [:p [:strong "DB's are fn"] " is a blog about functional databases, written by " [:a {:href "http://augustl.com"} "August Lilleaas"] "."]
    [:p "Old data is valuable to many domains. Keeping old data shouldn't be your job."]
    [:p "We already use version control and log files. Knowing what the world looked like in the past is useful to many domains. But our databases makes it easy to do destructive updates and deletes. If you want to keep around old data, that's your problem."]
    [:p "A functional database has no destructive updates or deletes. Datomic has a view of the database that doesn't show deleted data, but you can always fetch the database as of two weeks ago. Event Store is append-only and allows you to create views over streams of events so you can go back in time as you please, and query the changes made to your data over time."]
    [:p "This blog is all about functional database evangelism. Too many developers assume a database " [:em "has"] " to be global mutable state, but this is not the case."])
   "About"))

(defn layout-post
  [{:keys [get-body headers]}]
  (layout-page
   (list
    [:h1 (:title headers)]
    [:p.post-timestamp (:date headers)]
    (get-body))
   (:title headers)))

(defn get-assets []
  (assets/load-assets "public" [#".*"]))

(defn get-pages []
  (let [posts (post-parser/get-posts "posts")]
    (merge
     {"/" (partial get-home-page posts)
      "/about" get-about-page}
     (into {} (map (fn [post] [(:url post) (fn [req] (layout-post post))]) posts)))))
