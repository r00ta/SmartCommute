package com.r00ta.telematics.platform;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.r00ta.telematics.platform.operators.DateOperator;
import com.r00ta.telematics.platform.operators.LongOperator;
import com.r00ta.telematics.platform.operators.StringOperator;

public class SmartQuery {

    public List<InternalWhereDecision<StringOperator, String>> stringOperations = new ArrayList<>();

    public List<InternalWhereDecision<LongOperator, Long>> longOperations = new ArrayList<>();

    public List<InternalWhereDecision<DateOperator, String>> dateOperations = new ArrayList<>();

    public Integer limit = null;

    public List<String> includedProperties;

    public SmartQuery() {
    }

    public SmartQuery where(String property, StringOperator operator, String value) {
        stringOperations.add(new InternalWhereDecision(property, operator, value));
        return this;
    }

    public SmartQuery where(String property, LongOperator operator, Long value) {
        longOperations.add(new InternalWhereDecision(property, operator, value));
        return this;
    }

    public SmartQuery limit(Integer limit){
        this.limit = limit;
        return this;
    }

    public SmartQuery include(String ... params){
        includedProperties = Arrays.stream(params).collect(Collectors.toList());
        return this;
    }



    public class InternalWhereDecision<T, K> {

        public String property;
        public T operator;
        public K value;

        public InternalWhereDecision(String property, T operator, K value) {
            this.property = property;
            this.operator = operator;
            this.value = value;
        }
    }
}