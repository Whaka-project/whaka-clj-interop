package org.whaka.clj;

import java.util.function.Function;

import clojure.lang.AFn;
import clojure.lang.IFn;
import clojure.lang.ISeq;
import clojure.lang.PersistentVector;

@SuppressWarnings("hiding")
public final class UberFn extends AFn implements GenericIFn {

	private static final UberFn EMPTY = new UberFn();
	
	private final Object[] impls = new Object[22];

	private UberFn() {
	}
	
	public static UberFn empty() {
		return EMPTY;
	}
	
	public static UberFn withSelf(Function<GenericIFn, UberFn> f) {
		DelegatingFn del = new DelegatingFn();
		del.fn = f.apply(del);
		return (UberFn) del.fn;
	}
	
	public <Z> UberFn on0(F0<Z> f) {
		return createNewFn(0, f);
	}
	
	public <A,Z> UberFn on1(F1<A,Z> f) {
		return createNewFn(1, f);
	}
	
	public <A,B,Z> UberFn on2(F2<A,B,Z> f) {
		return createNewFn(2, f);
	}
	
	@Override
	@SuppressWarnings("rawtypes")
	public Object invoke() {
		return ((F0)getImpl(0)).call();
	}
	
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object invoke(Object arg1) {
		return ((F1)getImpl(1)).call(arg1);
	}
	
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object invoke(Object arg1, Object arg2) {
		return ((F2)getImpl(2)).call(arg1,arg2);
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

	private final Object getImpl(int arity) {
		Object impl = impls[arity];
		if (impl == null)
			throwArity(arity);
		return impl;
	}
	
	private final UberFn createNewFn(int arity, Object impl) {
		UberFn newFn = new UberFn();
		System.arraycopy(impls, 0, newFn.impls, 0, impls.length);
		newFn.impls[arity] = impl;
		return newFn;
	}
	
	public static interface F0<Z> { Z call(); }
	public static interface F1<A,Z> { Z call(A a); }
	public static interface F2<A,B,Z> { Z call(A a,B b); }
	public static interface F3<A,B,C,Z> { Z call(A a,B b,C c); }
	public static interface F4<A,B,C,D,Z> { Z call(A a,B b,C c,D d); }
	public static interface F5<A,B,C,D,E,Z> { Z call(A a,B b,C c,D d,E e); }
	public static interface F6<A,B,C,D,E,F,Z> { Z call(A a,B b,C c,D d,E e,F f); }
	public static interface F7<A,B,C,D,E,F,G,Z> { Z call(A a,B b,C c,D d,E e,F f,G g); }
	public static interface F8<A,B,C,D,E,F,G,H,Z> { Z call(A a,B b,C c,D d,E e,F f,G g,H h); }
	public static interface F9<A,B,C,D,E,F,G,H,I,Z> { Z call(A a,B b,C c,D d,E e,F f,G g,H h,I i); }
	public static interface F10<A,B,C,D,E,F,G,H,I,J,Z> { Z call(A a,B b,C c,D d,E e,F f,G g,H h,I i,J j); }
	public static interface F11<A,B,C,D,E,F,G,H,I,J,K,Z> { Z call(A a,B b,C c,D d,E e,F f,G g,H h,I i,J j,K k); }
	public static interface F12<A,B,C,D,E,F,G,H,I,J,K,L,Z> { Z call(A a,B b,C c,D d,E e,F f,G g,H h,I i,J j,K k,L l); }
	public static interface F13<A,B,C,D,E,F,G,H,I,J,K,L,M,Z> { Z call(A a,B b,C c,D d,E e,F f,G g,H h,I i,J j,K k,L l,M m); }
	public static interface F14<A,B,C,D,E,F,G,H,I,J,K,L,M,N,Z> { Z call(A a,B b,C c,D d,E e,F f,G g,H h,I i,J j,K k,L l,M m,N n); }
	public static interface F15<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,Z> { Z call(A a,B b,C c,D d,E e,F f,G g,H h,I i,J j,K k,L l,M m,N n,O o); }
	public static interface F16<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Z> { Z call(A a,B b,C c,D d,E e,F f,G g,H h,I i,J j,K k,L l,M m,N n,O o,P p); }
	public static interface F17<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,Z> { Z call(A a,B b,C c,D d,E e,F f,G g,H h,I i,J j,K k,L l,M m,N n,O o,P p,Q q); }
	public static interface F18<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,Z> { Z call(A a,B b,C c,D d,E e,F f,G g,H h,I i,J j,K k,L l,M m,N n,O o,P p,Q q,R r); }
	public static interface F19<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,Z> { Z call(A a,B b,C c,D d,E e,F f,G g,H h,I i,J j,K k,L l,M m,N n,O o,P p,Q q,R r,S s); }
	public static interface F20<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,Z> { Z call(A a,B b,C c,D d,E e,F f,G g,H h,I i,J j,K k,L l,M m,N n,O o,P p,Q q,R r,S s,T t); }
	public static interface F21<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,Z> { Z call(A a,B b,C c,D d,E e,F f,G g,H h,I i,J j,K k,L l,M m,N n,O o,P p,Q q,R r,S s,T t,U[] u); }
	
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
		PersistentVector v = CljCore.NAMESPACE.call("mapv", UberFn.empty().on1((String s) -> s.toUpperCase()), UberClj.vector("qwe", "rty", "qaz"));
		System.out.println(v);
	}
	
