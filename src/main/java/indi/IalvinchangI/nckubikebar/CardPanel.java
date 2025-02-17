package indi.IalvinchangI.nckubikebar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JPanel;

import indi.IalvinchangI.nckubikebar.tools.PaintTools;

public class CardPanel extends JPanel {
    
    private String[] cardPathTabel = null;
    private Image cardImage = null;

    public CardPanel(String[] cardPathTabel) {
        this.cardPathTabel = cardPathTabel;
        this.setBackground(Color.WHITE);
    }
    
    public void showCard(int index) {
        this.cardImage = PaintTools.getImage(this.cardPathTabel[index]);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {  // paint card
        // setting
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        if (this.cardImage != null) {
            Dimension panelSize = this.getSize();
            g2d.drawImage(
                this.cardImage, 
                (panelSize.width - this.cardImage.getWidth(null)) / 2, 
                (panelSize.height - this.cardImage.getHeight(null)) / 2, 
                null
            );
        }
    }
}
