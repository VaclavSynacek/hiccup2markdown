# hiccup2markdown

Converts **normalized** hiccup datastructure to markdown.

[![Clojars Project](https://img.shields.io/clojars/v/org.clojars.vaclavsynacek/hiccup2markdown.svg)](https://clojars.org/org.clojars.vaclavsynacek/hiccup2markdown)

## Overview

Normaly one would not use this conversion. But for migrations of old blog from
wherever to static site generator based on markdown texts, it might be handy to
turn old html somehow to markdown. If for such thing Clojure(Script) is used
and the html happens to be already turned to hiccup, then this might be handy
actually.

Apart from my obscure need to migrate my blog every 10 years to new tech, it also
comes handy in tests testing the markdown-to-html or markdown-to-hiccup (read
opposite) conversion.

## Usage

```clojure
   (:require [hiccup2markdown :as h2m])
   (h2m/hiccup->markdown [:p {} "first paragraph " [:a {:href "some-link"} "with a link"] " and a trailing ."])
   ;; => "first paragraph (with a link)[some-link] and a trailing .\n\n"
```

## License

Copyright © 2018 Vaclav Synacek
BSD License
