package org.whaka.clj;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import clojure.lang.IPersistentList;
import clojure.lang.ISeq;
import clojure.lang.PersistentHashMap;
import clojure.lang.PersistentHashSet;
import clojure.lang.PersistentList;
import clojure.lang.PersistentVector;
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
	 * 
	 * @throws NoSuchElementException in case no such var is bound
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
	 * 
	 * @throws NoSuchElementException in case no such var is bound
	 */
	public static IFn fn(String ns, String name) {
		return UberClj.<IFn>value(ns, name);
	}
	
	/**
	 * Create a persistent vector from the specified elements
	 * 
	 * @see #vector(Collection)
	 */
	public static PersistentVector vector(Object... elements) {
		return PersistentVector.create(elements);
	}
	
	/**
	 * Create a persistent vector from the specified elements
	 * 
	 * @see #vector(Object...)
	 */
	public static PersistentVector vector(Collection<?> elements) {
		return PersistentVector.create(elements);
	}
	
	/**
	 * Create a persistent list from the specified elements
	 * 
	 * @see #list(Collection)
	 */
	public static IPersistentList list(Object... elements) {
		return PersistentList.create(vector(elements));
	}
	
	/**
	 * <p>Create a persistent list from the specified elements
	 * 
	 * <p><b>Note:</b> the most direct construction will be performed by this method
	 * if you pass it an instance of {@link List}. Otherwise - list will be constructed thru a vector.
	 * 
	 * @see #list(Object...)
	 */
	public static IPersistentList list(Collection<?> elements) {
		return PersistentList.create(
				elements instanceof List ?
						(List<?>) elements : vector(elements));
	}
	
	/**
	 * Create a persistent set (removed duplicated elements)
	 * 
	 * @see #set(Collection)
	 */
	public static PersistentHashSet set(Object... elements) {
		return PersistentHashSet.create(elements);
	}
	
	/**
	 * Create a persistent set (removed duplicated elements)
	 * 
	 * @see #set(Object...)
	 */
	public static PersistentHashSet set(Collection<?> elements) {
		return PersistentHashSet.create(elements);
	}
	
	/**
	 * Create a persistent map from the even number of the specified elements,
	 * where each first element is a key, and each second element is a value.
	 * 
	 * @throws IllegalArgumentException in case odd number of elements is passed
	 * 
	 * @see #map(Collection)
	 */
	public static PersistentHashMap map(Object... elements) {
		assertMapSize(elements.length);
		return PersistentHashMap.create(elements);
	}
	
	/**
	 * Create a persistent map from the even number of the specified elements,
	 * where each first element is a key, and each second element is a value.
	 * 
	 * @throws IllegalArgumentException in case odd number of elements is passed
	 * 
	 * @see #map(Object...)
	 */
	public static PersistentHashMap map(Collection<?> elements) {
		assertMapSize(elements.size());
		return PersistentHashMap.create(elements);
	}
	
	private static void assertMapSize(int size) {
		if ((size & 1) > 0)
			throw new IllegalArgumentException("Map may be constructed only from even number of elements!");
	}
	
	/**
	 * Create an abstract seq from the specified elements
	 * 
	 * @see #seq(Collection)
	 */
	public static ISeq seq(Object... elements) {
		return vector(elements).seq();
	}
	
	/**
	 * Create an abstract seq from the specified elements
	 * 
	 * @see #seq(Object...)
	 */
	public static ISeq seq(Collection<?> elements) {
		return vector(elements).seq();
	}
}
