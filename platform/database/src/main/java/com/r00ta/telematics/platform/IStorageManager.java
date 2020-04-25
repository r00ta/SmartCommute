package com.r00ta.telematics.platform;

import java.util.List;

public interface IStorageManager {

    String create(String key, String request, String index);

    <T> List<T> search(SmartQuery query, String index, Class<T> type);
}
