package com.vsubhuman.clj;

import static com.vsubhuman.clj.TestNames.AGENT_STR_QWE;
import static com.vsubhuman.clj.TestNames.ATOM_STR_QWE;
import static com.vsubhuman.clj.TestNames.NIL;
import static com.vsubhuman.clj.TestNames.REF_STR_QWE;
import static com.vsubhuman.clj.TestNames.STR_QWE;
import static com.vsubhuman.clj.TestNames.TEST_NS;

import java.io.FileNotFoundException;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import clojure.java.api.Clojure;
import clojure.lang.Agent;
import clojure.lang.Atom;
import clojure.lang.IFn;
import clojure.lang.Ref;
import clojure.lang.Var;

public class CljCoreTest {

	public CljCoreTest() {
		CljCore.require(TEST_NS);
	}

	@Test
	public void test_deref_returns_unbound_value_before_namespace_is_required() {

		// Separate namespace is used, since it should not be loaded at the beginning of the test
		final String clj_core_test_ns = "com.vsubhuman.clj.clj-core-test-ns";

		// Before `require` is executed - deref returns an unbound var
		Object field = Clojure.var(clj_core_test_ns, "var42");
		Object deref1 = CljCore.deref(field);
		Assert.assertThat(deref1, Matchers.instanceOf(Var.Unbound.class));

		// After require is executed - deref returns actual var value
		UberClj.require(clj_core_test_ns);
		Object deref2 = CljCore.deref(field);
		Assert.assertThat(deref2, Matchers.is(42L));
	}

	@Test(expected = FileNotFoundException.class)
	public void test_require_fails_on_illegal_namespace() {
		UberClj.require("qwe.rty.qaz");
	}

	@Test
	public void test_deref_on_var_returns_bound_value() {
		// This will load the `var`, NOT a var value
		Var var = UberClj.var(TEST_NS, STR_QWE);
		Assert.assertThat(CljCore.deref(var), Matchers.is("qwe"));
	}

	@Test
	public void test_deref_on_ref_returns_value() {
		Ref ref = UberClj.value(TEST_NS, REF_STR_QWE);
		Assert.assertThat(CljCore.deref(ref), Matchers.is("qwe"));
	}

	@Test
	public void test_deref_on_atom_returns_value() {
		Atom atom = UberClj.value(TEST_NS, ATOM_STR_QWE);
		Assert.assertThat(CljCore.deref(atom), Matchers.is("qwe"));
	}

	@Test
	public void test_deref_on_agent_returns_state() {
		Agent agent = UberClj.value(TEST_NS, AGENT_STR_QWE);
		Assert.assertThat(CljCore.deref(agent), Matchers.is("qwe"));
	}

	@Test(expected = NullPointerException.class)
	public void test_deref_on_nil_or_null_throws_NPE() {
		CljCore.deref(UberClj.value(TEST_NS, NIL));
	}

	@Test
	public void test_namespace_returns_properly_working_sum_function() {
		IFn sum = CljCore.NAMESPACE.fn("+");
		Assert.assertThat(sum.invoke(10, 20, 30), Matchers.is(60L));
	}
}
