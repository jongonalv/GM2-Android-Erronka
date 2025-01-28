package com.ikaslea.komertzialakapp.utils;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBManager {
    
    private static DBManager instance;

    private static final Map<Type ,Dao<?, ?>> DAO_MAP = new HashMap<>();

    private static final String DATABASE_URL = "jdbc:sqlite:komertzioa.db";

    private ConnectionSource connectionSource;

    private DBManager() {
        try {

            connectionSource = new JdbcConnectionSource(DATABASE_URL);

            // Tabla bezela definitutta ditugun klassen Dao-a sortu
            for (Class<?> table : Const.TABLES) {
                TableUtils.createTableIfNotExists(connectionSource, table);
                DAO_MAP.put(table, DaoManager.createDao(connectionSource, table));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    /**
     * Datubasean pasatako objetu gordetzen du datubasearen Dao-a erabiliz,
     * Gorden nahi den objetuaren Dao-a ex bada exititzen errore hemango du
     * @param object gorde nahi den objetua
     */
    public void save(Object object) {
        try {
            @SuppressWarnings("unchecked")
            Dao<Object, ?> dao = (Dao<Object, ?>) DAO_MAP.get(object.getClass());
            dao.createOrUpdate(object);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Datubasean pasatako objetu ezabatzen du datubasearen Dao-a erabiliz,
     * Gorden nahi den objetuaren Dao-a ex bada exititzen errore hemango du
     * @param object ezabatu nahi den objetua
     */
    public void delete(Object object) {
        try {
            @SuppressWarnings("unchecked")
            Dao<Object, ?> dao = (Dao<Object, ?>) DAO_MAP.get(object.getClass());
            dao.delete(object);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Datubasean pasatako objetu guztiak itzultzen ditu datubasearen Dao-a erabiliz
     * @param clazz objetuaren klasea
     * @return T motako objetuen lista
     * @param <T> objetuaren klasea
     */
    public <T> List<T> getAll(Class<?> clazz) {
        try {
            @SuppressWarnings("unchecked")
            Dao<T, ?> dao = (Dao<T, ?>) DAO_MAP.get(clazz);
            return dao.queryForAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Datubasean pasatako objetuaren ID-a erabiliz itzultzen du objetua
     * @param clazz objetuaren klasea
     * @param id objetuaren ID-a
     * @return objetua
     * @param <T> objetuaren klasea
     * @param <ID> ID-aren klasea
     */
    public <T, ID> T getById(Class<T> clazz, ID id) {
        try {
            @SuppressWarnings("unchecked")

            Dao<T, ID> dao = (Dao<T, ID>) DAO_MAP.get(clazz);

            return dao.queryForId(id);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Datubaseako tabla guztiak garbitzen ditu datubasearen Dao-a erabiliz
     */
    public void deleteAll() {
        try {
            for (Class<?> table : Const.TABLES) {
                TableUtils.clearTable(connectionSource, table);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
