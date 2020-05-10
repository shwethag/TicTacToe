package com.example.tictactoe;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    private ListView mGameListView;
    private GameAdapter mGameAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        mGameListView = (ListView) findViewById(R.id.game_data);
        mGameAdapter = new GameAdapter(getApplicationContext());
        mGameListView.setAdapter(mGameAdapter);
    }


}
