package indi.IalvinchangI.nckubikebar.style;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.plaf.synth.SynthContext;
import javax.swing.plaf.synth.SynthPainter;

import indi.IalvinchangI.nckubikebar.tools.GUIConstant;


/**
 * a SynthPainter that paints the border and background of the button
 * 
 * @author IalvinchangI
 */
public class ButtonPainter extends SynthPainter implements GUIConstant {

    public static final Color BACKGROUND = GUIConstant.BACKGROUND;
    public static final Color BORDER = TEXT_COLOR;
    
    public static final float ARC_DIAMETER_RATIO = 0.3f;

    public static final int STROKE_RADIUS = 1;

    public void paintButtonBackground(SynthContext context, Graphics g, int x, int y, int width, int height) {
        int arcDiameter = (int)(Math.min(width, height) * ARC_DIAMETER_RATIO);
        this.paintBackground(g, x, y, width, height, BACKGROUND, arcDiameter);
    }
    
    public void paintButtonBorder(SynthContext context, Graphics g, int x, int y, int width, int height) {
        int arcDiameter = (int)(Math.min(width, height) * ARC_DIAMETER_RATIO);
        this.paintBorder(g, x, y, width, height, BORDER, arcDiameter);
    }
    
    /** paint background */
    protected void paintBackground(Graphics g, int x, int y, int width, int height, Color color, int arcDiameter) {
        Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2d.setColor(color);
        g2d.fillRoundRect(
            x + STROKE_RADIUS, y + STROKE_RADIUS, 
            width - STROKE_RADIUS * 2 - 1, height - STROKE_RADIUS * 2 - 1, 
            arcDiameter, arcDiameter
        );
    }

    /** paint border */
    protected void paintBorder(Graphics g, int x, int y, int width, int height, Color color, int arcDiameter) {
        Graphics2D g2d = (Graphics2D)g;
        g2d.setStroke(new BasicStroke(STROKE_RADIUS * 2));
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(color);
        g2d.drawRoundRect(
            x + STROKE_RADIUS, y + STROKE_RADIUS, 
            width - STROKE_RADIUS * 2 - 1, height - STROKE_RADIUS * 2 - 1, 
            arcDiameter, arcDiameter
        );
    }
}
