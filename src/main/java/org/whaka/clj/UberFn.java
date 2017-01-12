package org.whaka.clj;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import clojure.lang.AFn;
import clojure.lang.PersistentVector;

/**
 * <p>This class allows you to build a clojure function using java lambdas.
 *
 * <p>Clojure functions may have multiple arities still being a single object,
 * so this class also allows you to register different function bodies
 * for different arities from 0 to 21. Note that arity 21 has a vararg
 * array as the last argument.
 *
 * <p>To register a function body you can either call a specific methods
 * like {@link #on0(Supplier)}, {@link #on1(Function)}, {@link #on3(F3)}, and so on,
 * or call a generic method {@link #on(int, Object)}
 *
 * <p>When you call a specific methods `on#` it is designed (but not required)
 * to specify a lambda as the argument. For first three arities classic java
 * functional interfaces are used ({@link Supplier}, {@link Function}, and {@link BiFunction}),
 * but for higher arities custom interfaces used, like {@link F3}, {@link F4}, {@link F5},
 * and so on, all the way to {@link F21}. All this interfaces have a single method `call`
 * and differ only in number of generics and `call` method arguments.
 *
 * <p>When you specify a lambda as a body for an arity, you may specify
 * types of its arguments, since argument interfaces have generics,
 * but be aware that these are <b>weak generics</b>, meaning that
 * you will be able to call your function with any arguments you like,
 * but {@link ClassCastException} will be thrown if they don't satisfy
 * expected types. This is exactly the way clojure works.
 *
 * <p><b>Note:</b> that when you call generic method {@link #on(int, Object)}
 * the type of the function body object MUST be the same as in specific `on#` method
 * for the same arity that was specified as the first argument.
 *
 * <p>Instances of this class are <b>immutable</b>!
 * So every time you call one of the `on*` methods - new instance if returned,
 * and the old one is as usable as it were.
 *
 * <p>The only way to acquire an initial instance of this class
 * is to call {@link #empty()} method, that will return an empty function
 * that does not have any implemenations for any arity.
 *
 * <p>Class also allows you to create recursive implementations.
 * See {@link #withSelf(Function)} method.
 *
 * <p>Class implements {@link GenericIFn} interface,
 * so usability `_invoke` methods with weak generics may be called.
 *
 * @see #empty()
 * @see #on0(Supplier)
 * @see #on1(Function)
 * @see #on2(BiFunction)
 * @see #on3(F3)
 * @see #on(int, Object)
 * @see #withSelf(Function)
 */
@SuppressWarnings({"hiding", "TypeParameterHidesVisibleType", "WeakerAccess"})
public final class UberFn extends AFn implements GenericIFn {

	private static final UberFn EMPTY = new UberFn();
	
	private final Object[] impls = new Object[22];

	private UberFn() {
	}

	/**
	 * @return empty function with no arities implemented.
	 * It will throw an exception on any attempt to invoke it.
	 */
	public static UberFn empty() {
		return EMPTY;
	}

