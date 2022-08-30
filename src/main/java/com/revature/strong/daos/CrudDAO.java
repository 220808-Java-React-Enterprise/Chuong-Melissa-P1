package com.revature.strong.daos;

import java.io.IOException;
import java.util.List;

public interface CrudDAO<T> {
    void save(T obj) throws IOException;
    void update(T obj);
    void delete(T obj);
    T getById(String id);
    List<T> getAll();

}
