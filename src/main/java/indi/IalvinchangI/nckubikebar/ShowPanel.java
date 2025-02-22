package indi.IalvinchangI.nckubikebar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import indi.IalvinchangI.nckubikebar.tools.GUIConstant;


/**
 * the panel to display bar chart and its title
 * 
 * @author IalvinchangI
 */
public class ShowPanel extends JPanel implements GUIConstant {
    
    private JLabel title = null;
    private BarChart bar = null;

    /** the margin of frame {up, left, down, right} */
    private static final int[] MARGIN = new int[] {30, 30, 30, 30};

    /**
     * create ShowPanel
     * 
     * @param labels the label for each bar
     */
    public ShowPanel(String[] labels) {
        // setting
        this.setBackground(Color.WHITE);
        this.setLayout(new BorderLayout());

        // title label
        this.title = new JLabel(" ");
        this.title.setFont(TITLE_FONT);
        this.title.setHorizontalAlignment(SwingConstants.CENTER);
        this.title.setBorder(BorderFactory.createEmptyBorder(
            MARGIN[0], MARGIN[1], 0, MARGIN[3]
        ));
        this.add(this.title, BorderLayout.NORTH);

        // bar chart
        this.bar = new BarChart(labels);
        this.add(this.bar, BorderLayout.CENTER);

        // margin
        this.add(Box.createHorizontalStrut(MARGIN[1]), BorderLayout.WEST);
        this.add(Box.createHorizontalStrut(MARGIN[3]), BorderLayout.EAST);
        this.add(Box.createVerticalStrut(MARGIN[2]), BorderLayout.SOUTH);
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

    /**
     * get the index of the series which has the max sum
     * <p>
     * if the sums of any series are equal, the function will list all of them and output the results
     * 
     * @return a list of index
     */
    public int[] getArgmax() {
        return this.bar.getArgmax();
    }
}
