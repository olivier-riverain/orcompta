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

    public Boolean between(DateEntry dateBegin, DateEntry dateEnd) {
        Integer jjBegin = Integer.parseInt(dateBegin.getDay());
        Integer mmBegin = Integer.parseInt(dateBegin.getMonth());
        Integer yyBegin = Integer.parseInt(dateBegin.getYear());
        Integer jjEnd = Integer.parseInt(dateEnd.getDay());
        Integer mmEnd = Integer.parseInt(dateEnd.getMonth());
        Integer yyEnd = Integer.parseInt(dateEnd.getYear());
        Integer jj = Integer.parseInt(day);
        Integer mm = Integer.parseInt(month);
        Integer yy = Integer.parseInt(year);
        if((yyBegin > yy) || (yy > yyEnd)) return false;
        if(mmBegin < mmEnd) {
            if((mmBegin > mm) || (mm > mmEnd)) return false;
        }
        if(mmBegin == mmEnd) {
            if((mm != mmBegin) || ((jjBegin > jj) || (jj > jjEnd))) return false;
        }
        if(mmBegin > mmEnd) {
            if((yy == yyBegin) && (mm < mmBegin)) return false;
             if((yy == yyBegin) && (mm == mmBegin) && (jj< jjBegin)) return false;
            if((yy == yyEnd) && (mm > mmEnd)) return false;
            if((yy == yyEnd) && (mm == mmEnd) && (jj > jjEnd)) return false;
        }        
        return true;

    }

    private boolean checkDate() {
        // verifier si la date existe

        return true;
    }

    public String toString() {
         return this.day + "/" + this.month + "/" + this.year;
    }
}