	/**
	 * <p>This method allows you to create a recursive multi-arity function.
	 *
	 * <p>As an argument you ought to provide a function that takes an instance
	 * of the {@link GenericIFn} function and returns an instance of this class,
	 * i.e. a result function.
	 *
	 * <p><b>Note:</b> you are not allowed to use the argument {@link GenericIFn}
	 * right in the body of the specified function, for it is not yet available!
	 * Instead you are expected to use it from second-level lambdas that you specify
	 * as arity-implementations for the result instance. Example:
	 *
	 * <pre>
	 *     // You can create a function that calls another of its own arities
	 *     UberFn fn = UberFn.withSelf(self ->
	 *         UberFn.empty()
	 *           .on1((Integer a) -> self.invoke(a, 10))
	 *           .on2((Integer a, Integer b) -> a + b));
	 *
	 *     System.out.println(fn.invoke(20));     // 30
	 *     System.out.println(fn.invoke(20, 20)); // 40
	 *
	 *     // Or you can create a fully recursive function
	 *     UberFn fac = UberFn.withSelf(self ->
	 *         UberFn.empty()
	 *           .on1((Integer x) -> self.invoke(a, 1L))
	 *           .on2((Integer x, Long res) -> x < 2 ? res
	 *               : self.invoke(x - 1, res * x)));
	 *
	 *     System.out.println(fac.invoke(5)); // 120
	 *     System.out.println(fac.invoke(6)); // 720
	 *
	 *     // But this is WRONG:
	 *     UberFn fn2 = UberFn.withSelf(self -> {
	 *         self.invoke(); // throws IllegalStateException
	 *         return UberFn.empty()
	 *           .on0(() -> 42);
	 *       });
	 *
	 *     // You cannot call argument `self` function, since
	 *     // you haven't yet returned the function to be called
	 * </pre>
	 *
	 * @param f the function that ought to produce the result of this method
	 * @return the result of calling the specified function with its result as its argument
	 */
	public static UberFn withSelf(Function<GenericIFn, UberFn> f) {
		GenericIFnWrapper del = new GenericIFnWrapper();
		del.setDelegateFn(f.apply(del));
		return (UberFn) del.getDelegateFn();
	}

	/**
	 * <p>Create new instance with 0-arity implemented.
	 * When result function will be invoked with no arguments
	 * it will delegate to the specified supplier.
	 *
	 * <pre>
	 *     UberFn fn = UberFn.empty()
	 *         .on0(() -> "Hello!");
	 *
	 *     System.out.println(fn.invoke()); // Hello!
	 * </pre>
	 *
	 * @param f arity implementation
	 * @return new instance of the {@link UberFn} class
	 * @see #on(int, Object)
	 * @see #on1(Function)
	 * @see #on2(BiFunction)
	 * @see #on3(F3)
	 */
	public <Z> UberFn on0(Supplier<Z> f) {
		return createNewFn(0, f);
	}

	/**
	 * <p>Create new instance with 1-arity implemented.
	 * When result function will be invoked with a single argument
	 * it will delegate to the specified function.
	 *
	 * <pre>
	 *     UberFn fn = UberFn.empty()
	 *         .on1((Long x) -> x * x);
	 *
	 *     System.out.println(fn.invoke(8L)); // 64
	 *
	 *     // Note: it would throw an exception
	 *     // if we hadn't specified the 'L' part of the long!
	 * </pre>
	 *
	 * @param f arity implementation
	 * @return new instance of the {@link UberFn} class
	 * @see #on(int, Object)
	 * @see #on0(Supplier)
	 * @see #on2(BiFunction)
	 * @see #on3(F3)
	 */
	public <A,Z> UberFn on1(Function<A,Z> f) {
		return createNewFn(1, f);
	}

	/**
	 * <p>Create new instance with 2-arity implemented.
	 * When result function will be invoked with two arguments
	 * it will delegate to the specified bi-function.
	 *
	 * <pre>
	 *     UberFn fn = UberFn.empty()
	 *         .on2((String s, Long x) -> s.toUpperCase() + x);
	 *
	 *     System.out.println(fn.invoke("qwe", 8L)); // QWE8
	 *
	 *     // Note: it would throw an exception
	 *     // if we hadn't specified the 'L' part of the long!
	 * </pre>
	 *
	 * @param f arity implementation
	 * @return new instance of the {@link UberFn} class
	 * @see #on(int, Object)
	 * @see #on0(Supplier)
	 * @see #on1(Function)
	 * @see #on3(F3)
	 */
	public <A,B,Z> UberFn on2(BiFunction<A,B,Z> f) {
		return createNewFn(2, f);
	}

