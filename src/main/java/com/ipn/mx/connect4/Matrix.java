package com.ipn.mx.connect4;

import javax.swing.JButton;
import javax.swing.JLabel;

public class Matrix {

    public JButton[] buttons;
    public JLabel[][] labels;
    
    public Matrix(int i, int j) {
        buttons = new JButton[j];
        labels = new JLabel[i][j];
    }
    
    public void setJButton(int i, JButton button) {
        this.buttons[i] = button;
    }
    
    public JButton getButtonByIndex(int i) {
        return this.buttons[i];
    }
    
    public void setJLabel(int i, int j, JLabel label) {
        this.labels[i][j] = label;
    }
    
    public JLabel getLabelByIndex(int i, int j) {
        return this.labels[i][j];
    }
        
}
