package mBud;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

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
    static Bud budget;

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
        frame = new JFrame("Mike's Budgetter.");
        budget = new Bud();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame.setSize(frameWidth / 2, frameHeight - 48);
        frame.setSize(300, 80);
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
                try {
                    loadBudget();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(MBud.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panel.add(loadBudget);
    }

    private static void addCBButton() {
        createBudget = new JButton("New Budget");
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
        Transaction toAdd = addTransactionForm();
        if (toAdd != null) {
            budget.addTransaction(toAdd);
        } else {
            System.out.println("Failed to add transaction");
        }
    }

    private static void loadBudget() throws FileNotFoundException {
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
        editBudgetView();
    }

    private static void editBudgetView() {
        frame.setVisible(false);
        frame.remove(panel);
        panel = new JPanel();
        frame.add(panel);
        addATButton();
        frame.setVisible(true);

    }

    private static void ingestBudget(File file) throws FileNotFoundException {
        Scanner in = new Scanner(file);
        while (in.hasNext()) {
            Transaction toAdd = new Transaction(in.nextLine());
            budget.addTransaction(toAdd);
        }
        System.out.println(budget);

    }

    private static Transaction addTransactionForm() {
        Transaction toReturn = null;

        frame.setVisible(false);
        frame.remove(panel);
        JPanel textBoxPanel = new JPanel(new GridLayout(0,1));
        
        JTextField title = new JTextField(16);
        title.setText("Transaction Name");
        
        JTextField amount = new JTextField(16);
        amount.setText("Dollar Amount");
        
        JTextField day = new JTextField(16);
        day.setText("day number");
        
        JTextField month = new JTextField(16);
        month.setText("day number");
        
        JTextField year = new JTextField(16);
        year.setText("year");
        
        textBoxPanel.add(title);
        textBoxPanel.add(month);
        textBoxPanel.add(day);
        textBoxPanel.add(year);
        
        frame.add(textBoxPanel, BorderLayout.WEST);
        frame.setVisible(true);

        return toReturn;
    }
}
