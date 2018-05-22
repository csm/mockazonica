# mockazonica

[![Clojars Project](https://img.shields.io/clojars/v/locoroll/mockazonica.svg)](https://clojars.org/locoroll/mockazonica)

Library to replace all of [amazonica](http://github.com/mcohen01/amazonica) with mocked functions.

The overall intent is to add mocked versions of functions in your tests, while also ensuring that everything else is stubbed out and throws a java.lang.Error when called. This way if you fail to stub out a function in your tests, you'll get a clear indication of it.

## Usage

```clojure
[locoroll/mockazonica "0.1.2"]
```

To replace only `amazonica.aws.s3/put-object` and `amazonica.aws.s3/get-object` with your versions, and stub out all other amazonica functions with a version that constantly throws a `java.lang.Error`.

```clojure
(ns your-test-ns
  (:require [mockazonica.core :refer [mock-amazonica!]]))
  
(mock-amazonica! :redefs {'amazonica.aws.s3/put-object (fn [& args] ,,,)
                          'amazonica.aws.s3/get-object (fn [& args] ,,,)})
```

You can also pass in a custom `:default-fn`, which should be a function that takes the symbol being stubbed out, and returns the mock function.
