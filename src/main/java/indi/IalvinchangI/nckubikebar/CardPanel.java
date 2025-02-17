package indi.IalvinchangI.nckubikebar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JPanel;

import indi.IalvinchangI.nckubikebar.tools.PaintTools;


/**
 * the panel to show the card
 * 
 * @author IalvinchangI
 */
public class CardPanel extends JPanel {
    
    private String[] cardPathTabel = null;
    private Image cardImage = null;

    /**
     * create CardPanel 
     * 
     * @param cardPathTabel the mapping tabel between label indices and the corresponding card paths
     */
    public CardPanel(String[] cardPathTabel) {
        this.cardPathTabel = cardPathTabel;
        this.setBackground(Color.BLACK);
    }
    
    /**
     * show the card by specifying the label index
     * 
     * @param index the label index
     */
    public void showCard(int index) {
        this.cardImage = PaintTools.getImage(this.cardPathTabel[index]);
        repaint();
    }


    /** the margin of card {up and down, left and right} */
    private static final float[] MARGIN = new float[] {0.01f, 0.1f};


    @Override
    protected void paintComponent(Graphics g) {  // paint card
        // setting
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        if (this.cardImage != null) {
            Dimension panelSize = this.getSize();

            // zoom cardImage
            int cardWidth = this.cardImage.getWidth(null);
            int cardHeight = this.cardImage.getHeight(null);
            float zoomRatio = Math.min(
                (1 - MARGIN[1] * 2) * panelSize.width / cardWidth,   // width ratio
                (1 - MARGIN[0] * 2) * panelSize.height / cardHeight  // height ratio
            );
            Image zoomCardImage = this.cardImage.getScaledInstance(
                (int)(cardWidth * zoomRatio), (int)(cardHeight * zoomRatio), 
                Image.SCALE_SMOOTH
            );

            g2d.drawImage(
                zoomCardImage, 
                (panelSize.width - zoomCardImage.getWidth(null)) / 2, 
                (panelSize.height - zoomCardImage.getHeight(null)) / 2, 
                null
            );
        }
    }
}
