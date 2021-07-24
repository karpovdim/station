package by.karpov.service;

import by.karpov.model.BaseModel;

import java.util.List;

public interface Service<T extends BaseModel>{
    T findById(int id);

    List<T> findAll();

    T create(T t);

    T update(T t);

    void delete(T t);
}
