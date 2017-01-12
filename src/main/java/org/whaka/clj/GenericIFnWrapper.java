package org.whaka.clj;

import clojure.lang.IFn;
import clojure.lang.ISeq;

/**
 * Created by vsubhuman on 12.01.2017.
 */
public final class GenericIFnWrapper implements GenericIFn {

    private IFn delegateFn;

    public GenericIFnWrapper() {
    }

    public GenericIFnWrapper(IFn delegateFn) {
        this.delegateFn = delegateFn;
    }

    public void setDelegateFn(IFn delegateFn) {
        this.delegateFn = delegateFn;
    }

    public IFn getDelegateFn() {
        return delegateFn;
    }

    private IFn assureDelegateFn() {
        IFn delegate = getDelegateFn();
        if (delegate == null)
            throw new IllegalStateException("Delegate IFn is not available at this moment");
        return delegate;
    }

    @Override
    public Object call() throws Exception {
        return assureDelegateFn().call();
    }

    @Override
    public void run() {
        assureDelegateFn().run();
    }

    @Override
    public Object invoke() {
        return assureDelegateFn().invoke();
    }

    @Override
    public Object invoke(Object arg1) {
        return assureDelegateFn().invoke(arg1);
    }

    @Override
    public Object invoke(Object arg1, Object arg2) {
        return assureDelegateFn().invoke(arg1, arg2);
    }

    @Override
    public Object invoke(Object arg1, Object arg2, Object arg3) {
        return assureDelegateFn().invoke(arg1, arg2, arg3);
    }

    @Override
    public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4) {
        return assureDelegateFn().invoke(arg1, arg2, arg3, arg4);
    }

    @Override
    public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5) {
        return assureDelegateFn().invoke(arg1, arg2, arg3, arg4, arg5);
    }

    @Override
    public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6) {
        return assureDelegateFn().invoke(arg1, arg2, arg3, arg4, arg5, arg6);
    }

    @Override
    public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6,
                         Object arg7) {
        return assureDelegateFn().invoke(arg1, arg2, arg3, arg4, arg5, arg6, arg7);
    }

    @Override
    public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
                         Object arg8) {
        return assureDelegateFn().invoke(arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8);
    }

    @Override
    public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
                         Object arg8, Object arg9) {
        return assureDelegateFn().invoke(arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9);
    }

    @Override
    public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
                         Object arg8, Object arg9, Object arg10) {
        return assureDelegateFn().invoke(arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10);
    }

    @Override
    public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
                         Object arg8, Object arg9, Object arg10, Object arg11) {
        return assureDelegateFn().invoke(arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11);
    }

    @Override
    public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
                         Object arg8, Object arg9, Object arg10, Object arg11, Object arg12) {
        return assureDelegateFn().invoke(arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11, arg12);
    }

    @Override
    public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
                         Object arg8, Object arg9, Object arg10, Object arg11, Object arg12, Object arg13) {
        return assureDelegateFn().invoke(arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11, arg12, arg13);
    }

    @Override
    public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
                         Object arg8, Object arg9, Object arg10, Object arg11, Object arg12, Object arg13, Object arg14) {
        return assureDelegateFn().invoke(arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11, arg12, arg13, arg14);
    }

    @Override
    public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
                         Object arg8, Object arg9, Object arg10, Object arg11, Object arg12, Object arg13, Object arg14,
                         Object arg15) {
        return assureDelegateFn().invoke(arg15, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11, arg12, arg13, arg14, arg15);
    }

    @Override
    public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
                         Object arg8, Object arg9, Object arg10, Object arg11, Object arg12, Object arg13, Object arg14,
                         Object arg15, Object arg16) {
        return assureDelegateFn().invoke(arg16, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11, arg12, arg13, arg14, arg15, arg16);
    }

    @Override
    public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
                         Object arg8, Object arg9, Object arg10, Object arg11, Object arg12, Object arg13, Object arg14,
                         Object arg15, Object arg16, Object arg17) {
        return assureDelegateFn().invoke(arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11, arg12, arg13, arg14, arg15, arg16, arg17);
    }

    @Override
    public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
                         Object arg8, Object arg9, Object arg10, Object arg11, Object arg12, Object arg13, Object arg14,
                         Object arg15, Object arg16, Object arg17, Object arg18) {
        return assureDelegateFn().invoke(arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11, arg12, arg13, arg14, arg15, arg16, arg17, arg18);
    }

    @Override
    public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
                         Object arg8, Object arg9, Object arg10, Object arg11, Object arg12, Object arg13, Object arg14,
                         Object arg15, Object arg16, Object arg17, Object arg18, Object arg19) {
        return assureDelegateFn().invoke(arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11, arg12, arg13, arg14, arg15, arg16, arg17, arg18, arg19);
    }

    @Override
    public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
                         Object arg8, Object arg9, Object arg10, Object arg11, Object arg12, Object arg13, Object arg14,
                         Object arg15, Object arg16, Object arg17, Object arg18, Object arg19, Object arg20) {
        return assureDelegateFn().invoke(arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11, arg12, arg13, arg14, arg15, arg16, arg17, arg18, arg19, arg20);
    }

    @Override
    public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
                         Object arg8, Object arg9, Object arg10, Object arg11, Object arg12, Object arg13, Object arg14,
                         Object arg15, Object arg16, Object arg17, Object arg18, Object arg19, Object arg20, Object... args) {
        return assureDelegateFn().invoke(arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11, arg12, arg13, arg14, arg15, arg16, arg17, arg18, arg19, arg20, args);
    }

    @Override
    public Object applyTo(ISeq arglist) {
        return assureDelegateFn().applyTo(arglist);
    }
}
