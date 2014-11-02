/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mBud;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author Michael Watkins
 */
public class Scheduler extends GregorianCalendar {

    Scheduler(int year, int month, int day) {
        super(year, month-1, day);
        

    }

    public String add(String code) {
        String period;
        period = code.split("|")[0];
        String numbInc = "";
        for (int i = 2; i < code.split("|").length; i++) {
            numbInc = numbInc + code.split("|")[i];
        }
        int magnitude = Integer.parseInt(numbInc);

        if (period.compareToIgnoreCase("M") == 0) {
            return addMonth(magnitude);
        } else if (period.compareToIgnoreCase("W") == 0) {
            return addDays(magnitude * 7);
        } else if (period.compareToIgnoreCase("D") == 0) {
            return addDays(magnitude);
        } else if (period.compareToIgnoreCase("Y") == 0) {
            return addMonth(magnitude * 12);
        }

        //calendar.add(Calendar.DAY_OF_MONTH, 14);
        //System.out.println("Date : " + sdf.format(calendar.getTime()));
        return "";
    }

    private String addMonth(int magnitude) {
        add(Calendar.MONTH, magnitude);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy MM dd");
        return sdf.format(getTime());
    }

    private String addDays(int magnitude) {
        add(Calendar.DAY_OF_MONTH, magnitude);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy MM dd");
        return sdf.format(getTime());
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy MM dd");
        String print = sdf.format(getTime());
        String sYear = print.substring(0, 5);
        String sMonth = print.substring(5, 7);
        String sDay = print.substring(7);
        return sYear+sMonth+sDay;
        
    }

    public boolean isAfter(Transaction toCompare) {
        String transDate = toCompare.getDate();
        String sYear = transDate.split(" ")[0];
        String sMonth = transDate.split(" ")[1];
        String sDay = transDate.split(" ")[2];
        
        
        Scheduler transSched = new Scheduler(Integer.parseInt(sYear),Integer.parseInt(sMonth),Integer.parseInt(sDay));
        if(getTimeInMillis()<transSched.getTimeInMillis()){
            return false;
        }
        return true;
    }

}
