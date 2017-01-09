package com.lhr.jiandou.model.db;

import android.content.Context;

import com.lhr.jiandou.model.dbinterface.DbObservrt;
import com.lhr.jiandou.model.dbinterface.DbSubject;

import java.util.Collections;
import java.util.List;

/**
 * Created by ChinaLHR on 2017/1/8.
 * Email:13435500980@163.com
 */

public class GreenDaoUtils extends DbSubject {

    private GreenDaoHelper mHelper;

    public GreenDaoUtils(Context context) {
        mHelper = GreenDaoHelper.getInstance(context);
    }

    /**
     * 插入电影
     *
     * @param movie_db
     * @return
     */
    public boolean insertMovie(Movie_db movie_db) {
        long insert = mHelper.getDaoSession().getMovie_dbDao().insert(movie_db);
        if (insert != 0) {
            notifyUpdateMovie();
            return true;

        } else {
            return false;
        }
    }

    public boolean insertBook(Book_db book_db) {
        long insert = mHelper.getDaoSession().getBook_dbDao().insert(book_db);
        if (insert != 0) {
            notifyUpdateBook();
            return true;
        } else {
            return false;
        }
    }

    public boolean insertActor(Actor_db actor_db) {
        long insert = mHelper.getDaoSession().getActor_dbDao().insert(actor_db);
        if (insert != 0) {
            notifyUpdateActor();
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
    public boolean deleteMovie(String id) {
        List<Movie_db> list = mHelper.getDaoSession().getMovie_dbDao().queryBuilder()
                .where(Movie_dbDao.Properties.MovieId.eq(id))
                .build()
                .list();
        for (Movie_db movie : list) {
            mHelper.getDaoSession().getMovie_dbDao().delete(movie);
        }
        if (!list.isEmpty()) {
            notifyUpdateMovie();
        }
        return !list.isEmpty();
    }

    public boolean deleteBook(String id) {
        List<Book_db> list = mHelper.getDaoSession().getBook_dbDao().queryBuilder()
                .where(Book_dbDao.Properties.Book_id.eq(id))
                .build()
                .list();
        for (Book_db book : list) {
            mHelper.getDaoSession().getBook_dbDao().delete(book);
        }
        if (!list.isEmpty()) {
            notifyUpdateBook();
        }
        return !list.isEmpty();
    }

    public boolean deletaActor(String id) {
        List<Actor_db> list = mHelper.getDaoSession().getActor_dbDao().queryBuilder()
                .where(Actor_dbDao.Properties.Actor_id.eq(id))
                .build()
                .list();
        for (Actor_db actor : list) {
            mHelper.getDaoSession().getActor_dbDao().delete(actor);
        }
        if (!list.isEmpty()) {
            notifyUpdateActor();
        }
        return !list.isEmpty();
    }

    /**
     * 查询所有电影
     *
     * @return
     */
    public List<Movie_db> queryAllMovie() {
        List<Movie_db> movie_dbs = mHelper.getDaoSession().getMovie_dbDao().loadAll();
        Collections.reverse(movie_dbs);
        return movie_dbs;
    }

    public List<Book_db> queryAllBook() {
        List<Book_db> book_dbs = mHelper.getDaoSession().getBook_dbDao().loadAll();
        Collections.reverse(book_dbs);
        return book_dbs;
    }

    public List<Actor_db> queryAllActor() {
        List<Actor_db> actor_dbs = mHelper.getDaoSession().getActor_dbDao().loadAll();
        Collections.reverse(actor_dbs);
        return actor_dbs;
    }

    /**
     * 根据Title查询电影
     *
     * @param id
     * @return
     */
    public boolean queryMovie(String id) {
        List<Movie_db> list = mHelper.getDaoSession().getMovie_dbDao().queryBuilder()
                .where(Movie_dbDao.Properties.MovieId.eq(id))
                .build()
                .list();
        return !list.isEmpty();
    }

    public boolean queryBook(String id) {
        List<Book_db> list = mHelper.getDaoSession().getBook_dbDao().queryBuilder()
                .where(Book_dbDao.Properties.Book_id.eq(id))
                .build()
                .list();
        return !list.isEmpty();
    }

    public boolean queryActor(String id) {
        List<Actor_db> list = mHelper.getDaoSession().getActor_dbDao().queryBuilder()
                .where(Actor_dbDao.Properties.Actor_id.eq(id))
                .build()
                .list();
        return !list.isEmpty();
    }


    @Override
    public void notifyUpdateMovie() {
        for (Object o : observers) {
            ((DbObservrt) o).updateMovie();
        }
    }

    @Override
    public void notifyUpdateBook() {
        for (Object o : observers) {
            ((DbObservrt) o).updateBook();
        }
    }

    @Override
    public void notifyUpdateActor() {
        for (Object o : observers) {
            ((DbObservrt) o).updateActor();
        }
    }
}
