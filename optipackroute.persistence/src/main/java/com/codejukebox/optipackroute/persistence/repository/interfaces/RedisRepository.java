package com.codejukebox.optipackroute.persistence.repository.interfaces;

import java.util.List;

public interface RedisRepository {
	<T> void saveArrayList(String key, List<T> list);
    void addToList(String key, Object item);
    <T> List<T> retrieveArrayList(String key, Class<T> clazz);
    void deleteArrayList(String key);
    void removeItemFromList(String key, Object item);
}
