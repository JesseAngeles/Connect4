    package com.ipn.mx.connect4;

    public class BoardController {

        protected int iSize;
        protected int jSize;
        protected int[][] board;

        public BoardController(int i, int j) {
            this.iSize = i;
            this.jSize = j;
            this.board = new int[i][j];
        }

        public void resetBoard() {
            this.board = new int[this.iSize][this.jSize];
        }

        public int setPiece(int j, int player) {
            if (board[0][j] == 0) {         // No hay pieza en el tope
                System.out.println("this.iSize: " + this.iSize);
                int pos = 0;
                for (int i = 0; i < this.iSize; i++) {
                    if (board[i][j] != 0) {
                        break;
                    }
                    pos++;
                }

                System.out.println("pos: " + pos);
                board[pos - 1][j] = player;
                System.out.println("4");
                return pos - 1;
            } else {                        // ERROR: hay pieza en el borde
                return -1;
            }
        }

        public int winVerification() {
            // Recorremos todas las casillas
            for (int iPos = 0; iPos < board.length; iPos++) {
                for (int jPos = 0; jPos < board[iPos].length; jPos++) {

                    int value = board[iPos][jPos]; 

                    // Si no esta vacia se buscan similitudes en sus vecinos        
                    if (value != 0) {
                        //Right Validation
                        for (int count = 1; count < 4 && jPos + count < this.jSize; count++) {
                            if (board[iPos][jPos + count] != value) {
                                break;
                            }
                            if (count == 3) {
                                return value;
                            }
                        }

                        //Left Down Validation
                        for (int count = 1; count < 4 && iPos + count < this.iSize && jPos - count >= 0; count++) {
                            if (board[iPos + count][jPos - count] != value) {
                                break;
                            }
                            if (count == 3) {
                                return value;
                            }
                        }

                        //Down Validation
                        for (int count = 1; count < 4 && iPos + count < this.iSize; count++) {
                            if (board[iPos + count][jPos] != value) {
                                break;
                            }
                            if (count == 3) {
                                return value;
                            }
                        }

                        //Righ Down Validation 
                        for (int count = 1; count < 4 && iPos + count < this.iSize && jPos + count < this.jSize; count++) {
                            if (board[iPos + count][jPos + count] != value) {
                                break;
                            }
                            if (count == 3) {
                                return value;
                            }
                        }
                    }
                }
            }

            return 0;
        }

        public void printBoard() {
        System.out.println("Tablero:");
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(String.format("%2d ", board[i][j]));
            }
            System.out.println(); 
        }
    }

    }
