package by.karpov.repository;

import by.karpov.model.BaseModel;

import java.util.List;
import java.util.Optional;

public interface Dao<T extends BaseModel> {
    Optional<T> findById(int id);

    List<T> findAll();

    T create(T t);

    void delete(T t);

    T update(T t);
}
