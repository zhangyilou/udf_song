package com.baice100.bigdata.impala.udf;

/**
 * Created by mengyu on 2019/7/6.
 */
public class BtToCity extends ToCity {
    @Override
    public String evaluate(String bt) {
        return evaluate((Double) null, (Double) null, bt, "");
    }
}
