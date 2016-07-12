package org.whaka.clj;

import java.io.FileNotFoundException;
import java.util.NoSuchElementException;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import clojure.lang.Var;

public class UberCljTest {

	private static final String REQUIRE_TEST_NS = "org.whaka.clj.require-test-ns";
	private static final String TEST_NS = "org.whaka.clj.test-ns";

	@Test
	public void test_require_and_deref() {

		// Before `require` is executed - deref returns an unbound var
		IFn field = Clojure.var(REQUIRE_TEST_NS, "var42");
		Object deref1 = UberClj.deref(field);
		Assert.assertThat(deref1, Matchers.instanceOf(Var.Unbound.class));

		// After require is executed - deref returns actual var value
		UberClj.require(REQUIRE_TEST_NS);
		Object deref2 = UberClj.deref(field);
		Assert.assertThat(deref2, Matchers.is(42L));
	}

	@Test(expected = FileNotFoundException.class)
	public void test_require_fails_on_illegal_namespace() {
		UberClj.require("qwe.rty.qaz");
	}

	@Test
	public void test_var_is_validly_loaded() {

		UberClj.require(TEST_NS);

		// "Variable" (definition) is loaded by `var`
		Var var = UberClj.var(TEST_NS, "test-var");
		Assert.assertThat(var.deref(), Matchers.is("qwe"));

		// Function definition is loaded by `var`
		Var var2 = UberClj.var(TEST_NS, "test-fn");
		IFn fn = UberClj.deref(var2, IFn.class);
		Assert.assertThat(fn.invoke(12L), Matchers.is(24L));
	}

	@Test
	public void test_value() {

		UberClj.require(TEST_NS);

		Assert.assertThat(UberClj.value(TEST_NS, "test-var"), Matchers.is("qwe"));
		Assert.assertThat(UberClj.value(TEST_NS, "test-fn"), Matchers.instanceOf(IFn.class));
	}

	@Test(expected = NoSuchElementException.class)
	public void test_value_fails_on_non_existing_reference() {

		UberClj.require(TEST_NS);
		UberClj.value(TEST_NS, "no-such-var");
	}

	@Test
	public void test_fn() {
		UberClj.require(TEST_NS);
		IFn fn = UberClj.fn(TEST_NS, "test-fn");
		Assert.assertThat(fn.invoke(42), Matchers.is(84L));
	}

	@Test(expected = NoSuchElementException.class)
	public void test_fn_fails_on_no_existing_reference() {
		UberClj.require(TEST_NS);
		UberClj.fn(TEST_NS, "no-such-fn");
	}

	@Test(expected = ClassCastException.class)
	public void test_fn_fails_on_non_function_var() {
		UberClj.require(TEST_NS);
		UberClj.fn(TEST_NS, "test-var");
	}
}
