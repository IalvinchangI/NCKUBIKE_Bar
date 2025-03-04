package indi.IalvinchangI.nckubikebar;

import java.util.ArrayList;
import java.util.HashMap;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import indi.IalvinchangI.nckubikebar.tools.Animator;
import indi.IalvinchangI.nckubikebar.tools.GUIConstant;
import indi.IalvinchangI.nckubikebar.tools.PaintTools;
import indi.IalvinchangI.nckubikebar.utils.DataFrame;
import indi.IalvinchangI.nckubikebar.utils.MathTools;
import indi.IalvinchangI.nckubikebar.utils.Series;


/**
 * plot Bar Chart
 * <p>
 * When adding a new data, it will plot the new bar at the top of the original one.
 * 
 * @author IalvinchangI
 */
public class BarChart extends JPanel implements GUIConstant {

    private DataFrame data = null;
    private ArrayList<String> rowName = null;
    private String[] labels = null;
    
    /**
     * create a bar chart
     * 
     * @param labels the x-axis label
     */
    public BarChart(String[] labels) {
        // setting
        this.setBackground(Color.WHITE);
        this.setPreferredSize(new Dimension(800, 600));

        this.animator = new Animator(ANIMATION_DELAY);

        // init container
        this.labels = labels;
        this.data = new DataFrame(labels);
        this.rowName = new ArrayList<>();
        this.frameData = getPlotData();
        this.frameValue = new int[labels.length];
    }

    /**
     * clear the bar chart and update it
     */
    public void clear() {
        this.rowName.clear();
        this.data.clearData();
        // update graph
        this.frameData = getPlotData();
        this.frameValue = new int[labels.length];
        repaint();
    }

    /**
     * add new data to the bar chart and plot the new bar at the top of the original one
     * 
     * @param newData new data
     * @param title the title for new data
     * @return success or not
     */
    public boolean update(HashMap<String, Integer> newData, String title) {
        if (this.animator.isRuning() == true) {
            return false;
        }
        this.rowName.add(title);
        this.data.putRow(newData);
        // update graph
        if (this.data.isRowZero(this.getDataCount() - 1) == false) {
            this.animation();
        }
        return true;
    }

    /**
     * get the row count of the data stored in BarChart
     * 
     * @return the row count of the data
     */
    public int getDataCount() {
        return this.rowName.size();
    }

    /**
     * get the index of the series which has the max sum
     * <p>
     * if the sums of any series are equal, the function will list all of them and output the results
     * 
     * @return a list of index
     */
    public int[] getArgmax() {
        ArrayList<String> columnNames = this.data.getArgmax();
        int[] out = new int[columnNames.size()];

        for (int i = 0; i < out.length; i++) {
            String name = columnNames.get(i);
            for (int labelIndex = 0; labelIndex < this.labels.length; labelIndex++) {
                if (this.labels[labelIndex].equals(name)) {
                    out[i] = labelIndex;
                    break;
                }
            }
        }

        return out;
    }
    
    /**
     * transfer the data from raw one to plot one
     * 
     * @return final plot data
     */
    private DataFrame getPlotData() {
        DataFrame out = new DataFrame();

        int length = this.rowName.size();
        for (String label : this.data.keySet()) {
            Series series = Series.zeros(length);
            int temp = 0;
            for (int i = length - 1; i >= 0; i--) {
                int value = this.data.get(label).get(i);
                temp += value;
                if (value < 0 || temp < 0) {
                    series.set(i, 0);
                }
                else {
                    series.set(i, temp);
                    temp = 0;  // reset temp
                }
            }
            if (temp < 0) {  // cut down the upper part
                for (int i = 1; i < length; i++) {
                    temp += series.get(i);
                    if (temp < 0) {
                        series.set(i, 0);
                    }
                    else {
                        series.set(i, temp);
                        break;  // end
                    }
                }
            }
            out.put(label, series);
        }
        
        return out;
    }

    private static final int HALF_BORDER_WIDTH = 2;
    private static final int BAR_ARC_DIAMETER = 10;

    /** the ratio of the bar width to the margin width */
    private static final float BAR_MARGIN_RATIO = 0.3f;
    /** the margin of frame {up, left, down, right} */
    private static final int[] MARGIN = {10, 40, 40, 40};
    /** the margin between the frame and the top of the bar */
    private static final int BAR_UP_MARGIN = 24;

