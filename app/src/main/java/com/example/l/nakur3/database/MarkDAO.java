package com.example.l.nakur3.database;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by L on 16.08.2017.
 */
public class MarkDAO extends BaseDaoImpl<Mark, Integer> {

    protected MarkDAO(ConnectionSource connectionSource,
                      Class<Mark> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public List<Mark> getAllMark() throws SQLException{
        return this.queryForAll();
    }

    public Mark getMarkById(int id) throws SQLException{
        return this.queryForId(id);
    }
}
