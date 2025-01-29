package indi.IalvinchangI.nckubikebar;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import indi.IalvinchangI.nckubikebar.tools.GUIConstant;


/**
 * the control window for the bar chart 
 * 
 * @author IalvinchangI
 */
public class ControlFrame extends JFrame implements GUIConstant {

    public static final String[] LABELS = {
        "A", "B", "C", "D", "E", "F", 
    };
    public static final String[] TITLES = {
        "Mon", "Tue", "Wed", "Thu", "Fri", "Sat", 
    };

    private static final String ICON_PATH = "/BarChart.png";
    /** the size of drawButton and clearButton */
    private static final Dimension BUTTON_SIZE = new Dimension(150, 30);

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
        InputStream inputImage = null;
        Image icon = null;
        try {
            inputImage = ControlFrame.class.getResourceAsStream(ICON_PATH);
            icon = ImageIO.read(inputImage);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (inputImage != null) {
                    inputImage.close();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

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
        this.showFrame = new ShowFrame(LABELS);
        
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
        this.drawButton = new JButton("繪製");
        this.drawButton.setPreferredSize(BUTTON_SIZE);
        this.drawButton.setForeground(TEXT_COLOR);
        this.drawButton.setFont(SUBTITLE_FONT);
        this.drawButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (showFrame.isVisible() == false) {
                    // make show frame visible if it has not show up yet
                    showFrame.setVisible(true);
                }
                // get data and update bar chart
                HashMap<String, Integer> data = input.getInput();
                if (showFrame.updateBar(data, TITLES[(showFrame.getDataCount() % TITLES.length)]) == true) {
                    input.clear();
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
        this.clearButton = new JButton("清除");
        this.clearButton.setPreferredSize(BUTTON_SIZE);
        this.clearButton.setForeground(TEXT_COLOR);
        this.clearButton.setFont(SUBTITLE_FONT);
        this.clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (showFrame.isVisible() == false) {
                    // make show frame visible if it has not show up yet
                    showFrame.setVisible(true);
                }
                showFrame.clearBar();
                input.clear();
            }
        });
        constraints.gridx = 2;
        panel.add(this.clearButton, constraints);

        return panel;
    }
}
