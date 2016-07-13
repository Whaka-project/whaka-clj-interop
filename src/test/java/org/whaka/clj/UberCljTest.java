package org.whaka.clj;

import static org.whaka.clj.TestNames.*;

import java.util.NoSuchElementException;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import clojure.lang.IFn;
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
}
