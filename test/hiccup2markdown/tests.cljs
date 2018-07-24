(ns hiccup2markdown.tests
  (:require [cljs.test :refer-macros [deftest is are testing run-tests]]
            [hiccup2markdown.core :refer [process-tag]]))



(deftest examples
  (are [hicc expected] (= (process-tag hicc) expected)

[:p {} "bla"
             [:a {:href "http://example.com"} "link"]
             [:img {:src "img.png" :alt "some image"}]]
"bla (link)[http://example.com] ![some image](img.png)\n\n"

[:p {} "first paragraph" [:a {:href "some-link"} "with a link"] "and a trailing ."]
"first paragraph (with a link)[some-link] and a trailing .\n\n"

[:div {} [:p {} "first paragraph"]
         [:p {} "... and second paragraph"]]
"first paragraph\n\n... and second paragraph\n\n"


))

(enable-console-print!)

(cljs.test/run-tests)