	/**
	 * Create new instance with 3-arity implemented.
	 * When result function will be invoked with three arguments
	 * it will delegate to the specified function.
	 *
	 * <pre>
	 *     UberFn fn = UberFn.empty()
	 *         .on2((String s, Long x) -> s.toUpperCase() + x)
	 *         .on3((Integer a, Integer b, Integer c) -> a * 64 + b * 8 + c);
	 *
	 *     System.out.println(fn.invoke("qwe", 8L)); // QWE8
	 *     System.out.println(fn.invoke(1, 2, 3)); // 83
	 *
	 *     // Note: it would throw an exception
	 *     // if we hadn't specified the 'L' part of the long!
	 * </pre>
	 *
	 * @param f arity implementation
	 * @return new instance of the {@link UberFn} class
	 * @see #on(int, Object)
	 * @see #on0(Supplier)
	 * @see #on1(Function)
	 * @see #on4(F4)
	 */
	public <A,B,C,Z> UberFn on3(F3<A,B,C,Z> f) {
		return createNewFn(3, f);
	}

	/**
	 * @see #on3(F3)
	 */
	public <A,B,C,D,Z> UberFn on4(F4<A,B,C,D,Z> f) {
		return createNewFn(4, f);
	}

	/**
	 * @see #on3(F3)
	 */
	public <A,B,C,D,E,Z> UberFn on5(F5<A,B,C,D,E,Z> f) {
		return createNewFn(5, f);
	}

	/**
	 * @see #on3(F3)
	 */
	public <A,B,C,D,E,F,Z> UberFn on6(F6<A,B,C,D,E,F,Z> f) {
		return createNewFn(6, f);
	}

	/**
	 * @see #on3(F3)
	 */
	public <A,B,C,D,E,F,G,Z> UberFn on7(F7<A,B,C,D,E,F,G,Z> f) {
		return createNewFn(7, f);
	}

	/**
	 * @see #on3(F3)
	 */
	public <A,B,C,D,E,F,G,H,Z> UberFn on8(F8<A,B,C,D,E,F,G,H,Z> f) {
		return createNewFn(8, f);
	}

	/**
	 * @see #on3(F3)
	 */
	public <A,B,C,D,E,F,G,H,I,Z> UberFn on9(F9<A,B,C,D,E,F,G,H,I,Z> f) {
		return createNewFn(9, f);
	}

	/**
	 * @see #on3(F3)
	 */
	public <A,B,C,D,E,F,G,H,I,J,Z> UberFn on10(F10<A,B,C,D,E,F,G,H,I,J,Z> f) {
		return createNewFn(10, f);
	}

	/**
	 * @see #on3(F3)
	 */
	public <A,B,C,D,E,F,G,H,I,J,K,Z> UberFn on11(F11<A,B,C,D,E,F,G,H,I,J,K,Z> f) {
		return createNewFn(11, f);
	}

	/**
	 * @see #on3(F3)
	 */
	public <A,B,C,D,E,F,G,H,I,J,K,L,Z> UberFn on12(F12<A,B,C,D,E,F,G,H,I,J,K,L,Z> f) {
		return createNewFn(12, f);
	}

	/**
	 * @see #on3(F3)
	 */
	public <A,B,C,D,E,F,G,H,I,J,K,L,M,Z> UberFn on13(F13<A,B,C,D,E,F,G,H,I,J,K,L,M,Z> f) {
		return createNewFn(13, f);
	}

	/**
	 * @see #on3(F3)
	 */
	public <A,B,C,D,E,F,G,H,I,J,K,L,M,N,Z> UberFn on14(F14<A,B,C,D,E,F,G,H,I,J,K,L,M,N,Z> f) {
		return createNewFn(14, f);
	}

	/**
	 * @see #on3(F3)
	 */
	public <A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,Z> UberFn on15(F15<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,Z> f) {
		return createNewFn(15, f);
	}

	/**
	 * @see #on3(F3)
	 */
	public <A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Z> UberFn on16(F16<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Z> f) {
		return createNewFn(16, f);
	}

