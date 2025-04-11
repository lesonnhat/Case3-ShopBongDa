package org.example.case3shopbongda.service;

import java.sql.SQLException;
import java.util.List;

public interface IDAO<E> {
    List<E> findAll();
    List<E> findAllWithStoreProcedure();
    void save(E entity) throws SQLException;
    void saveWithStoreProcedure(E entity) throws SQLException;
    public E findById(String id);
    public E findByIdWithStoreProcedure(String id);
    public boolean update(E entity) throws SQLException;
    public boolean updateWithStoreProcedure(E entity) throws SQLException;
}