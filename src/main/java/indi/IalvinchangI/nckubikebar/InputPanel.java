package indi.IalvinchangI.nckubikebar;

import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import indi.IalvinchangI.nckubikebar.tools.GUIConstant;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;


/**
 * get the input from user
 * 
 * @author IalvinchangI
 */
public class InputPanel extends JPanel implements GUIConstant {

    public static final int INPUT_LIMIT = 20;

    private static final Dimension FIELD_SIZE = new Dimension(60, 25);

    private JLabel[] labels = null;
    private JSpinner[] inputs = null;

    /**
     * create InputPanel
     * 
     * @param labels the label for each input field
     */
    public InputPanel(String[] labels) {
        this.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.CENTER;
        constraints.insets = new Insets(0, 5, 5, 0);

        // labels and inputs
        this.labels = new JLabel[labels.length];
        this.inputs = new JSpinner[labels.length];
        int i = 0;
        for (; i < labels.length; i++) {  // init label and input field
            this.labels[i] = new JLabel(labels[i]);
            this.inputs[i] = new JSpinner(new SpinnerNumberModel(0, -INPUT_LIMIT, INPUT_LIMIT, 1));
            
            this.inputs[i].setPreferredSize(FIELD_SIZE);

            this.labels[i].setHorizontalAlignment(SwingConstants.CENTER);
            this.labels[i].setForeground(TEXT_COLOR);
            this.labels[i].setFont(CONTENT_FONT);
            this.inputs[i].setForeground(TEXT_COLOR);
            this.inputs[i].setFont(CONTENT_FONT);
            
            constraints.gridx = i;
            constraints.gridy = 0;
            this.add(this.labels[i], constraints);
            constraints.gridy = 1;
            this.add(this.inputs[i], constraints);
        }
        
        // label
        JLabel label = new JLabel("類別");
        label.setForeground(TEXT_COLOR);
        label.setFont(CONTENT_FONT);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        constraints.gridx = i;
        constraints.gridy = 0;
        this.add(label, constraints);
        
        label = new JLabel("數字");
        label.setForeground(TEXT_COLOR);
        label.setFont(CONTENT_FONT);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        constraints.gridy = 1;
        constraints.insets.bottom += 2;
        this.add(label, constraints);
    }

    /**
     * get the all the values that the user inputs
     * 
     * @return {"label" : value}
     */
    public HashMap<String, Integer> getInput() {
        HashMap<String, Integer> out = new HashMap<>();
        for (int i = 0; i < labels.length; i++) {
            out.put(
                this.labels[i].getText(), 
                (Integer)this.inputs[i].getValue()
            );
        }
        return out;
    }

    /**
     * set all the input fields to 0
     */
    public void clear() {
        for (int i = 0; i < labels.length; i++) {
            this.inputs[i].setValue(0);
        }
    }
}
