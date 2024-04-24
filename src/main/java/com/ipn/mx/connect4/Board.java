package com.ipn.mx.connect4;

import java.util.PriorityQueue;

public class Board extends BoardController {

    private int heristic;
    private int currentValue;
    private Board father;

    private PriorityQueue<Board> sons;

    public Board(int iSize, int jSize, Board father) {
        super(iSize, jSize);
        this.father = father;
        this.board = this.father.board.clone();
        this.sons = new PriorityQueue<>();
    }

    public int minMax(int value, int count) {
        if (count != 0) {
            for (int j = 0; j < this.jSize; j++) {
                Board tmp = new Board(iSize, jSize, this);
                int i = tmp.setPiece(j, value);
                if (i != -1) {
                    this.sons.add(tmp);
                    this.board[i][j] = value;
                    int currentHeuristic = this.minMax(-value, --count);
                }
            }
        } else {
            return getHeuristic();
        }
        return getHeuristic();
    }

    public int getHeuristic() {
        int value;
        int counter = 0;

        int botCounter = 0;
        int playerCounter = 0;

        for (int i = iSize - 1; i >= 0; i--) {
            for (int j = 0; j < jSize; j++) {

                value = board[i][j];

                //Right counter
                for (int k = 1; k < 4 && j + 3 < jSize; k++) {
                    int jNew = j + k;
                    if (board[i][jNew] == -value) {
                        counter = 0;        // Heuristica 0                      
                        break;
                    } else if (board[i][jNew] == value) {
                        counter++;
                    }
                }

                //Right Up
                for (int k = 1; k < 4 && i - 3 >= 0 && j + 3 < jSize; k++) {
                    int iNew = i - k;
                    int jNew = j + k;
                    if (board[iNew][jNew] == -value) {
                        counter = 0;        // Heuristica 0                      
                        break;
                    } else if (board[iNew][jNew] == value) {
                        counter++;
                    }
                }

                //Up
                for (int k = 1; k < 4 && i - 3 >= 0; k++) {
                    int iNew = i - k;
                    if (board[iNew][j] == -value) {
                        counter = 0;        // Heuristica 0                      
                        break;
                    } else if (board[iNew][j] == value) {
                        counter++;
                    }
                }

                //Left Up
                for (int k = 1; k < 4 && i - 3 >= 0 && j - 3 >= 0; k++) {
                    int iNew = i - k;
                    int jNew = j - k;
                    if (board[iNew][jNew] == -value) {
                        counter = 0;        // Heuristica 0                      
                        break;
                    } else if (board[iNew][jNew] == value) {
                        counter++;
                    }
                }
                
                
                if (value == -1) {
                    botCounter += value;
                } else {
                    playerCounter += value;
                }

            }
        }

        return botCounter - playerCounter;
    }

    public void setHeuristic(int heuristic) {
        this.heristic = heuristic;
    }
}
