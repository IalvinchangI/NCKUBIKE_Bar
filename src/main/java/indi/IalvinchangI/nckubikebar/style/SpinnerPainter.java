package indi.IalvinchangI.nckubikebar.style;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.SwingConstants;
import javax.swing.plaf.synth.SynthContext;
import javax.swing.plaf.synth.SynthPainter;

import indi.IalvinchangI.nckubikebar.tools.GUIConstant;


/**
 * a SynthPainter that paints the border, background, and arrow buttons of the spinner
 * 
 * @author IalvinchangI
 */
public class SpinnerPainter extends SynthPainter implements GUIConstant {

    public static final Color BACKGROUND = GUIConstant.BACKGROUND;
    public static final Color BORDER = TEXT_COLOR;
    public static final Color ARROW = TEXT_COLOR;
    
    public static final int ARC_DIAMETER = 10;

    public static final int STROKE_RADIUS = 1;

    public static final float ARROW_WIDTH_RATIO = 0.6f;
    public static final float ARROW_HEIGHT_RATIO = 0.6f;
    public static final float ARROW_X_MARGIN_RATIO = 0.2f;
    public static final float ARROW_Y_MARGIN_RATIO = 0.2f;

    public void paintSpinnerBackground(SynthContext context, Graphics g, int x, int y, int width, int height) {
        this.paintBackground(g, x, y, width, height, BACKGROUND, ARC_DIAMETER);
    }
    
    public void paintSpinnerBorder(SynthContext context, Graphics g, int x, int y, int width, int height) {
        this.paintBorder(g, x, y, width, height, BORDER, ARC_DIAMETER);
    }

    public void paintArrowButtonForeground(SynthContext context, Graphics g, int x, int y, int width, int height, int direction) {
        Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int arrowWidth = (int)(width * ARROW_WIDTH_RATIO);
        int arrowHeight = (int)(height * ARROW_HEIGHT_RATIO);
        int startX = (int)(width * ARROW_X_MARGIN_RATIO);
        int startY = (int)(height * ARROW_Y_MARGIN_RATIO);

        g2d.setColor(ARROW);
        if (direction == SwingConstants.NORTH) {
            startY += (int)(height * 0.2);
            g2d.fillPolygon(
                new int[]{startX, startX + arrowWidth / 2, startX + arrowWidth}, 
                new int[]{startY + arrowHeight, startY, startY + arrowHeight}, 
                3
            );
        }
        else if (direction == SwingConstants.SOUTH) {
            g2d.fillPolygon(
                new int[]{startX, startX + arrowWidth / 2, startX + arrowWidth}, 
                new int[]{startY, startY + arrowHeight, startY}, 
                3
            );
        }
        // g2d.drawRect(x, y, width, height);
    }
    
    /** paint background */
    protected void paintBackground(Graphics g, int x, int y, int width, int height, Color color, int arcDiameter) {
        Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2d.setColor(color);
        g2d.drawRoundRect(
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
