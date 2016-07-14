<img src="https://lh4.googleusercontent.com/-WkzYBFHWhgI/AAAAAAAAAAI/AAAAAAAAAAY/YDtbm6FIolc/photo.jpg" width="80" alt="Clojure!" />

# whaka-clj-interop
Clojure interoperability tool.
An API to usabilitilize (Is it a word? We can make it one! We have the technology!) working with Java-Clojure interop.

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

#### org.whaka.clj.UberClj
Loading vars and then dereferencing them is quite tedious, so of course there's a better way:
```
CljCore.require("my.namespace");
String str = (String) UberClj.var("my.namespace", "my-str-var").deref();
```
Method `UberClj.var` casts its result to `clojure.lang.Var`, so you can access its methods directly.
But it's still not pretty enough, so you may do the same thing like this:
```
CljCore.require("my.namespace");
String str = UberClj.value("my.namespace", "my-str-var");
```
When you want to acquire a function, you may do it like this:
```
CljCore.require("my.namespace");
IFn fn = UberClj.fn("my.namespace", "my-fn");
```
Functions are values in clojure, so you could do the same thing with `value` method,
but `fn` seems more readable in this case, and it's also automatically casts result to `clojure.lang.IFn`:
```
CljCore.require("my.namespace");
String result = UberClj.fn("my.namespace", "my-fn").invoke(42);
// vs:
String result2 = UberClj.<IFn>value("my.namespace", "my-fn").invoke(42);
```
Tho, there's already a much better way to call a function:
```
CljCore.require("my.namespace");
String result = UberClj.call("my.namespace", "my-fn", 42);
Long sum = UberClj.call("clojure.core", "+", 10, 20, 30);
```

## Kanban
https://waffle.io/Whaka-project/whaka-clj-interop/join

## CI
https://travis-ci.org/Whaka-project/whaka-clj-interop/pull_requests

## Clojure ki te mate!
<img src="http://i.imgur.com/CR3RFUl.jpg" width="400" alt="Whaka!" />