	private static final class DelegatingFn implements GenericIFn {
		
		private IFn fn;

		@Override
		public Object call() throws Exception {
			return fn.call();
		}

		@Override
		public void run() {
			fn.run();
		}

		@Override
		public Object invoke() {
			return fn.invoke();
		}

		@Override
		public Object invoke(Object arg1) {
			return fn.invoke(arg1);
		}

		@Override
		public Object invoke(Object arg1, Object arg2) {
			return fn.invoke(arg1, arg2);
		}

		@Override
		public Object invoke(Object arg1, Object arg2, Object arg3) {
			return fn.invoke(arg1, arg2, arg3);
		}

		@Override
		public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4) {
			return fn.invoke(arg1, arg2, arg3, arg4);
		}

		@Override
		public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5) {
			return fn.invoke(arg1, arg2, arg3, arg4, arg5);
		}

		@Override
		public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6) {
			return fn.invoke(arg1, arg2, arg3, arg4, arg5, arg6);
		}

		@Override
		public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6,
				Object arg7) {
			return fn.invoke(arg1, arg2, arg3, arg4, arg5, arg6, arg7);
		}

		@Override
		public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
				Object arg8) {
			return fn.invoke(arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8);
		}

		@Override
		public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
				Object arg8, Object arg9) {
			return fn.invoke(arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9);
		}

		@Override
		public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
				Object arg8, Object arg9, Object arg10) {
			return fn.invoke(arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10);
		}

		@Override
		public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
				Object arg8, Object arg9, Object arg10, Object arg11) {
			return fn.invoke(arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11);
		}

		@Override
		public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
				Object arg8, Object arg9, Object arg10, Object arg11, Object arg12) {
			return fn.invoke(arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11, arg12);
		}

		@Override
		public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
				Object arg8, Object arg9, Object arg10, Object arg11, Object arg12, Object arg13) {
			return fn.invoke(arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11, arg12, arg13);
		}

		@Override
		public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
				Object arg8, Object arg9, Object arg10, Object arg11, Object arg12, Object arg13, Object arg14) {
			return fn.invoke(arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11, arg12, arg13, arg14);
		}

		@Override
		public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
				Object arg8, Object arg9, Object arg10, Object arg11, Object arg12, Object arg13, Object arg14,
				Object arg15) {
			return fn.invoke(arg15, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11, arg12, arg13, arg14, arg15);
		}

		@Override
		public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
				Object arg8, Object arg9, Object arg10, Object arg11, Object arg12, Object arg13, Object arg14,
				Object arg15, Object arg16) {
			return fn.invoke(arg16, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11, arg12, arg13, arg14, arg15, arg16);
		}

		@Override
		public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
				Object arg8, Object arg9, Object arg10, Object arg11, Object arg12, Object arg13, Object arg14,
				Object arg15, Object arg16, Object arg17) {
			return fn.invoke(arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11, arg12, arg13, arg14, arg15, arg16, arg17);
		}

		@Override
		public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
				Object arg8, Object arg9, Object arg10, Object arg11, Object arg12, Object arg13, Object arg14,
				Object arg15, Object arg16, Object arg17, Object arg18) {
			return fn.invoke(arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11, arg12, arg13, arg14, arg15, arg16, arg17, arg18);
		}

		@Override
		public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
				Object arg8, Object arg9, Object arg10, Object arg11, Object arg12, Object arg13, Object arg14,
				Object arg15, Object arg16, Object arg17, Object arg18, Object arg19) {
			return fn.invoke(arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11, arg12, arg13, arg14, arg15, arg16, arg17, arg18, arg19);
		}

		@Override
		public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
				Object arg8, Object arg9, Object arg10, Object arg11, Object arg12, Object arg13, Object arg14,
				Object arg15, Object arg16, Object arg17, Object arg18, Object arg19, Object arg20) {
			return fn.invoke(arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11, arg12, arg13, arg14, arg15, arg16, arg17, arg18, arg19, arg20);
		}

		@Override
		public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
				Object arg8, Object arg9, Object arg10, Object arg11, Object arg12, Object arg13, Object arg14,
				Object arg15, Object arg16, Object arg17, Object arg18, Object arg19, Object arg20, Object... args) {
			return fn.invoke(arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11, arg12, arg13, arg14, arg15, arg16, arg17, arg18, arg19, arg20, args);
		}

		@Override
		public Object applyTo(ISeq arglist) {
			return fn.applyTo(arglist);
		}
	}
}
