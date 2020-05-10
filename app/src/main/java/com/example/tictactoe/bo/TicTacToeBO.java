package com.example.tictactoe.bo;

import java.util.ArrayList;
import java.util.List;

public class TicTacToeBO {

    public static final int DIM = 3;
    public static final int DIAG = 1;
    public static final int ANTI_DIAG = 2;

    public enum Cell {
        Empty,
        X,
        O
    }

    public enum Dimension {
        ROW,
        COL,
        DIAG
    }

    public class Result {
        int index;
        Dimension dimension;
        boolean isWon;

        Result(int in, Dimension dim, boolean isWon) {
            index = in;
            dimension = dim;
            this.isWon = isWon;
        }

        public int getIndex() {
            return index;
        }

        public Dimension getDimension() {
            return dimension;
        }

        public boolean isWon() {
            return isWon;
        }
    }

    private List<List<Cell>> cells;
    private Result mResult;

    public TicTacToeBO() {
        cells = new ArrayList<>(3);
        List<Cell> row;
        for(int i = 0; i< DIM; i++) {
            row = new ArrayList<>(3);
            for(int j=0; j<DIM; j++) {
                row.add(Cell.Empty);
            }
            cells.add(row);
        }
    }

    public void set(int i, int j, Cell val) {
        if(cells.get(i).get(j) != Cell.Empty) {
            throw new IllegalArgumentException("Cell not empty");
        }
        List<Cell> row = cells.get(i);
        row.set(j, val);
        cells.set(i, row);
    }

    public boolean isAllFilled() {
        for(int i=0; i<DIM; i++) {
            for(int j=0; j<DIM; j++) {
                if(cells.get(i).get(j) == Cell.Empty) {
                    return false;
                }
            }
        }
        return true;
    }

    public Result getResult() {
        if(mResult == null) {
            throw new NullPointerException("Result is not set");
        }
        return mResult;
    }

    public boolean isComplete() {
        int completedRow, completedCol, completedDiag;
        completedRow = getCompletedRow();
        if(completedRow != -1) {
            mResult = new Result(completedRow, Dimension.ROW, cells.get(completedRow).get(0) == Cell.X);
            return true;
        }
        completedCol = getCompltedCol();
        if(completedCol != -1) {
            mResult = new Result(completedCol, Dimension.COL, cells.get(0).get(completedCol) == Cell.X);
            return true;
        }
        completedDiag = getCompletedDiag();
        if(completedDiag != -1) {
            Cell val = completedDiag == DIAG? cells.get(0).get(0): cells.get(0).get(2);
            mResult = new Result(completedDiag, Dimension.DIAG, val == Cell.X);
            return true;
        }

        return false;
    }

    private int getCompletedRow() {
        boolean isComplete = false;
        Cell value;
        for(int i=0; i<DIM; i++) {
            value = cells.get(i).get(0);
            if(value != Cell.Empty) {
                isComplete = true;
                for(int j=1; j< DIM ; j++) {
                    if(cells.get(i).get(j) != value) {
                        isComplete = false;
                        break;
                    }
                }
                if(isComplete) {
                    return i;
                }
            }
        }
        return -1;
    }

    private int getCompltedCol() {
        boolean isComplete = false;
        Cell value;
        for(int j=0; j<DIM; j++) {
            value = cells.get(0).get(j);
            if(value != Cell.Empty) {
                isComplete = true;
                for(int i=1; i< DIM ; i++) {
                    if(cells.get(i).get(j) != value) {
                        isComplete = false;
                        break;
                    }
                }
                if(isComplete) {
                    return j;
                }
            }
        }
        return -1;
    }

    private int getCompletedDiag() {
        int i = 0, j = 0;
        Cell value = cells.get(i).get(j);

        boolean isComplete = true;
        if (value != Cell.Empty) {
            while (i < DIM && j < DIM) {
                if (cells.get(i).get(j) != value) {
                    isComplete = false;
                    break;
                }
                i++;
                j++;
            }
        } else {
            isComplete = false;
        }

        if (isComplete) {
            return DIAG;
        }

        //check for anti diagonal
        isComplete = true;
        i = 0;
        j = DIM - 1;
        value = cells.get(i).get(j);
        if (value != Cell.Empty) {
            while (i < DIM && j >= 0) {
                if (cells.get(i).get(j) != value) {
                    isComplete = false;
                    break;
                }
                i++;
                j--;
            }
        } else {
            isComplete = false;
        }

        if(isComplete) {
            return ANTI_DIAG;
        }
        return -1;
    }
}
