package com.codejukebox.optipackroute.persistence.repository.interfaces;

import java.util.List;

public interface RedisRepository {
    void saveArrayList(String key, List<? extends Object> data);
    void addToList(String key, Object item);
    List<Object> getArrayList(String key);
    void deleteArrayList(String key);
    void removeItemFromList(String key, Object item);
}
