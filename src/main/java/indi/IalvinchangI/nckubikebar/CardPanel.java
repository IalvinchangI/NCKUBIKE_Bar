package indi.IalvinchangI.nckubikebar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
// import java.awt.geom.RoundRectangle2D;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import indi.IalvinchangI.nckubikebar.tools.PaintTools;


/**
 * the panel to show the card
 * 
 * @author IalvinchangI
 */
public class CardPanel extends JPanel {
    
    private String[] cardPathTabel = null;
    private HashMap<Integer, Image> cardImages = null;

    /**
     * create CardPanel 
     * 
     * @param cardPathTabel the mapping tabel between label indices and the corresponding card paths
     */
    public CardPanel(String[] cardPathTabel) {
        this.cardPathTabel = cardPathTabel;
        this.cardImages = new HashMap<>();
        this.setBackground(new Color(0xf6ebb4));
    }
    
    /**
     * show the card by specifying the label indices
     * 
     * @param indices a list of label index
     */
    public void showCard(int[] indices) {
        this.cardImages.clear();
        for (int index : indices) {
            this.cardImages.put(index, PaintTools.getImage(this.cardPathTabel[index]));
        }
        repaint();
    }

    /**
     * erase cards
     */
    public void clear() {
        this.cardImages.clear();
        repaint();
    }


    /** the margin of card {up and down, left and right} */
    private static final float[] MARGIN = new float[] {0.01f, 0.01f};
    /** the arrangement of cards {column, row} */
    private static final int[] CARD_GRID = new int[] {3, 2};
    // private static final float ARC_DIAMETER_RATIO = 0.05f;


    @Override
    protected void paintComponent(Graphics g) {  // paint card
        // setting
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);

        if (this.cardImages.isEmpty() == false) {
            Dimension panelSize = this.getSize();
            Dimension slotSize = new Dimension(panelSize.width / CARD_GRID[0], panelSize.height / CARD_GRID[1]);
            
            for (Map.Entry<Integer, Image> entry : this.cardImages.entrySet()) {
                int index = entry.getKey();
                Image cardImage = entry.getValue();

                // zoom cardImage
                int cardWidth = cardImage.getWidth(null);
                int cardHeight = cardImage.getHeight(null);
                float zoomRatio = Math.min(
                    (1 - MARGIN[1] * 2) * slotSize.width / cardWidth,   // width ratio
                    (1 - MARGIN[0] * 2) * slotSize.height / cardHeight  // height ratio
                );
                Image zoomCardImage = cardImage.getScaledInstance(
                    (int)(cardWidth * zoomRatio), (int)(cardHeight * zoomRatio), 
                    Image.SCALE_SMOOTH
                );

                // x, y
                Dimension zoomCardImageSize = new Dimension(zoomCardImage.getWidth(null), zoomCardImage.getHeight(null));
                int x = (slotSize.width - zoomCardImageSize.width) / 2 + slotSize.width * (index % CARD_GRID[0]);
                int y = (slotSize.height - zoomCardImageSize.height) / 2 + slotSize.height * (index / CARD_GRID[0]);
                // int arc_diameter = (int) (Math.min(zoomCardImageSize.width, zoomCardImageSize.height) * ARC_DIAMETER_RATIO);

                // g2d.setClip(new RoundRectangle2D.Float(
                //     x, y, 
                //     zoomCardImageSize.width, zoomCardImageSize.height, 
                //     arc_diameter, arc_diameter
                // ));
                g2d.drawImage(zoomCardImage, x, y, null);
            }
        }

        g2d.dispose();
    }
}