	/**
	 * @see #on3(F3)
	 */
	public <A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,Z> UberFn on17(F17<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,Z> f) {
		return createNewFn(17, f);
	}

	/**
	 * @see #on3(F3)
	 */
	public <A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,Z> UberFn on18(F18<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,Z> f) {
		return createNewFn(18, f);
	}

	/**
	 * @see #on3(F3)
	 */
	public <A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,Z> UberFn on19(F19<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,Z> f) {
		return createNewFn(19, f);
	}

	/**
	 * @see #on3(F3)
	 */
	public <A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,Z> UberFn on20(F20<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,Z> f) {
		return createNewFn(20, f);
	}

	/**
	 * @see #on3(F3)
	 */
	public <A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,Z> UberFn on21(F21<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,Z> f) {
		return createNewFn(21, f);
	}

	/**
	 * <p>Create new instance with specified arity implemented.
	 * When result function will be invoked with two arguments
	 * it will delegate to the specified function.
	 *
	 * <p><b>Note:</b> arity implementation specified as generic object
	 * MUST have the same type as the argument of the specific `on#` method
	 * with the same arity as specified.
	 *
	 * @param function arity implementation
	 * @return new instance of the {@link UberFn} class
	 * @see #on0(Supplier)
	 * @see #on1(Function)
	 * @see #on3(F3)
	 */
	public UberFn on(int arity, Object function) {
		Class<?> expectedFunctionType = getExpectedFunctionType(arity);
		if (!expectedFunctionType.isInstance(function) && function != null)
			throw new IllegalArgumentException(
				String.format("For arity [%d] expected function of type: %s", arity, expectedFunctionType));
		return createNewFn(arity, function);
	}

	private static Class<?> getExpectedFunctionType(int arity) {
		switch (arity) {
			case 0:  return Supplier.class;
			case 1:  return Function.class;
			case 2:  return BiFunction.class;
			case 3:  return F3.class;
			case 4:  return F4.class;
			case 5:  return F5.class;
			case 6:  return F6.class;
			case 7:  return F7.class;
			case 8:  return F8.class;
			case 9:  return F9.class;
			case 10: return F10.class;
			case 11: return F11.class;
			case 12: return F12.class;
			case 13: return F13.class;
			case 14: return F14.class;
			case 15: return F15.class;
			case 16: return F16.class;
			case 17: return F17.class;
			case 18: return F18.class;
			case 19: return F19.class;
			case 20: return F20.class;
			case 21: return F21.class;
		}
		throw new IllegalArgumentException("Illegal function arity: " + arity);
	}

