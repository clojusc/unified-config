# unified-config

*Unifies configruation from the environment, Java system properties, and/or config files*

[![Build Status][travis-badge]][travis]
[![Dependencies Status][deps-badge]][travis]
[![Open Pull Requests][prs-badge]][prs]

[![Clojars Project][clojars-badge]][clojars]
[![Tag][tag-badge]][tag]

[![Clojure version][clojure-v]](project.clj)

[![][logo]][logo]


## About

A file-based config library that allows for configuration values to
be overridden using Java system properties (i.e, `-D...`) or shell
environment variables.

Supported file types:
* `.edn`
* `.yml`


## Dependencies [&#x219F;](#contents)

* Java
* `lein`


## License [&#x219F;](#contents)

Copyright © 2018, NASA

Copyright © 2019, Clojure-Aided Enrichment Center

Distributed under the Apache License, Version 2.0.


<!-- Named page links below: /-->

[logo]: https://avatars3.githubusercontent.com/u/18177940?s=200&v=4
[travis]: https://travis-ci.org/clojusc/unified-config
[travis-badge]: https://travis-ci.org/clojusc/unified-config.png?branch=master
[deps-badge]: https://img.shields.io/badge/deps%20check-passing-brightgreen.svg
[tag-badge]: https://img.shields.io/github/tag/clojusc/unified-config.svg
[tag]: https://github.com/clojusc/unified-config/tags
[clojure-v]: https://img.shields.io/badge/clojure-1.10.0-blue.svg
[clojars]: https://clojars.org/clojusc/unified-config
[clojars-badge]: https://img.shields.io/clojars/v/clojusc/unified-config.svg
[security-scan-badge]: https://img.shields.io/badge/nvd%2Fsecurity%20scan-passing-brightgreen.svg
[prs]: https://github.com/pulls?utf8=%E2%9C%93&q=is%3Aopen+is%3Apr+org%3Aclojusc
[prs-badge]: https://img.shields.io/badge/Open%20PRs-org-yellow.svg
