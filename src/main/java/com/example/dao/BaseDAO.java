package com.example.dao;


import java.util.List;

public interface BaseDAO<T> {
    boolean add(T t);

    boolean update(T t);

    boolean delete(T t);

    boolean deleteById(int id);

    void deleteAll();

    T findById(int id);

    List<T> findAll();


}

