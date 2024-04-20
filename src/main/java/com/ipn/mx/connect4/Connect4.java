package com.ipn.mx.connect4;

public class Connect4 {

    public static void main(String[] args) {
        GraphicInterface graphic = new GraphicInterface();
        
        graphic.updateBoard(0, 0, 1);
        
        graphic.updateBoard(1, 1, -1);
        graphic.winMessage(1);
    }
}
