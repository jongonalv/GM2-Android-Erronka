package com.ikaslea.komertzialakapp.utils;

import android.content.Context;

import com.ikaslea.komertzialakapp.models.Artikuloa;
import com.ikaslea.komertzialakapp.models.Bazkidea;
import com.ikaslea.komertzialakapp.models.Komerziala;
import com.j256.ormlite.android.AndroidConnectionSource;
import com.j256.ormlite.android.apptools.OpenHelperManager;
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

    private ConnectionSource connectionSource;

    private DBManager(Context context) {
        try {

            connectionSource = OpenHelperManager.getHelper(context, DBHelper.class).getConnectionSource();

            // Tabla bezela definitutta ditugun klassen Dao-a sortu
            for (Class<?> table : Const.TABLES) {
                TableUtils.createTableIfNotExists(connectionSource, table);
                DAO_MAP.put(table, DaoManager.createDao(connectionSource, table));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void inizializatu(Context context) {
        if (instance == null) {
            instance = new DBManager(context);
        }
    }

    public static DBManager getInstance() {
        if (instance == null) {
            throw new RuntimeException("DBManager has not been initialized");
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
     * Artikulo bat izenaren arabera bilatzeko metodoa
     * @param izena
     * @return
     */
    public Artikuloa getArtikuloaByIzena(String izena) {
        try {
            @SuppressWarnings("unchecked")
            Dao<Artikuloa, ?> dao = (Dao<Artikuloa, ?>) DAO_MAP.get(Artikuloa.class);
            return dao.queryBuilder()
                    .where()
                    .eq("izena", izena)
                    .queryForFirst();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Komerzial bat izenaren arabera bilatzeko metodoa (LOGIN-erako erabilia dena)
     * @param izena
     * @return
     */
    public Komerziala getByIzena(String izena) {
        try {
            @SuppressWarnings("unchecked")
            Dao<Komerziala, ?> dao = (Dao<Komerziala, ?>) DAO_MAP.get(Komerziala.class);

            return dao.queryBuilder()
                    .where()
                    .eq("izena", izena)
                    .queryForFirst();  // Devuelve el primer resultado encontrado

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Bazkidea> getBazkideByKomerzialaId(int komerzialaId) {
        try {
            @SuppressWarnings("unchecked")
            Dao<Bazkidea, ?> dao = (Dao<Bazkidea, ?>) DAO_MAP.get(Bazkidea.class);

            return dao.queryBuilder()
                    .where()
                    .eq("komerziala_id", komerzialaId)
                    .query();

        } catch (SQLException e) {
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
