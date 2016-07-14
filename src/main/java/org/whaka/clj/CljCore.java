package org.whaka.clj;

import clojure.java.api.Clojure;
import clojure.lang.IFn;

/**
 * <p>Wrapper over `clojure.core` namespace.
 * 
 * @see #NAMESPACE
 * @see #require(String)
 * @see #deref(Object)
 */
public final class CljCore {

	/*
	 * This is the first level of bootstrapping.
	 * CljNamespace uses `CljCore.require` in its constructor,
	 * so `require` itself cannot be implemented thru namespace API.
	 */
	
	/**
	 * Name of the `clojure.core` namespace
	 */
	public static final String NAME = "clojure.core";
	
	/**
	 * Function to require a name-space to be loaded
	 * 
	 * @see #require(String)
	 * @see UberClj#require(String)
	 */
	public static final IFn REQUIRE_FN = UberClj.fn(NAME, "require");
	
	/**
	 * The core namespace.
	 */
	public static final CljNamespace NAMESPACE = UberClj.require(NAME);
	
	/**
	 * Function to dereference values from vars, atoms, refs, agents, etc.
	 * 
	 * @see #deref(Object)
	 */
	public static final IFn DEREF_FN = NAMESPACE.fn("deref");
	
	private CljCore() {
	}
	
	/**
	 * Require the namespace with the specified name.
	 */
	public static void require(String ns) {
		REQUIRE_FN.invoke(Clojure.read(ns));
	}
	
	/**
	 * Dereference specified value
	 */
	@SuppressWarnings("unchecked")
	public static <T> T deref(Object val) {
		return (T) DEREF_FN.invoke(val);
	}
}