/*
 * This is my budgeting application. I am starting with the backend (getting
 transactions and repeating tansactions working) and then I will begin
 */
package mBud;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author Michael Watkins
 */
public class Bud {

    static ArrayList<Transaction> repeatingTransactions;
    static ArrayList<Transaction> transRecord;
    static Scheduler calendar;
    static double balance = 1331.91;
    static int saved = 0;

    /**
     * @param args the command line arguments
     */
    Bud() {
        calendarMaker();
     //   bRT();//build repeating transactions      
      //  bOT();//build once transactions
      //  sim("Y|1", 2014, 10, 29);//simulate 1 year of transactions starting on 10/29/2014
      //  sTR();//sort the transaction record by date
       // pTR();//print the report    
    }

    private void calendarMaker() {
        calendar = new Scheduler(2014, 10, 24);
        transRecord = new ArrayList<Transaction>();
    }

    private void bRT() {//build repeating transactions:
        repeatingTransactions = new ArrayList<Transaction>();
    }

    private void sim(String distance, int startYear, int startMonth, int startDay) {
        Scheduler destination = new Scheduler(startYear, startMonth, startDay);
        System.out.println(destination);
        destination.add(distance);
        System.out.println(destination);
        for (int i = 0; i < repeatingTransactions.size(); i++) {
            Transaction current = repeatingTransactions.get(i);
            Transaction next = current.getNextInstance();
            while (destination.isAfter(next)) {
                transRecord.add(next);
                next = next.getNextInstance();
            }

        }
    }

    private void sTR() {//sort Transaction Records
        ArrayList newTransRecord = new ArrayList<Transaction>();

        while (transRecord.size() > 0) {
            int smallest = 0;//tracks the index of the earliest date
            for (int i = 0; i < transRecord.size(); i++) {
                if (transRecord.get(i).isBefore(transRecord.get(smallest))) {
                    smallest = i;
                }
            }
            newTransRecord.add(transRecord.get(smallest));
            transRecord.remove(smallest);
        }

        transRecord = new ArrayList<Transaction>();
        transRecord = newTransRecord;
    }

    public void pTR() {//print transaction records.
        System.out.println("TRANSACTION RECORDS:");
        sTR();
        for (int i = 0; i < transRecord.size(); i++) {
            System.out.println(transRecord.get(i));
        }
    }

    private void bOT() {//build one time transactions

    }

    public double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public void save() {
        String fname = "output.txt";
        try {
            PrintStream out = new PrintStream(new File(fname));
            for (int i = 0; i < transRecord.size(); i++) {
                out.println(transRecord.get(i).toXML());
            }        
            out.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void addTransaction(Transaction toAdd){
        transRecord.add(toAdd);
    }
    public String toString(){
        String toReturn = "";
            System.out.println("TRANSACTION RECORDS:");
        sTR();
        for (int i = 0; i < transRecord.size(); i++) {
            toReturn = toReturn + transRecord.get(i)+"\n";
        }
        return toReturn;
    }

}
