package com.example.tictactoe;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.tictactoe.bo.TicTacToeBO;
import com.example.tictactoe.model.GameDataManager;

import java.util.Random;

public class SecondFragment extends Fragment {
    private static final String LOG_TAG = "SecondFragment";
    private TicTacToeBO mTictacbo;
    private int[] mCellArr;
    private GameDataManager mGameDataManager;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        mTictacbo = new TicTacToeBO();
        mGameDataManager = new GameDataManager(getContext());
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapCells();
    }


    public void updateVal(View view) {

        Button cell = (Button) view;
        int col = ((TableRow)cell.getParent()).indexOfChild(view);
        int row = ((TableLayout)getActivity().findViewById(R.id.table_layout)).indexOfChild(((TableRow)cell.getParent()));

        if(!TextUtils.isEmpty(cell.getText())) {
            Log.i(LOG_TAG, "Cell is already filled");
            return;
        }

        mTictacbo.set(row, col, TicTacToeBO.Cell.X);
        cell.setText("X");
        if(!mTictacbo.isAllFilled()) {
            fillRandomCell();
        }
        checkComplete();
    }

    private void checkComplete() {
        if(mTictacbo.isComplete()) {
            TicTacToeBO.Result result = mTictacbo.getResult();
            TextView resView = (TextView) getActivity().findViewById(R.id.res_value);

            if(result.isWon()) {
                resView.setText(getResources().getString(R.string.won));
                resView.setTextColor(getResources().getColor(R.color.green, getActivity().getTheme()));
            } else {
                resView.setText(getResources().getString(R.string.lost));
                resView.setTextColor(getResources().getColor(R.color.red, getActivity().getTheme()));
            }
            changeCellColors(result);
            disableAllCells();
            updateData(result.isWon()? getResources().getString(R.string.won): getResources().getString(R.string.lost));
        }
    }

    private void updateData(final String result) {
        AsyncTask.SERIAL_EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                mGameDataManager.open().insert(result);
                mGameDataManager.close();
            }
        });
    }

    private void changeCellColors(TicTacToeBO.Result result) {
        TableLayout tableLayout = (TableLayout) getActivity().findViewById(R.id.table_layout);

        int index = result.getIndex();
        Button button1 = null, button2 = null, button3 = null;
        switch (result.getDimension()) {
            case ROW:
                button1 = (Button)((TableRow)tableLayout.getChildAt(index)).getChildAt(0);
                button2 = (Button)((TableRow)tableLayout.getChildAt(index)).getChildAt(1);
                button3 = (Button)((TableRow)tableLayout.getChildAt(index)).getChildAt(2);
                break;
            case COL:
                button1 = (Button)((TableRow)tableLayout.getChildAt(0)).getChildAt(index);
                button2 = (Button)((TableRow)tableLayout.getChildAt(1)).getChildAt(index);
                button3 = (Button)((TableRow)tableLayout.getChildAt(2)).getChildAt(index);
                break;
            case DIAG:
                if(index == 1) {
                    button1 = (Button)((TableRow)tableLayout.getChildAt(0)).getChildAt(0);
                    button2 = (Button)((TableRow)tableLayout.getChildAt(1)).getChildAt(1);
                    button3 = (Button)((TableRow)tableLayout.getChildAt(2)).getChildAt(2);
                }  else {
                    button1 = (Button)((TableRow)tableLayout.getChildAt(0)).getChildAt(2);
                    button2 = (Button)((TableRow)tableLayout.getChildAt(1)).getChildAt(1);
                    button3 = (Button)((TableRow)tableLayout.getChildAt(2)).getChildAt(0);
                }
                break;
        }
        int color = result.isWon()? getResources().getColor(R.color.green, getActivity().getTheme()):
                        getResources().getColor(R.color.red, getActivity().getTheme());
        button1.setTextColor(color);
        button2.setTextColor(color);
        button3.setTextColor(color);
    }

    private void disableAllCells() {
        Button button;
        for(int cellId : mCellArr) {
            button = (Button)getActivity().findViewById(cellId);
            button.setEnabled(false);
        }
    }

    private void fillRandomCell() {
        Button cell = getRandomEmptyCell();
        int col = ((TableRow)cell.getParent()).indexOfChild(cell);
        int row = ((TableLayout)getActivity().findViewById(R.id.table_layout)).indexOfChild(((TableRow)cell.getParent()));
        cell.setText("O");
        mTictacbo.set(row, col, TicTacToeBO.Cell.O);
    }

    private Button getRandomEmptyCell() {
        int cellId = -1;
        int index;
        Button cell = null;
        Random random = new Random();
        do {
            index = random.nextInt(mCellArr.length);
            cell = (Button) getActivity().findViewById(mCellArr[index]);
            if(TextUtils.isEmpty(cell.getText())) {
                cellId = mCellArr[index];
            }
        } while(cellId == -1);
        return cell;
    }
    private void mapCells() {
        mCellArr = new int[9];
        mCellArr[0] = R.id.cell00;
        mCellArr[1] = R.id.cell01;
        mCellArr[2] = R.id.cell02;
        mCellArr[3] = R.id.cell10;
        mCellArr[4] = R.id.cell11;
        mCellArr[5] = R.id.cell12;
        mCellArr[6] = R.id.cell20;
        mCellArr[7] = R.id.cell21;
        mCellArr[8] = R.id.cell22;
    }
}
