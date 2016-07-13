package org.whaka.clj;

import java.util.NoSuchElementException;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import clojure.lang.IFn;
import clojure.lang.PersistentVector;
import clojure.lang.Var;

/**
 * <p>Usability wrapper around clojure namespace abstraction.
 * 
 * <p>An instance of this class represents a required (loaded) clojure namespace.
 * You may perform operations directly on it.
 * 
 * <p>For example you can acquire its vars using {@link #var(String)} method,
 * or you can directly access var's values using {@link #value(String)} method.
 * 
 * <p>When value is requested - you may easily try to cast to any expected type,
 * by simply inferring the expected type (by assigning, or passing to a method), e.g.:
 * <pre>
 * 	String str = ns.value("str-var");
 * 	"qwe".charAt(ns.value("int-var"));
 * </pre>
 * 
 * <p>If javac can't infer type automatically - you need to help a little:
 * <pre>
 * 	ns.&lt;String&gt;value("str-var").toUpperCase();
 * </pre>
 * 
 * <p>Note that calling value is the same as calling var and then dereferencing it.
 * But var may contain a value that also need to be dereferenced, such as atoms, refs, agents, etc.
 * For this case yo may use {@link #deref(String)} that will not only acquire value of the var,
 * but also ill additionally dereference it:
 * <pre>
 * 	Ref ref = ns.value("str-ref-var");
 * 	String str = (String) ref.deref();
 *  
 * 	// The same as:
 *  
 * 	String str = ns.deref("str-ref-var");
 * </pre>
 * 
 * <p>Clojure functions is same as values, so you can access them the same way:
 * <pre>
 * 	IFn pow2 = ns.value("pow2");
 * 	pow2.invoke(42);
 * </pre>
 * 
 * But it may be more readable to be clear that you want speciically a function:
 * <pre>
 * 	IFn pow2 = ns.fn("pow2");
 * 
 * 	// This way you may call it directly:
 * 
 *	ns.fn("pow2").invoke(42);
 * </pre>
 * 
 * Also method {@link #call(String, Object...)} will allow you
 * to execute a function with varargs and expected result type:
 * <pre>
 * 	Long sum = ns.call("+", 12, 22, 33);
 * </pre>
 * 
 * @see #value(String)
 * @see #fn(String)
 * @see #deref(String)
 * @see #call(String, Object...)
 */
public final class CljNamespace {

	public final String name;

	/**
	 * <p>Create namespace from the specified <b>full</b> name.
	 * 
	 * <p><b>Note:</b> the namespace will be required!
	 */
	public CljNamespace(String name) {
		CljCore.require(name);
		this.name = name;
	}

	/**
	 * <p>Acquire namepace var (not its value)
	 * 
	 * <p><b>Note:</b> var may be unbound!
	 * 
	 * @see UberClj#var(String, String)
	 */
	public Var var(String name) {
		return UberClj.var(this.name, name);
	}

	/**
	 * <p>Acquire var's value
	 * 
	 * @throws NoSuchElementException in case no such var is bound
	 * 
	 * @see #fn(String)
	 * @see UberClj#value(String, String)
	 */
	public <T> T value(String name) {
		return UberClj.<T>value(this.name, name);
	}

	/**
	 * <p>Acquire function value of the specified var
	 * 
	 * @throws NoSuchElementException in case no such var is bound
	 * 
	 * @see #value(String)
	 * @see UberClj#fn(String, String)
	 */
	public IFn fn(String name) {
		return UberClj.fn(this.name, name);
	}

	/**
	 * <p>Acquire var's value AND deref it.
	 * 
	 * <p><b>Note:</b> this function is different from {@link CljCore#deref(Object)}!
	 * Core's deref just calls core function on any specified value.
	 * This function will deref the var acquired by the specified name,
	 * and then ALSO deref the value. There will be an exceptin in case the value is not derefable.
	 * 
	 * @throws NoSuchElementException in case no such var is bound
	 * @throws ClassCastException in case var's value is not derefable
	 */
	public <T> T deref(String name) {
		return CljCore.deref(value(name));
	}

	/**
	 * Call function identified by the specified name with specified arguments.
	 * 
	 * @throws NoSuchElementException in case no such var is bound
	 */
	@SuppressWarnings("unchecked")
	public <T> T call(String name, Object... args) {
		return (T) fn(name).applyTo(PersistentVector.create(args).seq());
	}

	/**
	 * Acquire the function and return a supplier that will call it and cast result
	 */
	@SuppressWarnings("unchecked")
	public <R> Supplier<R> fn0(String name) {
		IFn fn = fn(name);
		return () -> (R) fn.invoke();
	}

	/**
	 * Acquire the function and return java Function that will call it and cast result
	 */
	@SuppressWarnings("unchecked")
	public <A,R> Function<A, R> fn1(String name) {
		IFn fn = fn(name);
		return a -> (R) fn.invoke(a);
	}

	/**
	 * Acquire the function and return java BiFunction that will call it and cast result
	 */
	@SuppressWarnings("unchecked")
	public <A,B,R> BiFunction<A, B, R> fn2(String name) {
		IFn fn = fn(name);
		return (a,b) -> (R) fn.invoke(a,b);
	}
}
