package mBud;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author mwatkins
 */
public class MBud {

    static JFrame frame;
    static JPanel panel;
    static int frameWidth;
    static int frameHeight;
    static JButton addTransaction;
    static JButton loadBudget;
    static JButton createBudget;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        init();
        addPanels();
        // frame.pack();
        frame.setVisible(true);
    }

    private static void init() {
        getResolution();
        frame = new JFrame("FrameDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(frameWidth/2, frameHeight-48);
        panel = new JPanel(true);

        addButtons();

    }

    private static void addPanels() {
        frame.getContentPane().add(panel, BorderLayout.CENTER);
    }

    private static void getResolution() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();
        setResolution(width, height);
        System.out.println("width: " + frameWidth + ", height: " + frameHeight);
    }

    private static void setResolution(int width, int height) {
        frameWidth = width;
        frameHeight = height;
    }

    private static void addButtons() {
        loadBudget = new JButton("Load Existing Budget");
        //Add action listener to button
        loadBudget.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                //Execute when button is pressed
                loadBudget();
            }

        });

        panel.add(loadBudget);

    }

    private static void addTransaction() {
        System.out.println("Adding transaction");
    }

    private static void loadBudget() {
        System.out.println("select budget file");
    }
    
    private static void createBudget(){
        System.out.println("create budget");
    }
}
