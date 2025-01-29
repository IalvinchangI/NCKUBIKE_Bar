package indi.IalvinchangI.nckubikebar.tools;

import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;


/**
 * static paint function
 * 
 * @author IalvinchangI
 */
public class PaintTools {
    
    /**
     * draw text at the given center point
     * 
     * @param targetG2D where to draw
     * @param text 
     * @param centerX center point x
     * @param centerY center point y
     */
    public static void drawStringAtCenter(Graphics2D targetG2D, String text, int centerX, int centerY) {
        FontMetrics fm = targetG2D.getFontMetrics();
        Rectangle stringBounds = fm.getStringBounds(text, targetG2D).getBounds();
        int textX = centerX - stringBounds.width / 2;
        int textY = centerY - stringBounds.height / 2 + fm.getAscent();
        targetG2D.drawString(text, textX, textY);
    }
}
