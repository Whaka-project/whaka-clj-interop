package org.whaka.clj;

import clojure.java.api.Clojure;
import clojure.lang.IFn;

public final class CljCore {

	/*
	 * This is the first level of bootstrapping.
	 * CljNamespace uses `CljCore.require` in its constructor,
	 * so `require` itself cannot be implemented thru namespace API.
	 */
	
	/**
	 * Name of the `clojure.core` name-space
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
	 * The core name-space.
	 */
	public static final CljNamespace NAMESPACE = UberClj.require("clojure.core");
	
	/**
	 * Function to dereference values from vars, atoms, refs, agents, etc.
	 */
	public static final IFn DEREF_FN = NAMESPACE.fn("deref");
	
	private CljCore() {
	}
	
	/**
	 * Require the name-space with the specified name.
	 */
	public static void require(String ns) {
		REQUIRE_FN.invoke(Clojure.read(ns));
	}
	
	/**
	 * Dereference specified value into an unknown type
	 * 
	 * @see #deref(IFn, Class)
	 */
	public static Object deref(IFn val) {
		return DEREF_FN.invoke(val);
	}

	/**
	 * Dereference specified value and try to cast it to the type of the specified class.
	 * 
	 * @see #deref(IFn)
	 */
	public static <T> T deref(IFn val, Class<T> type) {
		return type.cast(DEREF_FN.invoke(val));
	}
}
