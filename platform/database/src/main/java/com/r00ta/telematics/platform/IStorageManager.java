package com.r00ta.telematics.platform;

import java.util.List;

public interface IStorageManager {

    <T> boolean create(String key, T request, String index);

    <T> List<T> search(SmartQuery query, String index, Class<T> type);
}
