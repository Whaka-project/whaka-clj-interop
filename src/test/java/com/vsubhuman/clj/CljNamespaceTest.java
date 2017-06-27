package com.vsubhuman.clj;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import clojure.lang.Agent;
import clojure.lang.Atom;
import clojure.lang.IFn;
import clojure.lang.Ref;
import clojure.lang.Var;

public class CljNamespaceTest {

	private static final CljNamespace namespace = new CljNamespace(TestNames.TEST_NS);

	@Test
	public void namespace_instantiation_automatically_requires_it() {

		// Separate namespace is used, since it should not be loaded at the beginning of the test
		final String clj_core_test_ns = "com.vsubhuman.clj.clj-namespace-test-ns";

		Var var = UberClj.var(clj_core_test_ns, "var42");

		// Var is unbound, since namespace was not yet required
		Assert.assertFalse(var.isBound());

		new CljNamespace(clj_core_test_ns);

		// After CljNamespace was constructed - var is bound
		Assert.assertTrue(var.isBound());
	}

	@Test
	public void var_returns_namespace_var() {
		Var var = namespace.var(TestNames.STR_QWE);
		Assert.assertTrue(var.isBound());
		Assert.assertThat(var.deref(), Matchers.is("qwe"));
	}

	@Test
	public void value_returns_derefed_var() {
		String str = namespace.value(TestNames.STR_QWE);
		Assert.assertThat(str, Matchers.is("qwe"));
	}

	@Test
	public void value_returns_underefed_atoms_refs_agens_etc() {

		Ref ref = namespace.value(TestNames.REF_STR_QWE);
		Atom atom = namespace.value(TestNames.ATOM_STR_QWE);
		Agent agent = namespace.value(TestNames.AGENT_STR_QWE);

		Assert.assertThat(ref.deref(), Matchers.is("qwe"));
		Assert.assertThat(atom.deref(), Matchers.is("qwe"));
		Assert.assertThat(agent.deref(), Matchers.is("qwe"));
	}

	@Test
	public void deref_returns_derefed_atoms_refs_agents_etc() {

		String ref = namespace.deref(TestNames.REF_STR_QWE);
		String atom = namespace.deref(TestNames.ATOM_STR_QWE);
		String agent = namespace.deref(TestNames.AGENT_STR_QWE);

		Assert.assertThat(ref, Matchers.is("qwe"));
		Assert.assertThat(atom, Matchers.is("qwe"));
		Assert.assertThat(agent, Matchers.is("qwe"));
	}

	@Test(expected = ClassCastException.class)
	public void deref_on_a_simple_value_will_throw_a_exception() {
		namespace.deref(TestNames.STR_QWE);
	}

	@Test(expected = ClassCastException.class)
	public void deref_on_fn_value_will_throw_a_exception() {
		namespace.deref(TestNames.FN_POW2);
	}

	@Test
	public void fn_is_equal_to_value_with_a_predefined_cast() {

		IFn fn1 = namespace.value(TestNames.FN_POW2);
		IFn fn2 = namespace.fn(TestNames.FN_POW2);

		Assert.assertThat(fn1, Matchers.sameInstance(fn2));
	}

	@Test
	public void call_executes_a_function_with_varargs() {
		Long sum = CljCore.NAMESPACE.call("+", 10, 20, 30);
		Assert.assertThat(sum, Matchers.is(60L));
	}

	@Test
	public void fn0_creates_a_aupplier_that_calls_function() {
		Supplier<Long> sum = CljCore.NAMESPACE.fn0("+");
		Assert.assertThat(sum.get(), Matchers.is(0L));
	}

	@Test
	public void fn1_creates_a_java_function() {
		Function<Long, Long> sum = CljCore.NAMESPACE.fn1("+");
		Assert.assertThat(sum.apply(42L), Matchers.is(42L));
	}

	@Test
	public void fn2_creates_a_java_bifunction() {
		BiFunction<Long, Long, Long> sum = CljCore.NAMESPACE.fn2("+");
		Assert.assertThat(sum.apply(42L, 20L), Matchers.is(62L));
	}
}
