package org.whaka.clj;

import clojure.lang.IFn;
import clojure.lang.ISeq;

public interface GenericIFn extends IFn {

	static GenericIFn wrap(IFn fn) {
		return new GenericIFnWrapper(fn);
	}

	@SuppressWarnings("unchecked")
	default <T> T _invoke() {
		return (T) invoke();
	}

	@SuppressWarnings("unchecked")
	default <T> T _invoke(Object arg1) {
		return (T) invoke(arg1);
	}

	@SuppressWarnings("unchecked")
	default <T> T _invoke(Object arg1, Object arg2) {
		return (T) invoke(arg1, arg2);
	}

	@SuppressWarnings("unchecked")
	default <T> T _invoke(Object arg1, Object arg2, Object arg3) {
		return (T) invoke(arg1, arg2, arg3);
	}

	@SuppressWarnings("unchecked")
	default <T> T _invoke(Object arg1, Object arg2, Object arg3, Object arg4) {
		return (T) invoke(arg1, arg2, arg3, arg4);
	}

	@SuppressWarnings("unchecked")
	default <T> T _invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5) {
		return (T) invoke(arg1, arg2, arg3, arg4, arg5);
	}

	@SuppressWarnings("unchecked")
	default <T> T _invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6) {
		return (T) invoke(arg1, arg2, arg3, arg4, arg5, arg6);
	}

	@SuppressWarnings("unchecked")
	default <T> T _invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6,
			Object arg7) {
		return (T) invoke(arg1, arg2, arg3, arg4, arg5, arg6, arg7);
	}

	@SuppressWarnings("unchecked")
	default <T> T _invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
			Object arg8) {
		return (T) invoke(arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8);
	}

	@SuppressWarnings("unchecked")
	default <T> T _invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
			Object arg8, Object arg9) {
		return (T) invoke(arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9);
	}

	@SuppressWarnings("unchecked")
	default <T> T _invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
			Object arg8, Object arg9, Object arg10) {
		return (T) invoke(arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10);
	}

	@SuppressWarnings("unchecked")
	default <T> T _invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
			Object arg8, Object arg9, Object arg10, Object arg11) {
		return (T) invoke(arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11);
	}

	@SuppressWarnings("unchecked")
	default <T> T _invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
			Object arg8, Object arg9, Object arg10, Object arg11, Object arg12) {
		return (T) invoke(arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11, arg12);
	}

	@SuppressWarnings("unchecked")
	default <T> T _invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
			Object arg8, Object arg9, Object arg10, Object arg11, Object arg12, Object arg13) {
		return (T) invoke(arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11, arg12, arg13);
	}

	@SuppressWarnings("unchecked")
	default <T> T _invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
			Object arg8, Object arg9, Object arg10, Object arg11, Object arg12, Object arg13, Object arg14) {
		return (T) invoke(arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11, arg12, arg13, arg14);
	}

	@SuppressWarnings("unchecked")
	default <T> T _invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
			Object arg8, Object arg9, Object arg10, Object arg11, Object arg12, Object arg13, Object arg14,
			Object arg15) {
		return (T) invoke(arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11, arg12, arg13, arg14, arg15);
	}

	@SuppressWarnings("unchecked")
	default <T> T _invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
			Object arg8, Object arg9, Object arg10, Object arg11, Object arg12, Object arg13, Object arg14,
			Object arg15, Object arg16) {
		return (T) invoke(arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11, arg12, arg13, arg14, arg15, arg16);
	}

	@SuppressWarnings("unchecked")
	default <T> T _invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
			Object arg8, Object arg9, Object arg10, Object arg11, Object arg12, Object arg13, Object arg14,
			Object arg15, Object arg16, Object arg17) {
		return (T) invoke(arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11, arg12, arg13, arg14, arg15, arg16, arg17);
	}

	@SuppressWarnings("unchecked")
	default <T> T _invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
			Object arg8, Object arg9, Object arg10, Object arg11, Object arg12, Object arg13, Object arg14,
			Object arg15, Object arg16, Object arg17, Object arg18) {
		return (T) invoke(arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11, arg12, arg13, arg14, arg15, arg16, arg17, arg18);
	}

	@SuppressWarnings("unchecked")
	default <T> T _invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
			Object arg8, Object arg9, Object arg10, Object arg11, Object arg12, Object arg13, Object arg14,
			Object arg15, Object arg16, Object arg17, Object arg18, Object arg19) {
		return (T) invoke(arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11, arg12, arg13, arg14, arg15, arg16, arg17, arg18, arg19);
	}

	@SuppressWarnings("unchecked")
	default <T> T _invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
			Object arg8, Object arg9, Object arg10, Object arg11, Object arg12, Object arg13, Object arg14,
			Object arg15, Object arg16, Object arg17, Object arg18, Object arg19, Object arg20) {
		return (T) invoke(arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11, arg12, arg13, arg14, arg15, arg16, arg17, arg18, arg19, arg20);
	}

	@SuppressWarnings("unchecked")
	default <T> T _invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
			Object arg8, Object arg9, Object arg10, Object arg11, Object arg12, Object arg13, Object arg14,
			Object arg15, Object arg16, Object arg17, Object arg18, Object arg19, Object arg20, Object... args) {
		return (T) invoke(arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11, arg12, arg13, arg14, arg15, arg16, arg17, arg18, arg19, arg20, args);
	}

	@SuppressWarnings("unchecked")
	default <T> T _applyTo(ISeq arglist) {
		return (T) applyTo(arglist);
	}
}