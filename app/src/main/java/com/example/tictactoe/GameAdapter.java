package com.example.tictactoe;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tictactoe.model.GameData;
import com.example.tictactoe.model.GameDataManager;

import java.util.List;

public class GameAdapter extends BaseAdapter {
    private Context mContext;
    private List<GameData> mGameData;

    public GameAdapter(Context context) {
        mContext = context;
        new FetchDataTask(context).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    public int getCount() {
        return mGameData == null? 0: mGameData.size();
    }

    @Override
    public Object getItem(int position) {
        return mGameData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mGameData.get(position).getGameId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.game_data_list, null);
        }
        TextView gameIdView = convertView.findViewById(R.id.game_id);
        TextView resultView = convertView.findViewById(R.id.game_result);

        gameIdView.setText(String.valueOf(mGameData.get(position).getGameId()));
        resultView.setText(mGameData.get(position).getResult());
        return convertView;
    }

    private class FetchDataTask extends AsyncTask<Void, Void, List<GameData>> {
        private GameDataManager mGameDataManager;

        FetchDataTask(Context context) {
            mGameDataManager = new GameDataManager(context);
            mGameDataManager.open();
        }
        @Override
        protected List<GameData> doInBackground(Void... voids) {
            return mGameDataManager.getGameData();
        }

        @Override
        protected void onPostExecute(List<GameData> gameData) {
            super.onPostExecute(gameData);
            mGameData = gameData;
            mGameDataManager.close();
            notifyDataSetChanged();
        }
    }
}
