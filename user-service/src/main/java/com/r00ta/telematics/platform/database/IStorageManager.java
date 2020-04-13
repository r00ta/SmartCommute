package com.r00ta.telematics.platform.database;

import java.util.List;

public interface IStorageManager {

    String create(String key, String request, String index);

    <T> List<T> search(String request, String index, Class<T> type);
}
