package com.example.tictactoe.model;

import android.provider.BaseColumns;

public final class GameDataContract {
    private GameDataContract() {

    }

    public static class GameEntry implements BaseColumns {
        public static final String TABLE_NAME = "GameData";
        public static final String COL_ID = "GameId";
        public static final String COL_RESULT = "Result";
    }

    public static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + GameEntry.TABLE_NAME + " ( " +
                    GameEntry.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    GameEntry.COL_RESULT + " TEXT )";

    public static final String SQL_DELETE_TABLE =
            "DELETE TABLE IF EXISTS " + GameEntry.TABLE_NAME;
}
