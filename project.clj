(defproject locoroll/mockazonica "0.1.1"
  :description "Tool for mocking out amazonica"
  :url "https://github.com/csm/mockazonica"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/java.classpath "0.2.3"]
                 [org.clojure/tools.namespace "0.3.0-alpha4"]]
  :profiles {:dev {:dependencies [[amazonica "0.3.121"]]}}
  :release-tasks [["vcs" "assert-committed"]
                  ["change" "version"
                   "leiningen.release/bump-version" "release"]
                  ["vcs" "commit"]
                  ["vcs" "tag"]
                  ["deploy" "clojars"]
                  ["change" "version" "leiningen.release/bump-version"]
                  ["vcs" "commit"]
                  ["vcs" "push"]])
