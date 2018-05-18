# mockazonica

Library to replace all of [amazonica](http://github.com/mcohen01/amazonica) with mocked functions.

## Usage

To replace only `amazonica.aws.s3/put-object` and `amazonica.aws.s3/get-object` with your versions, and stub out all other amazonica functions with a version that constantly throws a `java.lang.Error`.

```clojure
(ns your-test-ns
  (:require [mockazonica.core :refer [mock-amazonica!]]))
  
(mock-amazonica! :redefs {'amazonica.aws.s3/put-object (fn [& args] ,,,)
                          'amazonica.aws.s3/get-object (fn [& args] ,,,)})
```
