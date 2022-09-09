package com.revature.reimburstment.daos;

import java.io.IOException;
import java.util.List;

public interface CrudDAO<T> {
    int save(T obj) throws IOException;
    int update(T obj);
    void delete(String id);
    T getById(String id);
    List<T> getAll();
}