    /** the maximum number of labels on the y-axis */
    private static final int Y_LABEL_COUNT_LIMIT = 5;
    /** the basic value interval between the labels on the y-axis */
    private static final int Y_LABEL_BASIC_INTERVAL = 5;

    private static final Color[] BAR_COLOR = {
        new Color(255, 207, 210), new Color(241, 192, 232), 
        new Color(207, 186, 240), new Color(141, 219, 244), 
        new Color(142, 236, 245), new Color(152, 245, 225)
    };
    
    public static final int ANIMATION_DELAY = 40;
    public static final int ANIMATION_DURATION = 1000;

    private Animator animator = null;
    private DataFrame frameData = null;
    private int[] frameValue = null;

    @Override
    protected void paintComponent(Graphics g) {  // graph bars
        // setting
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setStroke(new BasicStroke(HALF_BORDER_WIDTH * 2));
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // constant
        Dimension chartSize = this.getSize();
        float bar_margin_ratio = BAR_MARGIN_RATIO + chartSize.width / chartSize.height;
        // x
        int plotWidth = chartSize.width - MARGIN[3] - MARGIN[1];
        int barCount = this.data.size();
        float barWidth = (plotWidth- (HALF_BORDER_WIDTH << 1)) / ((barCount + 1) * bar_margin_ratio + barCount);
        // y
        int plotHeight = chartSize.height - MARGIN[2] - MARGIN[0];
        int baseline = chartSize.height - MARGIN[2] - HALF_BORDER_WIDTH;
        int upperLimit = MathTools.ceil(this.frameData.getMaxSum());
        upperLimit = (upperLimit > 20) ? upperLimit : 20;
        int barHeightRatio = (plotHeight - (HALF_BORDER_WIDTH << 1) - BAR_UP_MARGIN) / upperLimit;

        // plot bar
        int labelIndex = 0;
        for (
            float x = barWidth * bar_margin_ratio + MARGIN[3] + HALF_BORDER_WIDTH; 
            (int)(x + 0.1) < chartSize.width - MARGIN[1] - HALF_BORDER_WIDTH; 
            x += barWidth * (1 + bar_margin_ratio), labelIndex++
        ) {
            int y = baseline;
            String label = this.labels[labelIndex];
            // bar
            int colorIndex = 0;
            for (int value : this.frameData.get(label)) {
                if (value != 0) {
                    int barHeight = value * barHeightRatio;
                    y -= barHeight;
                    g2d.setColor(BAR_COLOR[(colorIndex % BAR_COLOR.length)]);
                    g2d.fillRoundRect(
                        (int)x, y, 
                        (int)barWidth, barHeight, 
                        BAR_ARC_DIAMETER, BAR_ARC_DIAMETER
                    );
                }
                colorIndex++;
            }
            // value
            g2d.setFont(CONTENT_FONT);
            g2d.setColor(Color.BLACK);
            PaintTools.drawStringAtCenter(
                g2d, Integer.toString(this.frameValue[labelIndex]), 
                (int)x + (int)barWidth / 2, y - BAR_UP_MARGIN / 2
            );

            // x-axis
            g2d.setFont(SUBTITLE_FONT);
            PaintTools.drawStringAtCenter(
                g2d, label, 
                (int)x + (int)barWidth / 2, baseline + MARGIN[2] / 2
            );
        }

        // y-axis
        g2d.setFont(CONTENT_FONT);
        g2d.setColor(Color.BLACK);
        int valueInterval = Y_LABEL_BASIC_INTERVAL;
        while (upperLimit / valueInterval > (Y_LABEL_COUNT_LIMIT - 1)) {  // find best interval
            valueInterval += Y_LABEL_BASIC_INTERVAL;
        }
        for (int yValue = 0; yValue <= upperLimit; yValue += valueInterval) {
            PaintTools.drawStringAtCenter(
                g2d, Integer.toString(yValue), 
                MARGIN[1] / 2, baseline - yValue * barHeightRatio
            );
        }

        // draw chart frame
        g2d.setColor(Color.BLACK);
        g2d.drawRoundRect(
            MARGIN[3], MARGIN[0], 
            plotWidth, plotHeight, 
            20, 20
        );

        // delete g2d
        g2d.dispose();
    }

