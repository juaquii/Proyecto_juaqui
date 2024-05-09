package com.github.juaquii.Model.dao;

import com.github.juaquii.Model.entity.Player;

import java.io.Closeable;
import java.sql.SQLException;
import java.util.List;

public interface DAO<T> extends Closeable {
    T save(T entity);
    T update(T entity);
    T delete(T entity) throws SQLException;
    T findById(int key);
}
