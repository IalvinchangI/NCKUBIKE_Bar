package indi.IalvinchangI.nckubikebar;

import java.util.HashMap;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;

import indi.IalvinchangI.nckubikebar.tools.GUIConstant;


/**
 * the window to show Bar Chart
 * <p>
 * this window allows user to switch between full screen and normal size
 * 
 * @author IalvinchangI
 */
public class ShowFrame extends JFrame implements GUIConstant {

    private JLabel title = null;
    private BarChart bar = null;
    private GraphicsDevice device = null;

    final Dimension SMALL_SIZE = new Dimension(900, 750);
    
    /**
     * create ShowFrame
     * 
     * @param labels the label for each bar
     */
    public ShowFrame(String[] labels) {
        ShowFrame window = this;

        // setting
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.rootPane.setBackground(Color.WHITE);
        this.setSize(this.SMALL_SIZE);
        this.rootPane.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        // full screen setting
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setResizable(false);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] devices = ge.getScreenDevices();
        for (int i = devices.length - 1; i >= 0; i--) {  // choose device
            if (devices[i].isFullScreenSupported()) {
                this.device = devices[i];
                break;
            }
        }
        if (this.device != null) {
            this.setUndecorated(true);
            this.device.setFullScreenWindow(window);
        }

        // title label
        this.title = new JLabel(" ");
        this.title.setFont(TITLE_FONT);
        constraints.gridx = 0;
        constraints.gridy = 0;
        this.rootPane.add(this.title, constraints);

        // bar chart
        this.bar = new BarChart(labels);
        constraints.gridx = 0;
        constraints.gridy = 1;
        this.rootPane.add(this.bar, constraints);

        // event
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                int keyCode = e.getKeyChar();
                if (keyCode == KeyEvent.VK_ESCAPE) {  // exit full screen
                    if (device != null && device.getFullScreenWindow() == window) {
                        window.dispose();
                        window.setUndecorated(false);
                        window.setExtendedState(JFrame.NORMAL);
                        device.setFullScreenWindow(null);
                        window.setVisible(true);
                    }
                    else {
                        window.setSize(window.SMALL_SIZE);
                        window.setExtendedState(JFrame.NORMAL);
                    }
                }
                else if (keyCode == 'f') {
                    if (device != null && device.getFullScreenWindow() != window) {
                        // full screen without decorate
                        window.dispose();
                        window.setUndecorated(true);
                        device.setFullScreenWindow(window);
                    }
                    else {  // full screen with decorate
                        window.setExtendedState(JFrame.MAXIMIZED_BOTH);
                    }
                }
                else if (keyCode == KeyEvent.VK_F) {  // full screen with decorate
                    window.setExtendedState(JFrame.MAXIMIZED_BOTH);
                }
            }
        });
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
}