	@Override
	@SuppressWarnings("rawtypes")
	public Object invoke() {
		return ((Supplier)getImpl(0)).get();
	}
	
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object invoke(Object arg1) {
		return ((Function)getImpl(1)).apply(arg1);
	}
	
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object invoke(Object arg1, Object arg2) {
		return ((BiFunction)getImpl(2)).apply(arg1,arg2);
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object invoke(Object arg1, Object arg2, Object arg3) {
		return ((F3)getImpl(3)).call(arg1,arg2,arg3);
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4) {
		return ((F4)getImpl(4)).call(arg1,arg2,arg3,arg4);
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5) {
		return ((F5)getImpl(5)).call(arg1,arg2,arg3,arg4,arg5);
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6) {
		return ((F6)getImpl(6)).call(arg1,arg2,arg3,arg4,arg5,arg6);
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7) {
		return ((F7)getImpl(7)).call(arg1,arg2,arg3,arg4,arg5,arg6,arg7);
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
			Object arg8) {
		return ((F8)getImpl(8)).call(arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8);
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
			Object arg8, Object arg9) {
		return ((F9)getImpl(9)).call(arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9);
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
			Object arg8, Object arg9, Object arg10) {
		return ((F10)getImpl(10)).call(arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9,arg10);
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
			Object arg8, Object arg9, Object arg10, Object arg11) {
		return ((F11)getImpl(11)).call(arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9,arg10,arg11);
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
			Object arg8, Object arg9, Object arg10, Object arg11, Object arg12) {
		return ((F12)getImpl(12)).call(arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9,arg10,arg11,arg12);
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
			Object arg8, Object arg9, Object arg10, Object arg11, Object arg12, Object arg13) {
		return ((F13)getImpl(13)).call(arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9,arg10,arg11,arg12,arg13);
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
			Object arg8, Object arg9, Object arg10, Object arg11, Object arg12, Object arg13, Object arg14) {
		return ((F14)getImpl(14)).call(arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9,arg10,arg11,arg12,arg13,arg14);
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
			Object arg8, Object arg9, Object arg10, Object arg11, Object arg12, Object arg13, Object arg14,
			Object arg15) {
		return ((F15)getImpl(15)).call(arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9,arg10,arg11,arg12,arg13,arg14,arg15);
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
			Object arg8, Object arg9, Object arg10, Object arg11, Object arg12, Object arg13, Object arg14,
			Object arg15, Object arg16) {
		return ((F16)getImpl(16)).call(arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9,arg10,arg11,arg12,arg13,arg14,arg15,arg16);
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
			Object arg8, Object arg9, Object arg10, Object arg11, Object arg12, Object arg13, Object arg14,
			Object arg15, Object arg16, Object arg17) {
		return ((F17)getImpl(17)).call(arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9,arg10,arg11,arg12,arg13,arg14,arg15,arg16,arg17);
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
			Object arg8, Object arg9, Object arg10, Object arg11, Object arg12, Object arg13, Object arg14,
			Object arg15, Object arg16, Object arg17, Object arg18) {
		return ((F18)getImpl(18)).call(arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9,arg10,arg11,arg12,arg13,arg14,arg15,arg16,arg17,arg18);
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
			Object arg8, Object arg9, Object arg10, Object arg11, Object arg12, Object arg13, Object arg14,
			Object arg15, Object arg16, Object arg17, Object arg18, Object arg19) {
		return ((F19)getImpl(19)).call(arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9,arg10,arg11,arg12,arg13,arg14,arg15,arg16,arg17,arg18,arg19);
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
			Object arg8, Object arg9, Object arg10, Object arg11, Object arg12, Object arg13, Object arg14,
			Object arg15, Object arg16, Object arg17, Object arg18, Object arg19, Object arg20) {
		return ((F20)getImpl(20)).call(arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9,arg10,arg11,arg12,arg13,arg14,arg15,arg16,arg17,arg18,arg19,arg20);
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
			Object arg8, Object arg9, Object arg10, Object arg11, Object arg12, Object arg13, Object arg14,
			Object arg15, Object arg16, Object arg17, Object arg18, Object arg19, Object arg20, Object... args) {
		return ((F21)getImpl(21)).call(arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9,arg10,arg11,arg12,arg13,arg14,arg15,arg16,arg17,arg18,arg19,arg20,args);
	}

	private Object getImpl(int arity) {
		Object impl = impls[arity];
		if (impl == null)
			throwArity(arity);
		return impl;
	}
	
	private UberFn createNewFn(int arity, Object impl) {
		UberFn newFn = new UberFn();
		System.arraycopy(impls, 0, newFn.impls, 0, impls.length);
		newFn.impls[arity] = impl;
		return newFn;
	}
	
	public interface F3<A,B,C,Z> { Z call(A a, B b, C c); }
	public interface F4<A,B,C,D,Z> { Z call(A a, B b, C c, D d); }
	public interface F5<A,B,C,D,E,Z> { Z call(A a, B b, C c, D d, E e); }
	public interface F6<A,B,C,D,E,F,Z> { Z call(A a, B b, C c, D d, E e, F f); }
	public interface F7<A,B,C,D,E,F,G,Z> { Z call(A a, B b, C c, D d, E e, F f, G g); }
	public interface F8<A,B,C,D,E,F,G,H,Z> { Z call(A a, B b, C c, D d, E e, F f, G g, H h); }
	public interface F9<A,B,C,D,E,F,G,H,I,Z> { Z call(A a, B b, C c, D d, E e, F f, G g, H h, I i); }
	public interface F10<A,B,C,D,E,F,G,H,I,J,Z> { Z call(A a, B b, C c, D d, E e, F f, G g, H h, I i, J j); }
	public interface F11<A,B,C,D,E,F,G,H,I,J,K,Z> { Z call(A a, B b, C c, D d, E e, F f, G g, H h, I i, J j, K k); }
	public interface F12<A,B,C,D,E,F,G,H,I,J,K,L,Z> { Z call(A a, B b, C c, D d, E e, F f, G g, H h, I i, J j, K k, L l); }
	public interface F13<A,B,C,D,E,F,G,H,I,J,K,L,M,Z> { Z call(A a, B b, C c, D d, E e, F f, G g, H h, I i, J j, K k, L l, M m); }
	public interface F14<A,B,C,D,E,F,G,H,I,J,K,L,M,N,Z> { Z call(A a, B b, C c, D d, E e, F f, G g, H h, I i, J j, K k, L l, M m, N n); }
	public interface F15<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,Z> { Z call(A a, B b, C c, D d, E e, F f, G g, H h, I i, J j, K k, L l, M m, N n, O o); }
	public interface F16<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Z> { Z call(A a, B b, C c, D d, E e, F f, G g, H h, I i, J j, K k, L l, M m, N n, O o, P p); }
	public interface F17<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,Z> { Z call(A a, B b, C c, D d, E e, F f, G g, H h, I i, J j, K k, L l, M m, N n, O o, P p, Q q); }
	public interface F18<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,Z> { Z call(A a, B b, C c, D d, E e, F f, G g, H h, I i, J j, K k, L l, M m, N n, O o, P p, Q q, R r); }
	public interface F19<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,Z> { Z call(A a, B b, C c, D d, E e, F f, G g, H h, I i, J j, K k, L l, M m, N n, O o, P p, Q q, R r, S s); }
	public interface F20<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,Z> { Z call(A a, B b, C c, D d, E e, F f, G g, H h, I i, J j, K k, L l, M m, N n, O o, P p, Q q, R r, S s, T t); }
	public interface F21<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,Z> { Z call(A a, B b, C c, D d, E e, F f, G g, H h, I i, J j, K k, L l, M m, N n, O o, P p, Q q, R r, S s, T t, U[] u); }
	
	public static void main(String[] args) {
		
		// 3 arity function
		UberFn fn = UberFn.empty()
				.on0(() -> "qwe")
				.on1((String s) -> s.toUpperCase())
				.on2((String s, Object o) -> s + o);
		
		System.out.println(fn.invoke());
		System.out.println(fn.invoke("zzz"));
		System.out.println(fn.invoke("zzz", 42));

		// function calling other arity on self
		UberFn fn2 = UberFn.withSelf(self -> {
			return UberFn.empty()
					.on1((Long a) -> self.invoke(a, 42L))
					.on2((Long a, Long b) -> a + b);
		});
		
		System.out.println(fn2.invoke(10L, 20L));
		System.out.println(fn2.invoke(10L));
		
		// recursive function
		UberFn fn3 = UberFn.withSelf(self -> {
			return UberFn.empty()
					.on1((Long x) -> self.invoke(x, 1L))
					.on2((Long x, Long res) -> x < 2 ? res : self.invoke(x - 1, res * x));
		});
		
		Long res = fn3._invoke(6L);
		System.out.println(res);
		
		// calling clj
		PersistentVector v = CljCore.NAMESPACE.call("mapv", fn, UberClj.vector("qwe", "rty", "qaz"));
		System.out.println(v);
	}
}
