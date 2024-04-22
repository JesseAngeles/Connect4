package com.ipn.mx.connect4;

public class GameController {

    private int turn;

    private final GraphicInterface graphicController;
    private final BoardController boardController;

    public GameController(int iSize, int jSize, GraphicInterface graphicController) {
        this.turn = 1;

        this.boardController = new BoardController(iSize, jSize);
        this.graphicController = graphicController;
    }

    public void move(int jPos) {
        int iPos = this.boardController.setPiece(jPos, turn);
        if (iPos != -1) {   // La columna esta vacia
            this.graphicController.updateBoard(iPos, jPos, turn);

            turn = -turn;

            if (boardController.winVerification() != 0) {
                this.boardController.resetBoard();
                this.graphicController.cleanBoard();
                this.graphicController.winMessage(turn);
                this.turn = 1;
            }

            this.boardController.printBoard();
        } else {        // La columna esta llena
            System.out.println("Column fill");
        }
    }
}