    /**
     * initialize the animation and start it
     */
    private void animation() {
        // init data
        DataFrame plotData = getPlotData();  // final state of bar
        int[] finalValue = new int[this.data.size()];  // final state of value
        for (int i = 0; i < this.data.size(); i++) {
            finalValue[i] = this.data.get(this.labels[i]).sum();
        }
        HashMap<String, Integer> valueDelta = this.data.getRow(this.getDataCount() - 1);
        
        // init container
        for (int barIndex = 0; barIndex < valueDelta.size(); barIndex++) {
            // init data
            String label = labels[barIndex];
            Series frameSeries = frameData.get(label);
            Series plotSeries = plotData.get(label);
            if (frameSeries.size() != plotSeries.size()) {
                Series temp = Series.zeros(plotSeries.size());
                for (int i = 0; i < frameSeries.size(); i++) {
                    temp.set(i, frameSeries.get(i));
                }
                frameSeries = temp;
                frameData.put(label, temp);
            }
        }

        // displacement per frame
        int totalFrame = ANIMATION_DURATION / ANIMATION_DELAY;
        int[] unitDisplacement = new int[valueDelta.size()];
        for (int i = 0; i < valueDelta.size(); i++) {
            unitDisplacement[i] = (InputPanel.INPUT_LIMIT / totalFrame + 1) * MathTools.getSign(valueDelta.get(this.labels[i]));
        }

        // animate the bar
        this.animator.addAnimation(new ActionListener() {
            int frame = 0;
            @Override
            public void actionPerformed(ActionEvent e) {
                frame++;
                if (frame == totalFrame) {
                    animator.stop(this);
                    return;
                }
                for (int barIndex = 0; barIndex < valueDelta.size(); barIndex++) {
                    if (unitDisplacement[barIndex] == 0) {  // no change
                        continue;
                    }
                    // init data
                    String label = labels[barIndex];
                    Series frameSeries = frameData.get(label);
                    Series plotSeries = plotData.get(label);
                    
                    // find index
                    int diffIndex = 0;
                    if (unitDisplacement[barIndex] > 0) {
                        for (; diffIndex < frameSeries.size(); diffIndex++) {
                            if (plotSeries.get(diffIndex) != frameSeries.get(diffIndex)) {
                                break;
                            }
                        }
                    }
                    else {
                        diffIndex = frameSeries.size() - 1;
                        for (; diffIndex >= 0; diffIndex--) {
                            if (plotSeries.get(diffIndex) != frameSeries.get(diffIndex)) {
                                break;
                            }
                        }
                    }
                    // animate value
                    if (frameValue[barIndex] != finalValue[barIndex]) {
                        frameValue[barIndex] += unitDisplacement[barIndex];
                        if (
                            (frameValue[barIndex] < finalValue[barIndex] && unitDisplacement[barIndex] < 0) || 
                            (frameValue[barIndex] > finalValue[barIndex] && unitDisplacement[barIndex] > 0)
                        ) {
                            frameValue[barIndex] = finalValue[barIndex];
                        }
                    }
                    // animate bar
                    if (diffIndex == frameSeries.size() || diffIndex == -1) {  // no different
                        continue;
                    }
                    if (frameValue[barIndex] < 0) {  // the bar can't grow when the value is less than 0
                        continue;
                    }
                    adjustFrameData(frameSeries, plotSeries, diffIndex, unitDisplacement[barIndex]);
                }
                repaint();
            }
            
        });
    }

    /**
     * calculate the height of each bar in a frame
     * 
     * @param frameSeries the series in frame data
     * @param plotSeries the series in plot data
     * @param index the row index of bar
     * @param displacement the distance of increase or decrease
     */
    private void adjustFrameData(Series frameSeries, Series plotSeries, int index, int displacement) {
        if (index < 0 || index >= frameSeries.size()) {
            return;
        }
        int temp = frameSeries.get(index) + displacement;
        if (temp < 0) {  // go back to lower bar (displacement < 0)
            frameSeries.set(index, 0);
            adjustFrameData(frameSeries, plotSeries, index - 1, temp);
        }
        else if (temp > plotSeries.get(index) && displacement > 0) {  // go up to upper bar
            frameSeries.set(index, plotSeries.get(index));
            adjustFrameData(frameSeries, plotSeries, index + 1, temp - plotSeries.get(index));
        }
        else {  // stop
            frameSeries.set(index, temp);
        }
    }
}
