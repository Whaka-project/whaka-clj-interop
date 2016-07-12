package org.whaka.clj;

import clojure.java.api.Clojure;
import clojure.lang.IFn;

public final class UberClj {

	public static final String CORE = "clojure.core";
	
	public static final IFn require = Clojure.var(CORE, "require");
	public static final IFn deref = Clojure.var(CORE, "deref");
	
	private UberClj() {
	}
}
