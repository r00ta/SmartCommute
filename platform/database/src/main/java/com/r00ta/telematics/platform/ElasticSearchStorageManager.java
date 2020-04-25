package com.r00ta.telematics.platform;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.r00ta.telematics.platform.elastic.ElasticQueryFactory;
import com.r00ta.telematics.platform.model.ElasticSearchResponse;
import com.r00ta.telematics.platform.model.Hit;
import com.r00ta.telematics.platform.utils.HttpHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ElasticSearchStorageManager implements IStorageManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(ElasticSearchStorageManager.class);

    private static final String HOST = "http://elasticsearch:9200/";
    //private static final String HOST = "http://localhost:9200/";

    private static ObjectMapper objectMapper = new ObjectMapper();
    private HttpHelper httpHelper = new HttpHelper(HOST);

    public String create(String key, String request, String index) {
        String response = httpHelper.doPost(index + "/_doc/" + key, request);
        return response;
    }

    public <T> List<T> search(SmartQuery query, String index, Class<T> type) {
        String request = ElasticQueryFactory.build(query);
        LOGGER.info("ES query " + request);

        String response = httpHelper.doPost(index + "/_search", request);
        JavaType javaType = TypeFactory.defaultInstance()
                .constructParametricType(ElasticSearchResponse.class, type);
        LOGGER.info("ES returned " + response);
        try {
            // TODO: check performance issue with generics
            List<Hit<T>> hits = ((ElasticSearchResponse) objectMapper.readValue(response, javaType)).hits.hits;
            if (hits.size() == 0) {
                return new ArrayList<>();
            }
            return hits.stream().map(x -> x.source).collect(Collectors.toList());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}

//@ApplicationScoped
//public class ElasticSearchStorageManager implements IStorageManager {
//
//    private static final Logger log = LoggerFactory.getLogger(ElasticSearchStorageManager.class);
//    private final RestHighLevelClient client;
//
//    public ElasticSearchStorageManager() {
//        client = new RestHighLevelClient(RestClient.builder(new HttpHost("http://elasticsearch",
//                                                                         9200, "http")));
//    }
//
//    /**
//     * Retrieves a document by id using the Get API.
//     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-get.html">Get API on elastic.co</a>
//     *
//     * @param getRequest     the request
//     * @param requestOptions the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
//     * @return the response
//     */
//    public GetResponse get(GetRequest getRequest, RequestOptions requestOptions) throws IOException {
//        return client.get(getRequest, requestOptions);
//    }
//
//    /**
//     * Index a document using the Index API.
//     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-index_.html">Index API on elastic.co</a>
//     *
//     * @param indexRequest   the request
//     * @param requestOptions the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
//     * @return the response
//     */
//    public IndexResponse index(IndexRequest indexRequest, RequestOptions requestOptions) throws IOException {
//        IndexResponse indexResponse = client.index(indexRequest, requestOptions);
//        log.info("Index response for request {} is {}", indexRequest, indexResponse.getResult());
//        return indexResponse;
//    }
//
//    /**
//     * Executes a search request using the Search API.
//     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/search-search.html">Search API on elastic.co</a>
//     *
//     * @param searchRequest  the request
//     * @param requestOptions the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
//     * @return the response
//     */
//    public <T> List<T> search(SearchRequest searchRequest, RequestOptions requestOptions, Class<T> type) throws IOException {
//        JavaType javaType = TypeFactory.defaultInstance()
//                .constructParametricType(ElasticSearchResponse.class, type);
//
//        SearchResponse search = client.search(searchRequest, requestOptions);
//        search.getHits(). .forEach(x -> x.getSourceAsString());
//
//        LOGGER.info("ES returned " + response);
//        try {
//            // TODO: check performance issue with generics
//            List<Hit<T>> hits = ((ElasticSearchResponse) objectMapper.readValue(response, javaType)).hits.hits;
//            if (hits.size() == 0) {
//                return new ArrayList<>();
//            }
//            return hits.stream().map(x -> x.source).collect(Collectors.toList());
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//        return ;
//    }
//
//    public DeleteResponse delete(DeleteRequest deleteRequest, RequestOptions requestOptions) throws IOException {
//        DeleteResponse deleteResponse = client.delete(deleteRequest, requestOptions);
//        log.info("Index response for request {} is {}", deleteRequest, deleteResponse.getResult());
//        return deleteResponse;
//    }
//
//}