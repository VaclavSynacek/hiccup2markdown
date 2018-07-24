(ns hiccup2markdown.tests
  (:require [cljs.test :refer-macros [deftest is are testing run-tests]]
            [hiccup2markdown.core :refer [hiccup->markdown]]))



(deftest examples
  (are [hicc expected] (= (hiccup->markdown hicc) expected)

;; Notice the trailing space after bla and separate one space string between <a>
;; and <img>. If they are not there, they are not added automagically.
[:p {} "bla "
             [:a {:href "http://example.com"} "link"]
             " "
             [:img {:src "img.png" :alt "some image"}]]
"bla (link)[http://example.com] ![some image](img.png)\n\n"

;; Simple test for link inside a paragraph
[:p {} "first paragraph " [:a {:href "some-link"} "with a link"] " and a trailing ."]
"first paragraph (with a link)[some-link] and a trailing .\n\n"


;; Two <p>s in <div> but <div> is not represented in markdown, just wraps
;; two siblings to give common parent node.
[:div {} [:p {} "first paragraph"]
         [:p {} "... and second paragraph"]]
"first paragraph\n\n... and second paragraph\n\n"


))

(enable-console-print!)

(cljs.test/run-tests)

