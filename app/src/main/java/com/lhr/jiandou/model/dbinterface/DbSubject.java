package com.lhr.jiandou.model.dbinterface;

import java.util.ArrayList;

/**
 * Created by ChinaLHR on 2017/1/9.
 * Email:13435500980@163.com
 */

public abstract class DbSubject {
    protected ArrayList<DbObservrt> observers = new ArrayList<>();

    public void attach(DbObservrt dbo) {
        if (dbo == null) throw new NullPointerException();
        if (!observers.contains(dbo)) {
            observers.add(dbo);
        }
    }

    public void detach(DbObservrt dbo) {
        observers.remove(dbo);
    }

    public abstract void notifyUpdateMovie();
    public abstract void notifyUpdateBook();
    public abstract void notifyUpdateActor();
}
