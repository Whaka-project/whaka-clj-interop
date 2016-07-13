package org.whaka.clj;

import clojure.lang.IFn;
import clojure.lang.Var;

public final class CljNamespace {

	public final String name;
	
	public CljNamespace(String name) {
		CljCore.require(name);
		this.name = name;
	}
	
	public Var var(String name) {
		return UberClj.var(this.name, name);
	}
	
	public <T> T deref(String name) {
		return CljCore.deref(value(name));
	}
	
	public <T> T value(String name) {
		return UberClj.<T>value(this.name, name);
	}
	
	public IFn fn(String name) {
		return UberClj.fn(this.name, name);
	}
}
