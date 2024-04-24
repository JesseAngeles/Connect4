package com.ipn.mx.connect4;

public class GameController {

    private int turn;
    /*
        1 -> player
       -1 -> bot
     */

    private final GraphicInterface graphicController;
    private final BoardController boardController;

    public GameController(int iSize, int jSize, GraphicInterface graphicController) {
        this.turn = 1;

        this.boardController = new BoardController(iSize, jSize);
        this.graphicController = graphicController;
    }

    public void move(int jPos) {
        int iPos = this.boardController.setPiece(jPos, this.turn);
        if (iPos != -1) {
            if (this.turn == 1) {
                // Movimiento del jugador
                this.graphicController.updateBoard(iPos, jPos, this.turn);
                if (boardController.winVerification() == 1) {
                    this.boardController.resetBoard();
                    this.graphicController.cleanBoard();
                    this.graphicController.winMessage(this.turn);
                    return;
                }
                this.turn = -1;
            }
            if (this.turn == -1) {
                // Movimiento del bot
                // Escoge Minimax
                
                System.out.println("");
                
                this.turn = 1;
            }

        } else {
            System.out.println("Column fill");
        }
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }
}
