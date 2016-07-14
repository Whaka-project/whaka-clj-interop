<img src="https://lh4.googleusercontent.com/-WkzYBFHWhgI/AAAAAAAAAAI/AAAAAAAAAAY/YDtbm6FIolc/photo.jpg" width="80" alt="Clojure!" />

# whaka-clj-interop
Clojure interoperability tool.
An API to usabilitilize (Is it a word? We can make it one! We have the technology!) working with Clojure-Java-interop.

## Examples
#### org.whaka.clj.CljCore
Functions `require` and `deref` are available statically in this class:
```
CljCore.require("my.namespace");

Object strVar = Clojure.var("my.namespace", "my-str-var");
Object longVar = Clojure.var("my.namespace", "my-long-var");

String strValue = CljCore.deref(strVar);
Long longValue = CljCore.deref(longVar);
```
(!) Note: that method `deref` provides `weak` generics that allow you to assign result to any type,
but you will get `ClassCastException` in case the type is incompatible.

## Kanban
https://waffle.io/Whaka-project/whaka-clj-interop/join

## CI
https://travis-ci.org/Whaka-project/whaka-clj-interop/pull_requests

## Clojure ki te mate!
<img src="http://i.imgur.com/CR3RFUl.jpg" width="400" alt="Whaka!" />
