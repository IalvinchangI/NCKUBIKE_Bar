package indi.IalvinchangI.nckubikebar;

import java.util.HashMap;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;


/**
 * the window to show Bar Chart and Cards
 * <p>
 * this window allows user to switch between full screen and normal size
 * 
 * @author IalvinchangI
 */
public class ShowFrame extends JFrame {

    private ShowPanel showPanel = null;
    private CardPanel cardPanel = null;
    private GraphicsDevice device = null;

    public static final String SHOW_PAGE_NAME = "show";
    public static final String CARD_PAGE_NAME = "card";

    final Dimension SMALL_SIZE = new Dimension(900, 750);

    private boolean fullscreen_TF = false;
    
    /**
     * create ShowFrame
     * 
     * @param labels the label for each bar
     */
    public ShowFrame(String[] labels, String[] cardPathTabel) {
        ShowFrame window = this;

        // setting
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(this.SMALL_SIZE);
        this.getContentPane().setLayout(new CardLayout());
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
            this.fullscreen_TF = true;
        }

        // panel setting
        this.showPanel = new ShowPanel(labels);
        this.getContentPane().add(this.showPanel, SHOW_PAGE_NAME);
        this.cardPanel = new CardPanel(cardPathTabel);
        this.getContentPane().add(this.cardPanel, CARD_PAGE_NAME);

        // event
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                int keyCode = e.getKeyChar();
                if (keyCode == KeyEvent.VK_ESCAPE && fullscreen_TF == true) {  // exit full screen
                    if (device != null && device.getFullScreenWindow() == window) {
                        // Initially, window is fullscreen, undecorated, and on top of other windows.
                        window.dispose();
                        window.setUndecorated(false);
                        window.setExtendedState(JFrame.NORMAL);
                        device.setFullScreenWindow(null);
                        window.setVisible(true);
                    }
                    else if (window.isUndecorated() == true) {
                        // Initially, window is fullscreen and undecorated.
                        window.dispose();
                        window.setUndecorated(false);
                        window.setSize(window.SMALL_SIZE);
                        window.setExtendedState(JFrame.NORMAL);
                        window.setVisible(true);
                    }
                    else {
                        // Initially, window is fullscreen and decorated.
                        // Normally, it is impossible to go to here.
                        window.setSize(window.SMALL_SIZE);
                        window.setExtendedState(JFrame.NORMAL);
                    }
                    fullscreen_TF = false;
                }
                else if (keyCode == 'f' && fullscreen_TF == false) {
                    if (device != null && device.getFullScreenWindow() != window) {
                        // window becomes fullscreen, undecorated, and stays on top of other windows.
                        window.dispose();
                        window.setUndecorated(true);
                        device.setFullScreenWindow(window);
                    }
                    else {
                        // window becomes fullscreen and undecorated.
                        window.setExtendedState(JFrame.MAXIMIZED_BOTH);
                        if (window.isUndecorated() == false) {
                            window.dispose();
                            window.setUndecorated(true);
                            window.setVisible(true);
                        }
                    }
                    fullscreen_TF = true;
                }
                else if (keyCode == KeyEvent.VK_F && fullscreen_TF == false) {
                    window.setExtendedState(JFrame.MAXIMIZED_BOTH);
                    if (window.isUndecorated() == false) {
                        // window becomes fullscreen and undecorated.
                        window.dispose();
                        window.setUndecorated(true);
                        window.setVisible(true);
                    }
                    fullscreen_TF = true;
                }
            }
        });
    }

    /**
     * make show frame visible
     */
    public void visible() {
        this.setSize(this.SMALL_SIZE);  // ensure the size is correct
        this.setVisible(true);
    }

    /**
     * show the page by its name
     * 
     * @param pageName the name of page
     */
    public void showPage(String pageName) {
        ((CardLayout) this.getContentPane().getLayout()).show(this.getContentPane(), pageName);
    }
    
    /**
     * add new data to bar chart and update it
     * 
     * @param data
     * @param title the name for this updata
     * @return success or not
     */
    public boolean updateBar(HashMap<String, Integer> data, String title) {
        return this.showPanel.updateBar(data, title);
    }

    /**
     * show the card according to the total sum of each bar
     */
    public void showCard() {
        int[] indices = this.showPanel.getArgmax();
        this.cardPanel.showCard(indices);
        this.showPage(CARD_PAGE_NAME);
    }

    /**
     * Clear the bar chart and title. Then, update it.
     */
    public void reset() {
        this.showPanel.clearBar();
        this.showPage(SHOW_PAGE_NAME);
    }

    /**
     * get the row count of the data stored in ShowFrame
     * 
     * @return the row count of the data
     */
    public int getDataCount() {
        return this.showPanel.getDataCount();
    }
}
