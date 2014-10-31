package mBud;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
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
        frame.setSize(frameWidth / 2, frameHeight - 48);
        panel = new JPanel(true);
        addButtons();
        addPanels();

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
        addLBButton();
        addCBButton();
    }

    private static void addLBButton() {
        loadBudget = new JButton("Load Existing Budget");
        loadBudget.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadBudget();
            }
        });
        panel.add(loadBudget);
    }

    private static void addCBButton() {
        createBudget = new JButton("Create Budget");
        createBudget.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createBudget();
            }
        });
        panel.add(createBudget);
    }

    private static void addATButton() {
        addTransaction = new JButton("New Transaction");
        addTransaction.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addTransaction();
            }
        });
        panel.add(addTransaction);
    }

    private static void addTransaction() {
        System.out.println("Adding transaction");
    }

    private static void loadBudget() {
        final JFileChooser fc = new JFileChooser();
        int returnVal = fc.showOpenDialog(frame);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            //This is where a real application would open the file.
            System.out.println("Opening: " + file.getName() + ".");
            ingestBudget(file);
        } else {
            System.out.println("Open command cancelled by user.");
        }
    }

    private static void createBudget() {
        System.out.println("create budget");
        editBudgetView("*");
    }

    private static void editBudgetView(String budgetFileName) {
        frame.setVisible(false);
        frame.remove(panel);
        panel = new JPanel();
        frame.add(panel);
        addATButton();
        frame.setVisible(true);
    }

    private static void ingestBudget(File file) {
     
    }
}
