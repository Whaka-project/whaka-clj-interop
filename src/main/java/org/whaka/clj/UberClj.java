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

	public static final String CORE_NAMESPACE = "clojure.core";

	public static final IFn REQUIRE_FN = fn(CORE_NAMESPACE, "require");
	public static final IFn DEREF_FN = fn(CORE_NAMESPACE, "deref");

	private UberClj() {
	}

	/**
	 * Require a name-space identified by the specified symbol
	 */
	public static void require(String ns) {
		REQUIRE_FN.invoke(Clojure.read(ns));
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
	 * Read var, deref it, and cast to a function
	 */
	public static IFn fn(String ns, String name) {
		return (IFn) value(ns, name);
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
