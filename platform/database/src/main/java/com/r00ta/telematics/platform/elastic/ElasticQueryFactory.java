package com.r00ta.telematics.platform.elastic;

import com.r00ta.telematics.platform.SmartQuery;
import com.r00ta.telematics.platform.operators.LongOperator;
import com.r00ta.telematics.platform.operators.StringOperator;
import org.json.JSONArray;
import org.json.JSONObject;

public class ElasticQueryFactory {

    public static String build(SmartQuery query) {
        JSONObject json = new JSONObject();
        if (query.limit != null) {
            json.put("size", query.limit);
        }

        JSONObject internalQuery = new JSONObject();

        if (query.stringOperations.size() + query.longOperations.size() > 1) { // there are multiple conditions
            JSONArray conditionsArray = new JSONArray();
            internalQuery.put("bool", new JSONObject().put("must", conditionsArray));

            for (SmartQuery.InternalWhereDecision<StringOperator, String> condition : query.stringOperations) {
                conditionsArray.put(createStringMatch(condition));
            }

            for (SmartQuery.InternalWhereDecision<LongOperator, Long> condition : query.longOperations) {
                conditionsArray.put(createLongcondition(condition));
            }
        } else {
            if (query.stringOperations.size() > 0) {
                SmartQuery.InternalWhereDecision<StringOperator, String> decision = query.stringOperations.get(0);
                internalQuery = createStringMatch(decision);
            } else {
                SmartQuery.InternalWhereDecision<LongOperator, Long> decision = query.longOperations.get(0);
                internalQuery = createLongcondition(decision);
            }
        }

        json.put("query", internalQuery);

        return json.toString();
    }

    private static JSONObject createStringMatch(SmartQuery.InternalWhereDecision<StringOperator, String> decision) {
        return new JSONObject().put("match", new JSONObject().put(decision.property, decision.value));
    }

    private static JSONObject createLongcondition(SmartQuery.InternalWhereDecision<LongOperator, Long> decision) {
        switch (decision.operator) {
            case LTE:
            case GTE:
                return new JSONObject().put("range", new JSONObject().put(decision.property, new JSONObject().put(OperatorQueryFactory.convert(decision.operator), decision.value)));
            case EQUALS:
                return new JSONObject().put("match", new JSONObject().put(decision.property, decision.value));
        }
        return null;
    }
}
