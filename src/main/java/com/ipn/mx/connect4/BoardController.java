package com.ipn.mx.connect4;

import java.util.HashSet;
import java.util.Set;

public class BoardController {

    private int[][] board;

    public BoardController(int i, int j) {
        this.board = new int[i][j];
    }

    public void resetBoard() {
        for (int[] row : board) {
            for (int pos : row) {
                pos = 0;
            }
        }
    }

    @SuppressWarnings("empty-statement")
    public int setPiece(int j, int player) {
        if (board[0][j] == 0) {         // No hay pieza en el tope
            int i = 0;
            while (board[++i][j] == 0);
            board[i - 1][j] = player;
            return i;
        } else {                        // ERROR: hay pieza en el borde
            return -1;
        }
    }

    @SuppressWarnings("empty-statement")
    public int winVerification() {
        int player = 0;
        int size;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {

                int value = board[i][j];

                if (value != 0) {
                    size = 0;
                    while (board[i][j + ++size] == value) {
                        if (size == 3) {
                            return value;
                        }
                    }
                    
                    size = 0;
                    while (board[i + ++size][j] == value) {
                        if (size == 3) {
                            return value;
                        }
                    }
                    
                    size = 1;
                    while (board[i + size][j + size] == value) {                        
                        if (size == 3) {
                            return value;
                        }
                        size++;
                    }
                }
                
                
            }
        }

        return 0;
    }

    public void printBoard() {
        System.out.println("Tablero: ");
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print("" + board[i][j]);
            }
            System.out.println("");
        }
    }
}
