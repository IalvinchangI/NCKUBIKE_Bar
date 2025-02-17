package indi.IalvinchangI.nckubikebar;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import indi.IalvinchangI.nckubikebar.tools.GUIConstant;
import indi.IalvinchangI.nckubikebar.tools.PaintTools;


/**
 * the control window for the bar chart 
 * 
 * @author IalvinchangI
 */
public class ControlFrame extends JFrame implements GUIConstant {

    public static final String[] LABELS = {
        "體力", "知識", "社交", "多巴胺", "零用錢", "軟實力", 
    };
    public static final String[] TITLES = {
        "Mon", };//"Tue", "Wed", "Thu", "Fri", "Sat", 
    // };
    public static final String[] CARD_PATH_TABLE = {
        "/cards/c1.png", 
        "/cards/c2.png", 
        "/cards/c3.png", 
        "/cards/c4.png", 
        "/cards/c5.png", 
        "/cards/c6.png"
    };

    private static final String ICON_PATH = "/BarChart.png";
    /** the size of drawButton and clearButton */
    private static final Dimension BUTTON_SIZE = new Dimension(150, 30);

    private static final String DRAW_TEXT = "繪製";
    private static final String SHOW_TEXT = "顯示結果";
    private static final String CLEAR_TEXT = "清除";

    private InputPanel input = null;
    private JButton drawButton = null;
    private JButton clearButton = null;

    private ShowFrame showFrame = null;
    
    /**
     * create ControlFrame
     */
    public ControlFrame() {
        // setting
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(500, 500);
        this.rootPane.setBackground(BACKGROUND);
        this.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.CENTER;

        // init icon
        Image icon = PaintTools.getImage(ICON_PATH);

        // input panel
        this.input = new InputPanel(LABELS);
        constraints.gridx = 0;
        constraints.gridy = 0;
        this.add(this.input, constraints);

        // margin
        constraints.gridy = 1;
        this.add(Box.createVerticalStrut(10), constraints);
        
        // buttons
        constraints.gridy = 2;
        this.add(this.initButtonPanel(), constraints);
        
        // show frame
        this.showFrame = new ShowFrame(LABELS, CARD_PATH_TABLE);
        
        // set icon
        if (icon != null) {
            this.setIconImage(icon);
            this.showFrame.setIconImage(icon);
        }

        // show show frame
        this.showFrame.setVisible(true);
    }

    /**
     * initialize the button panel which contains drawButton and cleanButton
     * 
     * @return button panel
     */
    private JPanel initButtonPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        // draw button
        this.drawButton = new JButton(DRAW_TEXT);
        this.drawButton.setPreferredSize(BUTTON_SIZE);
        this.drawButton.setForeground(TEXT_COLOR);
        this.drawButton.setFont(SUBTITLE_FONT);
        this.drawButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (showFrame.isVisible() == false) {
                    // make show frame visible if it has not show up yet
                    showFrame.setVisible(true);
                }
                // show card
                if (showFrame.getDataCount() == TITLES.length) {
                    // We have not called updateBar before, so the data count in bar is original one.
                    showFrame.showCard();
                    return;
                }

                // get data and update bar chart
                HashMap<String, Integer> data = input.getInput();
                if (showFrame.updateBar(data, TITLES[(showFrame.getDataCount() % TITLES.length)]) == true) {
                    input.clear();
                }
                // change the text on draw button
                if (showFrame.getDataCount() == TITLES.length) {
                    // We have called updateBar before, so the data count in bar is updated.
                    drawButton.setText(SHOW_TEXT);
                }
            }
        });
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(this.drawButton, constraints);
        
        // margin
        constraints.gridx = 1;
        panel.add(Box.createHorizontalStrut(70), constraints);
        
        // clear button
        this.clearButton = new JButton(CLEAR_TEXT);
        this.clearButton.setPreferredSize(BUTTON_SIZE);
        this.clearButton.setForeground(TEXT_COLOR);
        this.clearButton.setFont(SUBTITLE_FONT);
        this.clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (showFrame.isVisible() == false) {
                    // make show frame visible if it has not show up yet
                    showFrame.setVisible(true);
                }
                showFrame.reset();
                input.clear();
                drawButton.setText(DRAW_TEXT);
            }
        });
        constraints.gridx = 2;
        panel.add(this.clearButton, constraints);

        return panel;
    }
}
