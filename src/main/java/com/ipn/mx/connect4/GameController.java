package com.ipn.mx.connect4;

public class GameController {

    private final GraphicInterface graphicController;
    private BoardController board;

    public GameController(int iSize, int jSize, GraphicInterface graphicController) {
        this.graphicController = graphicController;
        this.board = new BoardController(iSize, jSize, null);
    }
    
    public void playerMove(int jPos) {
        int iPos = board.getIPos(jPos);
        if (iPos != -1) {
            if (movementValidation(iPos, jPos, 1)) {
                botMove();
            }
        } else {
            System.out.println("Column is full, choose another.");
        }
    }

    private void botMove() {
        int jPos = board.getNextPos();
        int iPos = board.getIPos(jPos);
        if (iPos != -1) {
            movementValidation(iPos, jPos, -1);
        } else {
            System.out.println("Bot has no moves left.");
        }
    }

    public boolean movementValidation(int iPos, int jPos, int turn) {
        board.setPiece(iPos, jPos, turn);
        graphicController.updateBoard(iPos, jPos, turn);
        board.printBoard();

        if (board.winVerification() == turn) {
            graphicController.winMessage(turn);
            resetGame();
            return false;
        }
        return true;
    }

    private void resetGame() {
        board.resetBoard();
        graphicController.cleanBoard();
    }
}
