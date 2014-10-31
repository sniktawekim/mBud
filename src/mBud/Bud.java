/*
 * This is my budgeting application. I am starting with the backend (getting
 transactions and repeating tansactions working) and then I will begin
 */
package mBud;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
    public static void Bud() {
        calendarMaker();
        bRT();//build repeating transactions      
        bOT();//build once transactions
        sim("Y|1", 2014, 10, 29);//simulate 1 year of transactions starting on 10/29/2014
        sTR();//sort the transaction record by date
        pTR();//print the report
    }

    private static void calendarMaker() {
        calendar = new Scheduler(2014, 10, 24);
        transRecord = new ArrayList<Transaction>();
    }

    private static void bRT() {//build repeating transactions:
        repeatingTransactions = new ArrayList<Transaction>();
        Transaction payday = new Transaction("Paycheck", 1149.83, "W|2", 2014, 11, 7);
        Transaction other = new Transaction("otha", -100.00, "W|1", 2014, 11, 3);
        Transaction rent = new Transaction("Rent", -362.50, "M|1", 2014, 11, 1);
        Transaction electric = new Transaction("Electric", -40, "M|1", 2014, 11, 23);
        Transaction car = new Transaction("Car", -188.82, "M|1", 2014, 11, 14);
        Transaction paypal = new Transaction("Paypal Debt", -270.90, "M|1", 2014, 11, 1);
        Transaction carInsurance = new Transaction("Car Insurance", -149.63, "M|1", 2014, 11, 19);
        Transaction internet = new Transaction("Internet", -22.00, "M|1", 2014, 11, 04);
        Transaction gas = new Transaction("Gas", -30, "W|1", 2014, 10, 30);
        Transaction savings = new Transaction("Savings", -100, "W|2", 2014, 11, 7);
        Transaction phone = new Transaction("Phone", -40, "M|1", 2014, 10, 30);

        repeatingTransactions.add(gas);
        transRecord.add(gas);
        repeatingTransactions.add(payday);
        transRecord.add(payday);
        repeatingTransactions.add(savings);
        transRecord.add(savings);
        repeatingTransactions.add(other);
        transRecord.add(other);
        repeatingTransactions.add(rent);
        transRecord.add(rent);
        repeatingTransactions.add(electric);
        transRecord.add(electric);
        repeatingTransactions.add(car);
        transRecord.add(car);
        repeatingTransactions.add(paypal);
        transRecord.add(paypal);
        repeatingTransactions.add(carInsurance);
        transRecord.add(carInsurance);
        repeatingTransactions.add(internet);
        transRecord.add(internet);
        repeatingTransactions.add(phone);
        transRecord.add(phone);
    }

    private static void sim(String distance, int startYear, int startMonth, int startDay) {
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

    private static void sTR() {//sort Transaction Records
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

    private static void pTR() {//print transaction records.
        System.out.println("TRANSACTION RECORDS:");
        for (int i = 0; i < transRecord.size(); i++) {
            balance += transRecord.get(i).getAmount();
            balance = round(balance, 2);
            if (balance < 0) {
                balance = balance - 40;
                System.out.println("OVERDRAFT FEE: -$40: " + balance);
            } else if (balance < 500) {
                System.out.println("**********-----UNDER 500----**********");

            }
            System.out.print(transRecord.get(i) + "\tBL:\t" + balance);
            if (transRecord.get(i).getTitle().compareToIgnoreCase("Savings") == 0) {
                saved++;
                System.out.println("\tSavings: " + (100 * saved));
            } else {
                System.out.println("");
            }
        }
    }

    private static void bOT() {
        Transaction Moe = new Transaction("Moe", -180, "0", 2014, 11, 8);
        transRecord.add(Moe);
        Transaction Moe2 = new Transaction("Moe2", -228, "0", 2014, 11, 22);
        transRecord.add(Moe2);
        Transaction hayward = new Transaction("Hayward", -300, "0", 2014, 11, 22);
        transRecord.add(hayward);
    }

    public static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static void save() throws IOException {
        File file = new File("output.txt");
        BufferedWriter out = new BufferedWriter(new FileWriter(file));
        //
       // out.write(text);
        for(int i=0;i<transRecord.size();i++){
            out.write(transRecord.get(i).toXML());
        }
        //
        out.close();
    }

}
