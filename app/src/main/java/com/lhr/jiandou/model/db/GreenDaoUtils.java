package com.lhr.jiandou.model.db;

import com.lhr.jiandou.MyApplication;

import java.util.List;

/**
 * Created by ChinaLHR on 2017/1/8.
 * Email:13435500980@163.com
 */

public class GreenDaoUtils {
    private static GreenDaoHelper mHelper = GreenDaoHelper.getInstance(MyApplication.getContext());

    /**
     * 插入电影
     *
     * @param movie_db
     * @return
     */
    public static boolean insertMovie(Movie_db movie_db) {
        long insert = mHelper.getDaoSession().getMovie_dbDao().insert(movie_db);
        if (insert != 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean insertBook(Book_db book_db) {
        long insert = mHelper.getDaoSession().getBook_dbDao().insert(book_db);
        if (insert != 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean insertActor(Actor_db actor_db) {
        long insert = mHelper.getDaoSession().getActor_dbDao().insert(actor_db);
        if (insert != 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 删除电影
     *
     * @param id
     */
    public static boolean deleteMovie(Long id) {
        List<Movie_db> list = mHelper.getDaoSession().getMovie_dbDao().queryBuilder()
                .where(Movie_dbDao.Properties.Id.eq(id))
                .build()
                .list();
        for (Movie_db movie : list) {
            mHelper.getDaoSession().getMovie_dbDao().delete(movie);
        }
        return !list.isEmpty();
    }

    public static boolean deleteBook(Long id) {
        List<Book_db> list = mHelper.getDaoSession().getBook_dbDao().queryBuilder()
                .where(Book_dbDao.Properties.Id.eq(id))
                .build()
                .list();
        for (Book_db book : list) {
            mHelper.getDaoSession().getBook_dbDao().delete(book);
        }
        return !list.isEmpty();
    }

    public static boolean deletaActor(Long id) {
        List<Actor_db> list = mHelper.getDaoSession().getActor_dbDao().queryBuilder()
                .where(Actor_dbDao.Properties.Id.eq(id))
                .build()
                .list();
        for (Actor_db actor : list) {
            mHelper.getDaoSession().getActor_dbDao().delete(actor);
        }
        return !list.isEmpty();
    }

    /**
     * 查询所有电影
     *
     * @return
     */
    public static List<Movie_db> queryAllMovie() {
        List<Movie_db> movie_dbs = mHelper.getDaoSession().getMovie_dbDao().loadAll();
        return movie_dbs;
    }

    public static List<Book_db> queryAllBook() {
        List<Book_db> book_dbs = mHelper.getDaoSession().getBook_dbDao().loadAll();
        return book_dbs;
    }

    public static List<Actor_db> queryAllActor() {
        List<Actor_db> actor_dbs = mHelper.getDaoSession().getActor_dbDao().loadAll();
        return actor_dbs;
    }

    /**
     * 根据Title查询电影
     *
     * @param id
     * @return
     */
    public static boolean queryMovie(Long id) {
        List<Movie_db> list = mHelper.getDaoSession().getMovie_dbDao().queryBuilder()
                .where(Movie_dbDao.Properties.Id.eq(id))
                .build()
                .list();
        return !list.isEmpty();
    }

    public static boolean queryBook(Long id) {
        List<Book_db> list = mHelper.getDaoSession().getBook_dbDao().queryBuilder()
                .where(Book_dbDao.Properties.Id.eq(id))
                .build()
                .list();
        return !list.isEmpty();
    }

    public static boolean queryActor(Long id) {
        List<Actor_db> list = mHelper.getDaoSession().getActor_dbDao().queryBuilder()
                .where(Actor_dbDao.Properties.Id.eq(id))
                .build()
                .list();
        return !list.isEmpty();
    }

}
