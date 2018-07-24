(ns hiccup2markdown.core
  "Converts **normalized** hiccup datastructure to markdown."
  (:require [clojure.string :as string]
            [clojure.walk :as walk]))


;; # General walking code
;; Recursively processes the whole normalized hiccup tree. First converting
;; every node to text, then concatenating the results.

(declare process-tag)

(defn pre-fn
  "Process nodes in markup tree. If sequential, than it is a tag and should
  be somhow converted to markdown. Otherwise it is probbaly just some string
  and shuld be left as is."
  [n]
  (if (sequential? n)
      (process-tag n)
      (identity n)))

(defn post-fn
  "After converting parts of markup to markdown, join using the strings"
  [n]
  (string/join " " n))

(def walk-down
  "Process children of a tag with the two above functions recursively."
  (partial walk/walk pre-fn post-fn))


;; # Specific html->markdown
;; Logic to specifically convert individual html tags reprezented as normalized
;; hiccup datastructure to markdown.

(defn p
  [attrs children]
  (str (walk-down children) "\n\n"))

(defn em
  [attrs children]
  (str "*" (walk-down children) "*"))

(defn s
  [attrs children]
  (str "~~" (walk-down children) "~~"))

(defn strong
  [attrs children]
  (str "**" (walk-down children) "**"))

(defn a
  [attrs children]
  (str "(" (walk-down children) ")[" (:href attrs) "]"))

(defn img
  [attrs children]
  (str "![" (:alt attrs) "](" (:src attrs) ")"))

(defn hr
  [attrs children]
  "\n---\n")

(defn ommit
  "For tags in source that should not throw, but should not be interpreted.
  They are basically ommited and forgotten. But their children are preserved."
  [attrs children]
  (walk-down children))

(defn process-tag
  [[tag attrs & children]]
  (case tag
        :p (p attrs children)
        :div (p attrs children)
        :span (ommit attrs children)
        :blockquote (ommit attrs children) ;FIX ME
        :cite (ommit attrs children)       ;FIX ME
        :iframe (ommit attrs children)       ;FIX ME
        :small (ommit attrs children)       ;FIX ME
        :ul (ommit attrs children)       ;FIX ME
        :ol (ommit attrs children)       ;FIX ME
        :li (ommit attrs children)       ;FIX ME
        :script (ommit attrs children)       ;FIX ME
        :del (ommit attrs children)       ;FIX ME
        :br (ommit attrs children)
        :object (ommit attrs children) ;FIX ME  
        :param (ommit attrs children)  ;FIX ME
        :embed (ommit attrs children)  ;FIX ME
        :em (em attrs children)
        :s (s attrs children)
        :strong (strong attrs children)
        :a (a attrs children)
        :img (img attrs children)
        :hr (hr attrs children)
        (throw #?(:clj (Exception. (str "UNKNOWN TAG " tag))
                  :cljs (js/Error. (str "UNKNOWN TAG " tag))))))

