package com.example.tictactoe.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class GameDataManager {
    private GameDataDbHelper mGameDataHelper;
    private Context mContext;
    private SQLiteDatabase mDb;

    public GameDataManager(Context context) {
        mContext = context;
    }

    public GameDataManager open() {
        mGameDataHelper = new GameDataDbHelper(mContext);
        mDb = mGameDataHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mGameDataHelper.close();
    }

    public void insert(String result) {
        ContentValues value = new ContentValues();
        value.put(GameDataContract.GameEntry.COL_RESULT, result);
        mDb.insert(GameDataContract.GameEntry.TABLE_NAME, null, value);
    }

    public List<GameData> getGameData() {
        String[] columns = new String[] {GameDataContract.GameEntry.COL_ID, GameDataContract.GameEntry.COL_RESULT};
        Cursor cursor = mDb.query(GameDataContract.GameEntry.TABLE_NAME, columns, null, null, null, null, null);
        List<GameData> gameData = new ArrayList<>();
        if(cursor != null) {
            cursor.moveToFirst();
            do {
                gameData.add(new GameData(cursor.getInt(cursor.getColumnIndex(GameDataContract.GameEntry.COL_ID))
                        , cursor.getString(cursor.getColumnIndex(GameDataContract.GameEntry.COL_RESULT))));
            } while(cursor.moveToNext());
            cursor.close();
        }
        return gameData;
    }

    public void clear() {
        mDb.delete(GameDataContract.GameEntry.TABLE_NAME, null, null);
    }
}
