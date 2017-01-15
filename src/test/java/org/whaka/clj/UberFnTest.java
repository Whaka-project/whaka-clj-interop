package org.whaka.clj;

import clojure.lang.ArityException;
import clojure.lang.IFn;
import clojure.lang.PersistentVector;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by vsubhuman on 15.01.2017.
 */
public class UberFnTest {

	private static final CljNamespace namespace = new CljNamespace(TestNames.TEST_NS);

	@Test(expected = ArityException.class)
	public void test_empty_UberFn_cannot_be_called() {
		UberFn.empty().invoke();
	}

	@Test
	public void test_UberFn_with_0_arguments() {
		Supplier<?> sup = () -> "qwe";
		IFn fn = UberFn.empty().on0(sup);
		Object o = fn.invoke();
		Assert.assertEquals("qwe", o);
	}

	@Test
	public void test_UberFn_with_1_argument() {
		Function<String, ?> f = String::toUpperCase;
		IFn fn = UberFn.empty().on1(f);
		Object o = fn.invoke("qwe");
		Assert.assertEquals("QWE", o);
	}

	@Test
	public void test_UberFn_with_2_arguments() {
		BiFunction<String, Integer, ?> f = String::charAt;
		IFn fn = UberFn.empty().on2(f);
		Object o = fn.invoke("qwe", 1);
		Assert.assertEquals('w', o);
	}

	@Test
	public void test_UberFn_with_3_arguments() {
		UberFn.F3<List<?>, Integer, Integer, List<?>> f = List::subList;
		IFn fn = UberFn.empty().on3(f);
		Object o = fn.invoke(Arrays.asList(1,2,3,4,5), 1, 3);
		Assert.assertEquals(Arrays.asList(2,3), o);
	}

	@Test
	public void test_UberFn_with_multiple_arities() {
		IFn fn = UberFn.empty()
			.on0(() -> "qwe")
			.on1((String s) -> s.toUpperCase())
			.on2((String s, Integer x) -> s + x)
			.on3((Integer a, Integer b, Integer c) -> a + b + c);
		Object o0 = fn.invoke();
		Assert.assertEquals("qwe", o0);
		Object o1 = fn.invoke(o0);
		Assert.assertEquals("QWE", o1);
		Object o2 = fn.invoke(o1, 42);
		Assert.assertEquals("QWE42", o2);
		Object o3 = fn.invoke(10, 20, 30);
		Assert.assertEquals(60, o3);
	}

	@Test
	public void test_UberFn_is_immutable() {
		UberFn fn = UberFn.empty();
		UberFn fn2 = fn.on0(() -> 42);
		Assert.assertFalse(fn.hasArity(0));
		Assert.assertTrue(fn2.hasArity(0));
		UberFn fn3 = fn2.on1(x -> 42);
		Assert.assertFalse(fn.hasArity(1));
		Assert.assertFalse(fn2.hasArity(1));
		Assert.assertTrue(fn3.hasArity(0));
		Assert.assertTrue(fn3.hasArity(1));
	}

	@Test
	public void test_mapping_vector_with_UberFn() {
		IFn fn = UberFn.empty().on1((String s) -> s.toUpperCase());
		PersistentVector vector = UberClj.vector("qwe", "rty", "qaz");
		PersistentVector result = CljCore.NAMESPACE.call("mapv", fn, vector);
		Assert.assertEquals(UberClj.vector("QWE", "RTY", "QAZ"), result);
	}

	@Test
	public void test_mapping_vector_from_clojure_with_UberFn() {
		IFn my_fn = UberFn.empty().on1((Long x) -> x * x);
		Function<IFn, PersistentVector> fn = namespace.fn1("fn-that-takes-fn-and-maps-a-vector");
		PersistentVector result = fn.apply(my_fn);
		Assert.assertEquals(UberClj.vector(1L, 4L, 9L, 16L, 25L), result);
	}

	@Test
	public void test_functon_composition_from_clojure_with_UberFn() {
		IFn pow2 = UberFn.empty()
				.on0(() -> Arrays.asList(1,2,3))
				.on1((Integer x) -> x * x);
		Function<IFn, IFn> fn = namespace.fn1("fn-that-takes-fn-and-returns-a-comp-fn");
		IFn pow2AndToString = fn.apply(pow2);
		Assert.assertEquals("[1, 2, 3]", pow2AndToString.invoke());
		Assert.assertEquals("9", pow2AndToString.invoke(3));
		Assert.assertEquals("16", pow2AndToString.invoke(4));
		Assert.assertEquals("25", pow2AndToString.invoke(5));
	}

}
