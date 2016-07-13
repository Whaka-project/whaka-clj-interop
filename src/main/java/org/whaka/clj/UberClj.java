package org.whaka.clj;

import java.util.NoSuchElementException;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import clojure.lang.Var;

/**
 * <p>This class may be used as main "entry point" to the interop API.
 * 
 * @see #require(String)
 * @see #var(String, String)
 * @see #value(String, String)
 * @see #fn(String, String)
 */
public final class UberClj {

	private UberClj() {
	}

	/**
	 * <p>Require a namespace identified by the specified symbol and return the name-space wrapper object.
	 * 
	 * @see CljNamespace
	 */
	public static CljNamespace require(String ns) {
		return new CljNamespace(ns);
	}

	/**
	 * <p>Read var in the specified name-space, at the specified name
	 * 
	 * <p><b>Note:</b> that this method will return the variable itself, and not its value!
	 * `Var` is an entity in clojure that's linked to a namespace and contains a value that change over time.
	 */
	public static Var var(String ns, String name) {
		return (Var) Clojure.var(ns, name);
	}

	/**
	 * Read and deref var
	 */
	@SuppressWarnings("unchecked")
	public static <T> T value(String ns, String name) {
		Var var = var(ns, name);
		if (var.isBound())
			return (T) var(ns, name).deref();
		throw new NoSuchElementException("No bound value found for: " + ns + "/" + name);
	}
	
	/**
	 * <p>Read var, deref it, and cast to a function
	 * 
	 * <p><b>Note:</b> that it's actually equal to calling:
	 * <pre>
	 * 	<code>IFn fn = UberClj.value(ns, name)</code>
	 * </pre>
	 * But calling `fn` is a little more readable, and preferred.
	 */
	public static IFn fn(String ns, String name) {
		return UberClj.<IFn>value(ns, name);
	}
}
