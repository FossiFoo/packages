(set-env!
  :resource-paths #{"resources"}
  :dependencies '[[cljsjs/boot-cljsjs "0.8.2" :scope "test"]])

(require '[cljsjs.boot-cljsjs.packaging :refer :all])

(def +lib-version+ "0.3.0")
(def +version+ (str +lib-version+ "-0"))

(task-options!
  pom {:project     'cljsjs/elkjs
       :version     +version+
       :description "A layer-based layout algorithm, particularly suited for node-link diagrams with an inherent direction and ports."
       :url         "https://github.com/OpenKieler/elkjs"
       :scm         {:url "https://github.com/OpenKieler/elkjs"}
       :license     {"EPL" "http://www.eclipse.org/legal/epl-v10.html"}})

(deftask package []
  (comp
   (download :url (format "https://unpkg.com/elkjs@%s/lib/elk.bundled.js" +lib-version+))
    (sift :move {#"elk.bundled.js" "cljsjs/development/elk.inc.js"})
    (minify :in "cljsjs/development/elk.inc.js"
            :out "cljsjs/production/elk.min.inc.js")
    (sift :include #{#"^cljsjs"})
    (deps-cljs :name "cljsjs.elkjs")
    (pom)
    (jar)))
