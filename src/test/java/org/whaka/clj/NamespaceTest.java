package org.whaka.clj;

import clojure.java.api.Clojure;
import clojure.lang.IFn;

public class NamespaceTest {

	public static void main(String[] args) throws Exception {
		
		IFn require = Clojure.var("clojure.core", "require");
		IFn deref = Clojure.var("clojure.core", "deref");
		require.invoke(Clojure.read("org.whaka.clj.test-ns"));
		
		IFn var = Clojure.var("org.whaka.clj.test-ns", "test-var");
		System.out.println(deref.invoke(var));
	}
}
