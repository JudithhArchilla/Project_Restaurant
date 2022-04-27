package dao;

import java.io.IOException;
import java.util.List;

public interface Dao <T, I> {

    void insert(T object);

    T getById(I id);

    List<T> getAll();

    void delete(I id);

    void update(T object);

}
