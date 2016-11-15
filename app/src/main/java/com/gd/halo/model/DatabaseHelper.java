package com.gd.halo.model;

import android.database.sqlite.SQLiteDatabase;

import com.gd.halo.App;
import com.gd.halo.bean.Jztk;
import com.litesuits.orm.db.model.ConflictAlgorithm;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by zhouxin on 2016/11/9.
 * Description:
 */
public class DatabaseHelper {
    public Observable saveCarriers(List list) {
        return null;
    }

    public Observable<List<Jztk>> saveJztks(final List<Jztk> jztks) {
        return Observable.create(new Observable.OnSubscribe<List<Jztk>>() {
            @Override
            public void call(Subscriber<? super List<Jztk>> subscriber) {
                if (subscriber.isUnsubscribed()) return;
                SQLiteDatabase db = App.sDb.getWritableDatabase();
                db.beginTransaction();
                try {
                    App.sDb.insert(jztks, ConflictAlgorithm.Replace);
                    subscriber.onNext(jztks);
                    db.setTransactionSuccessful();
                    subscriber.onCompleted();
                } finally {
                    db.endTransaction();
                }
            }
        });
    }

    /*public Observable<List<Jztk>> getJztks() {
        QueryBuilder query = new QueryBuilder(Jztk.class);
        query.appendOrderDescBy("publishedAt");
        query.limit(0, 10);
        App.sDb.query(query);
        return mDb.createQuery(Db.RibotProfileTable.TABLE_NAME,
                "SELECT * FROM " + Db.RibotProfileTable.TABLE_NAME)
                .mapToList(new Func1<Cursor, Ribot>() {
                    @Override
                    public Ribot call(Cursor cursor) {
                        return Ribot.create(Db.RibotProfileTable.parseCursor(cursor));
                    }
                });
    }*/

    /*public Observable<Ribot> setRibots(final Collection<Ribot> newRibots) {
        return Observable.create(new Observable.OnSubscribe<Ribot>() {
            @Override
            public void call(Subscriber<? super Ribot> subscriber) {
                if (subscriber.isUnsubscribed()) return;
                BriteDatabase.Transaction transaction = mDb.newTransaction();
                try {
                    mDb.delete(Db.RibotProfileTable.TABLE_NAME, null);
                    for (Ribot ribot : newRibots) {
                        long result = mDb.insert(Db.RibotProfileTable.TABLE_NAME,
                                Db.RibotProfileTable.toContentValues(ribot.profile()),
                                SQLiteDatabase.CONFLICT_REPLACE);
                        if (result >= 0) subscriber.onNext(ribot);
                    }
                    transaction.markSuccessful();
                    subscriber.onCompleted();
                } finally {
                    transaction.end();
                }
            }
        });
    }

    public Observable<List<Ribot>> getRibots() {
        return mDb.createQuery(Db.RibotProfileTable.TABLE_NAME,
                "SELECT * FROM " + Db.RibotProfileTable.TABLE_NAME)
                .mapToList(new Func1<Cursor, Ribot>() {
                    @Override
                    public Ribot call(Cursor cursor) {
                        return Ribot.create(Db.RibotProfileTable.parseCursor(cursor));
                    }
                });
    }*/
}
