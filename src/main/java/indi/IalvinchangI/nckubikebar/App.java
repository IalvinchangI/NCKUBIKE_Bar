package indi.IalvinchangI.nckubikebar;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.synth.SynthLookAndFeel;


/**
 * NCKU BIKE Bar Chart Project
 * <p>
 * 2025/1/17
 * <p>
 * Main Class
 * 
 * @author IalvinchangI
 */
public class App {
    public static void main(String[] args) {
        // create frame
        ControlFrame controlFrame = new ControlFrame();

        // style
        InputStream styleFile = null;
        try {
            SynthLookAndFeel laf = new SynthLookAndFeel();
            styleFile = App.class.getResourceAsStream("/style.xml");
            laf.load(styleFile, App.class);
            UIManager.setLookAndFeel(laf);
            SwingUtilities.updateComponentTreeUI(controlFrame);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (styleFile != null) {
                    styleFile.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        controlFrame.setVisible(true);
    }
}
