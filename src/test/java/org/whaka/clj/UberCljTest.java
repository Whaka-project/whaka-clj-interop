package org.whaka.clj;

import static org.whaka.clj.TestNames.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import clojure.lang.IFn;
import clojure.lang.IPersistentList;
import clojure.lang.ISeq;
import clojure.lang.PersistentHashMap;
import clojure.lang.PersistentHashSet;
import clojure.lang.PersistentVector;
import clojure.lang.Var;

public class UberCljTest {

	public UberCljTest() {
		UberClj.require(TEST_NS);
	}
	
	@Test
	public void test_var_is_validly_loaded() {

		// "Variable" (definition) is loaded by `var`
		Var var = UberClj.var(TEST_NS, STR_QWE);
		Assert.assertThat(var.deref(), Matchers.is("qwe"));

		// Function definition is loaded by `var`
		Var var2 = UberClj.var(TEST_NS, FN_POW2);
		Assert.assertThat(var2.deref(), Matchers.instanceOf(IFn.class));
		Assert.assertThat(CljCore.<IFn>deref(var2).invoke(12L), Matchers.is(144L));
	}

	@Test
	public void test_value() {
		Assert.assertThat(UberClj.value(TEST_NS, NIL), Matchers.nullValue());
		Assert.assertThat(UberClj.value(TEST_NS, STR_QWE), Matchers.is("qwe"));
		Assert.assertThat(UberClj.value(TEST_NS, INT_42), Matchers.is(42));
		Assert.assertThat(UberClj.value(TEST_NS, LONG_42), Matchers.is(42L));
		Assert.assertThat(UberClj.value(TEST_NS, FN_POW2), Matchers.instanceOf(IFn.class));
	}

	@Test(expected = NoSuchElementException.class)
	public void test_value_fails_on_non_existing_reference() {
		UberClj.value(TEST_NS, "no-such-var");
	}
	
	@Test
	public void test_var_does_not_fails_on_non_existing_reference() {
		Var var = UberClj.var(TEST_NS, "no-such-var");
		Assert.assertThat(var.deref(), Matchers.instanceOf(Var.Unbound.class));
	}

	@Test
	public void test_fn() {
		UberClj.require(TEST_NS);
		IFn fn = UberClj.fn(TEST_NS, FN_POW2);
		Assert.assertThat(fn.invoke(8), Matchers.is(64L));
	}

	@Test(expected = NoSuchElementException.class)
	public void test_fn_fails_on_no_existing_reference() {
		UberClj.require(TEST_NS);
		UberClj.fn(TEST_NS, "no-such-fn");
	}

	@Test(expected = ClassCastException.class)
	public void test_fn_fails_on_non_function_var() {
		UberClj.require(TEST_NS);
		UberClj.fn(TEST_NS, STR_QWE);
	}
	
	@Test
	public void vector_factory() {
		PersistentVector vector = UberClj.vector(1,2,3,4,4,5,5);
		Assert.assertThat(vector, Matchers.is(Arrays.asList(1,2,3,4,4,5,5)));
	}
	
	@Test
	public void list_factory() {
		IPersistentList list = UberClj.list(1,2,3,4,4,5,5);
		Assert.assertThat(list, Matchers.is(Arrays.asList(1,2,3,4,4,5,5)));
	}
	
	@Test
	public void set_factory() {
		PersistentHashSet set = UberClj.set(1,2,3,4,4,5,5);
		Assert.assertThat(set, Matchers.is(new HashSet<>(Arrays.asList(1,2,3,4,5))));
	}
	
	@Test
	public void map_factory() {
		PersistentHashMap map = UberClj.map(1,2, 3,4, 4,5, 5,5);
		Map<Integer, Integer> expected = new HashMap<Integer,Integer>() {{
			put(1, 2);
			put(3, 4);
			put(4, 5);
			put(5, 5);
		}};
		Assert.assertThat(map, Matchers.is(expected));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void map_factory_throws_exception_on_uneven_elements() {
		UberClj.map(1,2,3);
	}
	
	@Test
	public void seq_factory() {
		ISeq seq = UberClj.seq(1,2,3,4,4,5,5);
		Assert.assertThat(seq, Matchers.is(Arrays.asList(1,2,3,4,4,5,5)));
	}
}
