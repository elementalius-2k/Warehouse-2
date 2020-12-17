package dao;

import java.util.List;

public interface DAO<T> {
    void createTable();
    long insert(T object);
    void delete(long id);
    void update(T object);
    T getByID(long id);
    List<T> getAll();
}
