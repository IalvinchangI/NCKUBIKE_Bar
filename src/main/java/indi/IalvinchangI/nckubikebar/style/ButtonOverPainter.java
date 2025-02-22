package indi.IalvinchangI.nckubikebar.style;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.plaf.synth.SynthContext;


/**
 * a SynthPainter that paints the border and background of the button when the mouse hovers over it
 * 
 * @author IalvinchangI
 */
public class ButtonOverPainter extends ButtonPainter {
    
    public static final Color BACKGROUND = DARK_BACKGROUND;

    public void paintButtonBackground(SynthContext context, Graphics g, int x, int y, int width, int height) {
        int arcDiameter = (int)(Math.min(width, height) * ARC_DIAMETER_RATIO);
        this.paintBackground(g, x, y, width, height, BACKGROUND, arcDiameter);
    }

    public void paintButtonBorder(SynthContext context, Graphics g, int x, int y, int width, int height) {
        int arcDiameter = (int)(Math.min(width, height) * ARC_DIAMETER_RATIO);
        this.paintBorder(g, x, y, width, height, BORDER, arcDiameter);
    }
}
