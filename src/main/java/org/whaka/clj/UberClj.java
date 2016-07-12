package org.whaka.clj;

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

	public static final String CORE = "clojure.core";

	public static final IFn require = Clojure.var(CORE, "require");
	public static final IFn deref = Clojure.var(CORE, "deref");

	private UberClj() {
	}

	/**
	 * Require a name-space identified by the specified symbol
	 */
	public static void require(String ns) {
		require.invoke(Clojure.read(ns));
	}

	public static Var var(String ns, String name) {
		return (Var) Clojure.var(ns, name);
	}

	/**
	 * Dereference specified value into an unknown type
	 * 
	 * @see #deref(IFn, Class)
	 */
	public static Object deref(IFn val) {
		return deref.invoke(val);
	}

	/**
	 * Dereference specified value and try to cast it to the type of the specified class.
	 * 
	 * @see #deref(IFn)
	 */
	public static <T> T deref(IFn val, Class<T> type) {
		return type.cast(deref.invoke(val));
	}
}
