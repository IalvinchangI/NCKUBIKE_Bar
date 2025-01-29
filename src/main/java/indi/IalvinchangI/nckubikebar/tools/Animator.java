package indi.IalvinchangI.nckubikebar.tools;

import java.awt.event.ActionListener;

import javax.swing.Timer;


/**
 * a timer to invoke update function with the same delay
 * 
 * @author IalvinchangI
 */
public class Animator {

    private Timer animationTimer = null;

    /**
     * create Animator
     * 
     * @param delay the delay milliseconds for each frame
     */
    public Animator(int delay) {
        this.animationTimer = new Timer(delay, null);
    }

    /**
     * add update function to Animator
     * 
     * @param animation update function
     */
    public void addAnimation(ActionListener animation) {
        this.animationTimer.addActionListener(animation);
        if (this.isRuning() == false) {
            this.animationTimer.start();
        }
    }

    /**
     * stop specific update function in Animator
     * 
     * @param animation update function
     */
    public void stop(ActionListener animation) {
        this.animationTimer.removeActionListener(animation);  // stop animation
        if (this.animationTimer.getActionListeners().length == 0) {  // stop timer
            this.animationTimer.stop();
        }
    }

    /**
     * If all the update function in Animator are stopped, this function will return false. Otherwise, it will return true.
     * 
     * @return whether the Animator is runnung
     */
    public boolean isRuning() {
        return this.animationTimer.isRunning();
    }
}
