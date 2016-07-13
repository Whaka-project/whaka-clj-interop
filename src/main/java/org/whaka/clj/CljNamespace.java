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
	
	public Object deref(String name) {
		return UberClj.deref(var(name));
	}
	
	public <T> T deref(String name, Class<T> type) {
		return UberClj.deref(var(name), type);
	}
	
	public Object value(String name) {
		return UberClj.value(this.name, name);
	}
	
	public IFn fn(String name) {
		return UberClj.fn(this.name, name);
	}
}
