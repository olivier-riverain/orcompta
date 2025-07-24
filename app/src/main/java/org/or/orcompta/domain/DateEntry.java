package org.or.orcompta.domain;

public class DateEntry {
    private final String day;
    private final String month;
    private final String year;

    public DateEntry(String day, String month, String year) {       
        this.day = day;
        this.month = month;
        this.year = year;        
    }

    public String getDay() {
        return this.day;
    }

    public String getMonth() {
        return this.month;
    }

    public String getYear() {
        return this.year;
    }

    public String getDate() {
        return this.day + "/" + this.month + "/" + this.year;
    }

    private boolean checkDate() {
        // verifier si la date existe

        return true;

    }

    public String toString() {
         return this.day + "/" + this.month + "/" + this.year;
    }
}
