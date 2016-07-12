package org.whaka.clj;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import clojure.lang.Var;

public class UberCljTest {

	private static final String REQUIRE_TEST_NS = "org.whaka.clj.require-test-ns";

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
}
