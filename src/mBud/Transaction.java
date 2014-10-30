package mBud;

import java.util.Date;

/**
 *
 * @author Michael Watkins
 */
public class Transaction {

    double amount;
    String repeatCode;
    String date;
    String title;
    int year;
    int month;
    int day;

    Transaction(String title, double amount, String repeatCode, int year, int month, int day) {
        month = month;
        this.amount = amount;
        this.repeatCode = "" + repeatCode;
        this.year = year;
        this.month = month;
        this.day = day;
        this.title = "" + title;
        getDate();
    }

    public String toString() {
        return "" + month + "/" + day + "/" + year + "\t" + title + "\t" + amount + "\t {" + repeatCode + "}";
    }

    public String getDate() {
        String sYear = "" + year;
        String sMonth = "" + month;
        if (sMonth.length() < 2) {
            sMonth = "0" + sMonth;
        }
        String sDay = "" + day;
        if (sDay.length() < 2) {
            sDay = "0" + sDay;
        }
        this.date = sYear + " " + sMonth + " " + sDay;
        return date;
    }

    public String getNextOccurance() {
        Scheduler date = new Scheduler(year, month, day);
        return date.add(repeatCode);
    }

    public String getTitle() {
        return title;
    }

    public Transaction getNextInstance() {
        Scheduler date = new Scheduler(year, month, day);
        String newDate = date.add(repeatCode);

        String sYear = newDate.split(" ")[0];
        String sMonth = newDate.split(" ")[1];
        String sDay = newDate.split(" ")[2];

        Transaction next = new Transaction(title, amount, repeatCode, Integer.parseInt(sYear), Integer.parseInt(sMonth), Integer.parseInt(sDay));
        return next;
    }

    public boolean isBefore(Transaction toCheck) {
        String sYear = getDate().split(" ")[0];
        String sMonth = getDate().split(" ")[1];
        String sDay = getDate().split(" ")[2];

        String s2Year = toCheck.getDate().split(" ")[0];
        String s2Month = toCheck.getDate().split(" ")[1];
        String s2Day = toCheck.getDate().split(" ")[2];

        int comp1 = Integer.parseInt(sYear);
        int comp2 = Integer.parseInt(s2Year);

        if (comp1 - comp2 < 0) {
            return true;
        } else if (comp1 - comp2 > 0) {
            return false;
        }
        comp1 = Integer.parseInt(sMonth);
        comp2 = Integer.parseInt(s2Month);
        if (comp1 - comp2 < 0) {
            return true;
        } else if (comp1 - comp2 > 0) {
            return false;
        }

        comp1 = Integer.parseInt(sDay);
        comp2 = Integer.parseInt(s2Day);
        if (comp1 - comp2 < 0) {
            return true;
        } else if (comp1 - comp2 > 0) {
            return false;
        }
        return false;

    }
    public double getAmount(){
        return amount;
    }
}
