package com.example.tictactoe.model;

public class GameData {
    private int mGameId;
    private String mResult;

    public GameData(int id, String res) {
        mGameId = id;
        mResult = res;
    }

    public int getGameId() {
        return mGameId;
    }

    public String getResult() {
        return mResult;
    }
}
