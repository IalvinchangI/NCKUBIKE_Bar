package indi.IalvinchangI.nckubikebar;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JPanel;

import indi.IalvinchangI.nckubikebar.tools.GUIConstant;

public class ShowPanel extends JPanel implements GUIConstant {
    
    private JLabel title = null;
    private BarChart bar = null;

    /**
     * create ShowPanel
     * 
     * @param labels the label for each bar
     */
    public ShowPanel(String[] labels) {
        // setting
        this.setBackground(Color.WHITE);
        this.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        // title label
        this.title = new JLabel(" ");
        this.title.setFont(TITLE_FONT);
        constraints.gridx = 0;
        constraints.gridy = 0;
        this.add(this.title, constraints);

        // bar chart
        this.bar = new BarChart(labels);
        constraints.gridx = 0;
        constraints.gridy = 1;
        this.add(this.bar, constraints);
    }

    /**
     * add new data to bar chart and update it
     * 
     * @param data
     * @param title the name for this updata
     * @return success or not
     */
    public boolean updateBar(HashMap<String, Integer> data, String title) {
        if (this.bar.update(data, title) == true) {
            this.title.setText(title);
            return true;
        }
        return false;
    }

    /**
     * Clear the bar chart and title. Then, update it.
     */
    public void clearBar() {
        this.bar.clear();
        this.title.setText(" ");
    }

    /**
     * get the row count of the data stored in ShowFrame
     * 
     * @return the row count of the data
     */
    public int getDataCount() {
        return this.bar.getDataCount();
    }

    public int[] getArgmax() {
        return this.bar.getArgmax();
    }
}
