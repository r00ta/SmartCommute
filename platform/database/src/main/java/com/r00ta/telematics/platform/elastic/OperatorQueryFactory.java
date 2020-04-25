package com.r00ta.telematics.platform.elastic;

import java.util.HashMap;
import java.util.Map;

import com.r00ta.telematics.platform.operators.LongOperator;

public class OperatorQueryFactory {
    private static Map<LongOperator, String> longOperators = createDateOperators();

    private static Map<LongOperator, String> createDateOperators() {
        HashMap<LongOperator, String> map = new HashMap<>();
        map.put(LongOperator.LTE, "lte");
        map.put(LongOperator.GTE, "gte");
        return map;
    }

    public static String convert(LongOperator operator) {
        return longOperators.get(operator);
    }

}
