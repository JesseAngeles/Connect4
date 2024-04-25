package com.ipn.mx.connect4;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

class GraphicInterface extends JFrame {

    private final int IMG_SIZE = 64;
    private final int WINDOW_WIDTH = 470;
    private final int WINDOW_HEIGHT = 420;

    private final int iSize = 6;
    private final int jSize = 7;

    // Cambiar ruta al modificar versiones
    private final ImageIcon iconEmpty = new ImageIcon("assets/empty.png");
    private final ImageIcon bluePlayer = new ImageIcon("assets/bluePlayer.png");
    private final ImageIcon redPlayer = new ImageIcon("assets/redPlayer.png");

    private final Matrix matrix;

    private final GameController gameController;
    
    public GraphicInterface() {
        super("Busqueda adversaria");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setVisible(true);
        setLocationRelativeTo(null);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        this.matrix = new Matrix(iSize, jSize);
        add(initboardGridPanel());

        this.gameController = new GameController(iSize, jSize, this);
    }
    
    private JPanel initboardGridPanel() {
        JPanel boardGrid = new JPanel();

        boardGrid.setLayout(new GridLayout(this.iSize, this.jSize));

        Image imageDefault = iconEmpty.getImage().getScaledInstance(IMG_SIZE, IMG_SIZE, Image.SCALE_SMOOTH);

        // Botones superiores
        for (int j = 0; j < this.jSize; j++) {
            int jPos = j;

            JButton button = new JButton();

            button.setIcon(new ImageIcon(imageDefault));
            button.setBorderPainted(true);
            button.setContentAreaFilled(false);
            button.setFocusPainted(false);

            // Controlador de juego
            button.addActionListener((e) -> {
                this.gameController.playerMove(jPos);
            });

            boardGrid.add(button);

            this.matrix.setJButton(j, button);
            //System.out.print("(0," + j + ") ");
        }
        //System.out.println("");

        // Tablero inferior
        for (int i = 1; i < this.iSize; i++) {
            for (int j = 0; j < this.jSize; j++) {
                JLabel label = new JLabel();

                label.setIcon(new ImageIcon(imageDefault));
                boardGrid.add(label);

                this.matrix.setJLabel(i, j, label);
                //System.out.print("(" + i + ',' + j + ") ");
            }
            //System.out.println("");
        }

        boardGrid.setSize(new Dimension(IMG_SIZE * iSize, IMG_SIZE * jSize));

        return boardGrid;
    }

    /*
    * @param i Posición en el eje i
    * @param j Posición en el eje j
    * @param value  Imagen a colocar
                    0 -> Empty
                    1 -> Blue
                   -1 -> Red
     */
    public void updateBoard(int i, int j, int value) {
        Image newImage;
        switch (value) {
            case 0:
                newImage = iconEmpty.getImage().getScaledInstance(IMG_SIZE, IMG_SIZE, Image.SCALE_SMOOTH);
                break;
            case 1:
                newImage = bluePlayer.getImage().getScaledInstance(IMG_SIZE, IMG_SIZE, Image.SCALE_SMOOTH);
                break;
            case -1:
                newImage = redPlayer.getImage().getScaledInstance(IMG_SIZE, IMG_SIZE, Image.SCALE_SMOOTH);
                break;
            default:
                throw new AssertionError();
        }

        if (i == 0) {       // Se modifica un boton
            this.matrix.getButtonByIndex(j).setIcon(new ImageIcon(newImage));
        } else {            // Se modifica un label
            this.matrix.getLabelByIndex(i, j).setIcon(new ImageIcon(newImage));
        }
    }

    public void cleanBoard() {
        Image newImage = iconEmpty.getImage().getScaledInstance(IMG_SIZE, IMG_SIZE, Image.SCALE_SMOOTH);
        
        for (int j = 0; j < this.jSize; j++) {
            this.matrix.getButtonByIndex(j).setIcon(new ImageIcon(newImage));
        }
        
        for (int i = 1; i < this.iSize; i++) {
            for (int j = 0; j < this.jSize; j++) {
                this.matrix.getLabelByIndex(i, j).setIcon(new ImageIcon(newImage));
            }
        }
    }

    /*
    * @param winner Numero de ganador
                    1 -> Blue
                   -1 -> Red
     */
    public void winMessage(int winner) {
        String message = "¡Gano el jugador ";
        if (winner == 1) {
            message = message.concat("Azul!");
        } else {
            message = message.concat("Rojo!");
        }
        JOptionPane.showMessageDialog(this, message);
    }

}
