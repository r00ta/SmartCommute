package com.r00ta.telematics.platform.here.models.routing;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Warning {

    /**
     * Warning Category. Possible values:
     * 2 = Suspicious u-turn. The u-turn might come from a poor point quality and might not be actually driven.
     * <p>
     * 3 = Forbidden driving direction. Vehicle entered a one way street into the forbidden direction.
     * <p>
     * 4 = Forbidden access. Vehicle entered a street which is not allowed for the vehicle type.
     * <p>
     * 5 = Leaving no-through-traffic zone. Vehicle entered and left such a zone, which is not allowed.
     * <p>
     * 6 = Illegal u-turn. Vehicle made a u-turn where it is not allowed.
     * <p>
     * 7 = Gate traversal. Vehicle passed a gate. This might be illegal or impossible without a key.
     * <p>
     * 8 = Illegal turn. Vehicle made a forbidden turn.
     * <p>
     * 19 = Illegally traversed a road which is temporarily closed for construction work.
     * <p>
     * 1001 = Deprecated CSV column. The input file contains a column, which should no longer be used.
     * <p>
     * 1002 = Unknown CSV columns. The listed columns of the input file are being ignored.
     * <p>
     * 1003 = Unconnected route links. Within the route, there is a gap between two subsequent links.
     * <p>
     * 1004 = Ignored bad trace point. The quality of the trace point was considered so bad, that the point was ignored for the matching.
     * <p>
     * 1005 = Trace point long match distance. The trace point was matched onto a road link quite far away.
     * <p>
     * 1006 = Trace point matched out of sequence on route. A trace point got matched onto the route at a later spot then the subsequent trace point got matched.
     * <p>
     * 1008 = Trace point couldn't be matched. The trace point is far away from any road and no neighbouring trace points could be used to match it in between them.
     * <p>
     * 1009 = For subsequent trace points, the route distance is much bigger than the airline distance between the points.
     * <p>
     * 1010 = Vehicle stopped.
     * <p>
     * 1011 = Speed limits appear to be submitted in wrong unit. RME tried to correct them.
     * <p>
     * 1012 = Heading values appear to be broken and are ignored.
     */
    @JsonProperty("category")
    public Integer category;

    /**
     * Sequence number of the link in the route to which the warning refers. -1 if not applicable.
     */
    @JsonProperty("routeLinkSeqNum")
    public Integer routeLinkSeqNum;

    /**
     * Sequence number of the trace point to which the warning refers. -1 if not applicable.
     */
    @JsonProperty("tracePointSeqNum")
    public Integer tracePointSeqNum;

    @JsonProperty("text")
    public String text;
}
