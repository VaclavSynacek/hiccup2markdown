(ns hiccup2markdown.core
  "Converts **normalized** hiccup datastructure to markdown."
  (:require [clojure.walk :as walk]))


;; # General walking code
;; Recursively processes the whole normalized hiccup tree. First converting
;; every node to text, then concatenating the results.

(defmulti hiccup->markdown 
  (fn [[tag attrs & children]] 
       tag))

(defn pre-fn
  "Process nodes in markup tree. If sequential, than it is a tag and should
  be somhow converted to markdown. Otherwise it is probbaly just some string
  and should be left as is."
  [n]
  (if (sequential? n)
      (hiccup->markdown n)
      (identity n)))

(defn post-fn
  "After converting parts of markup to markdown, join them"
  [n]
  (apply str n))

(def walk-down
  "Process children of a tag with the two above functions recursively."
  (partial walk/walk pre-fn post-fn))


;; # Specific html->markdown
;; Logic to specifically convert individual html tags represented as normalized
;; hiccup datastructure to markdown.

(defmethod hiccup->markdown :p
  [[tag attrs & children]]
  (str (walk-down children) "\n\n"))

(defmethod hiccup->markdown :em
  [[tag attrs & children]]
  (str "*" (walk-down children) "*"))

(defmethod hiccup->markdown :s
  [[tag attrs & children]]
  (str "~~" (walk-down children) "~~"))

(defmethod hiccup->markdown :strong
  [[tag attrs & children]]
  (str "**" (walk-down children) "**"))

(defmethod hiccup->markdown :a
  [[tag attrs & children]]
  (str "(" (walk-down children) ")[" (:href attrs) "]"))

(defmethod hiccup->markdown :img
  [[tag attrs & children]]
  (str "![" (:alt attrs) "](" (:src attrs) ")"))

(defmethod hiccup->markdown :hr
  [[tag attrs & children]]
  "\n---\n")

(defmethod hiccup->markdown :default
  [[tag attrs & children]]
  "For tags in source that should not throw, but should not be interpreted.
  They are basically ommited and forgotten. But their children are preserved."
  (println "WARNING: unhandled tag <" (name tag) "> in source.")
  (walk-down children))

(defmethod hiccup->markdown :script
  [[tag attrs & children]]
  "To make <script> illegal in input, throw"
   (throw #?(:clj (Exception. (str "<script> in input not allowed"))
             :cljs (js/Error. (str "<script> in input not allowed")))))



