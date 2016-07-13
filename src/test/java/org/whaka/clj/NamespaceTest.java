package org.whaka.clj;

public class NamespaceTest {

	public static void main(String[] args) throws Exception {
		
		String ns = "org.whaka.clj.test-ns";
		UberClj.require(ns);
		System.out.println(UberClj.fn(ns, "test-fn").invoke(303));
		System.out.println(UberClj.value(ns, "test-var"));
	}
}
