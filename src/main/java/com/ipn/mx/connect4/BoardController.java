package com.ipn.mx.connect4;

import java.util.Random;

public class BoardController {

    private final int iSize;
    private final int jSize;

    protected int heuristic;

    private int[][] board;

    private BoardController[] children;

    public BoardController(int iSize, int jSize, BoardController father) {
        this.iSize = iSize;
        this.jSize = jSize;
        children = new BoardController[jSize];

        if (father != null) {
            this.board = new int[father.board.length][];
            for (int i = 0; i < father.board.length; i++) {
                this.board[i] = father.board[i].clone();
            }
        } else {
            this.board = new int[iSize][jSize];
        }

    }

    private int getPosMaxHeuristic(BoardController board) {
    if (board == null || board.children == null || !board.hasChildren(board)) {
        return -1; // Retorna -1 para indicar que no hay elementos válidos
    }

    int posMax = -1;
    int max = Integer.MIN_VALUE; // Usa el valor más bajo posible para inicializar
    for (int i = 0; i < board.children.length; i++) {
        if (board.children[i] != null && board.children[i].heuristic > max) {
            max = board.children[i].heuristic;
            posMax = i;
        }
    }

    return posMax;
}

    private int getPosMinHeuristic(BoardController board) {
    if (board == null || board.children == null || !board.hasChildren(board)) {
        return -1; // Retorna -1 para indicar que no hay elementos válidos
    }

    int posMin = -1;
    int min = Integer.MAX_VALUE; // Usa el valor más alto posible para inicializar
    for (int i = 0; i < board.children.length; i++) {
        if (board.children[i] != null && board.children[i].heuristic < min) {
            min = board.children[i].heuristic;
            posMin = i;
        }
    }

    return posMin;
}


    public void resetBoard() {
        this.board = new int[this.iSize][this.jSize];
    }

    public int getIPos(int j) {
        if (board[0][j] == 0) {         // No hay pieza en el tope
            int pos = 0;
            for (int i = 0; i < this.iSize; i++) {
                if (board[i][j] != 0) {
                    break;
                }
                pos++;
            }

            return pos - 1;
        } else {                        // ERROR: hay pieza en el borde
            return -1;
        }
    }

    public void setPiece(int i, int j, int turn) {
        this.board[i][j] = turn;
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

    public int getNextPos() {
        generateChildren(this, 1, 3);
        return getPosMaxHeuristic(this);
    }

    private void generateChildren(BoardController father, int turn, int deep) {
        if (deep > 0) {
            System.out.println("Deep: " + deep);

            for (int jPos = 0; jPos < this.jSize; jPos++) {
                BoardController boardSon = new BoardController(iSize, jSize, father);
                int iPos = boardSon.getIPos(jPos);
                if (iPos != -1) {                                       // Se puede poner
                    boardSon.setPiece(iPos, jPos, turn);
                    generateChildren(boardSon, -turn, deep - 1);
                    boardSon.heuristic = boardSon.getHeuristic();
                    if (hasChildren(boardSon)) {
                        if (turn == 1) {                                    // Obtener minimo
                            boardSon.heuristic = boardSon.children[getPosMinHeuristic(boardSon)].heuristic;
                        } else {
                            boardSon.heuristic = boardSon.children[getPosMaxHeuristic(boardSon)].heuristic;
                        }
                    }
                    father.children[jPos] = boardSon;
                System.out.println("(" + iPos + "," + jPos + ") = " + boardSon.heuristic);
                } else {
                    System.out.println("Column Fill");
                }
            }
            System.out.println("");
        } else {
            father.children = null;
        }
    }

    private boolean hasChildren(BoardController board) {
        if (board == null || board.children == null) {
            return false;  // Retornar false si el objeto board o su arreglo de hijos es nulo.
        }
        for (BoardController child : board.children) {
            // Usar children.length para seguridad.
            if (child != null) {
                return true;  // Retornar true al encontrar el primer hijo no nulo.
            }
        }
        return false;  // Retornar false si no se encuentra ningún hijo no nulo.
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
        Random random = new Random();
        // Genera un número aleatorio entre -20 y 20
        return random.nextInt(41) - 20;
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

    public BoardController getChild(int index) {
        return this.children[index];
    }
}
