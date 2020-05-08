package com.tt.concurrent.callable;

import java.util.concurrent.Callable;

public class ComplexMathCallable implements Callable<Double> {

    private Integer column;
    private ComplexMath complexMath;

    public ComplexMathCallable(ComplexMath complexMath, Integer column) {
        this.complexMath = complexMath;
        this.column = new Integer(column);
    }

    @Override
    public Double call() throws Exception {
        return complexMath.doComplexMathForColumn(column);
    }
}
