package com.example.l.nakur3.database;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by L on 16.08.2017.
 */
public class FlavorDAO extends BaseDaoImpl<Flavor, Integer> {

    protected FlavorDAO(ConnectionSource connectionSource,
                      Class<Flavor> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public List<Flavor> getAllFlavor() throws SQLException{
        return this.queryForAll();
    }

    public Flavor getFlavorById(int id) throws SQLException{
        return this.queryForId(id);
    }
}
