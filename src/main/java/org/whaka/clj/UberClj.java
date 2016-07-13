package org.whaka.clj;

import java.util.NoSuchElementException;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import clojure.lang.Var;

/**
 * <p>This class may be used as main "entry point" to the interop API.
 * 
 * @see #require(String)
 * @see #deref(IFn)
 * @see #deref(IFn, Class)
 */
public final class UberClj {

	private UberClj() {
	}

	/**
	 * Require a name-space identified by the specified symbol and return the name-space object.
	 */
	public static CljNamespace require(String ns) {
		return new CljNamespace(ns);
	}

	/**
	 * Read var in the specified name-space, at the specified name
	 */
	public static Var var(String ns, String name) {
		return (Var) Clojure.var(ns, name);
	}

	/**
	 * Read and deref var
	 */
	public static Object value(String ns, String name) {
		Var var = var(ns, name);
		if (var.isBound())
			return var(ns, name).deref();
		throw new NoSuchElementException("No bound value found for: " + ns + "/" + name);
	}
	
	/**
	 * Read and deref var and cast to the specified type
	 */
	public static <T> T value(String ns, String name, Class<T> type) {
		Var var = var(ns, name);
		if (var.isBound())
			return type.cast(var(ns, name).deref());
		throw new NoSuchElementException("No bound value found for: " + ns + "/" + name);
	}

	/**
	 * Read var, deref it, and cast to a function
	 */
	public static IFn fn(String ns, String name) {
		return (IFn) value(ns, name);
	}
}
