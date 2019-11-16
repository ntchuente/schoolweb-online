/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kerem.commons;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author DEV_4
 */
public class DateHelper {
    
    
    /**
     * 
     * @param date
     * @return 
     */
    public static Date getFirstDayOfMonth(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int day = c.getActualMinimum(Calendar.DAY_OF_MONTH);
        c.set(Calendar.DAY_OF_MONTH, day);
        return c.getTime();
    }
    
    /**
     * 
     * @param date
     * @return 
     */
    public static Date getLastDayOfMonth(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int day = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        c.set(Calendar.DAY_OF_MONTH, day);
        return c.getTime();
    }
    
    /**
     * Renvoi le nom du mois correspondant
     * @param date
     * @return 
     */
    public static String getMonthName(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        switch(c.get(Calendar.MONTH)){
            case Calendar.JANUARY :
                return "Janvier";
            case Calendar.FEBRUARY:
                return "Février";
            case Calendar.MARCH:
                return "Mars";
            case Calendar.APRIL:
                return "Avril";
            case Calendar.MAY:
                return "Mai";
            case Calendar.JUNE:
                return "Juin";
            case Calendar.JULY:
                return "Juillet";
            case Calendar.AUGUST:
                return "Août";
            case Calendar.SEPTEMBER:
                return "Septembre";
            case Calendar.OCTOBER:
                return "Octobre";
            case Calendar.NOVEMBER:
                return "Novembre";
            default :
                return "Decembre";
        }
    }
    /**
     * Renvoie la date dans 
     * @param debut
     * @param jour
     * @return 
     */
    public static Date next(Date debut , int jour){        
        if(debut==null)
                return null;
        
        Calendar c = Calendar.getInstance();
        c.setTime(debut);
        c.add(Calendar.DATE, jour);
        return c.getTime();
    }
    
    public static Date nextJoursOuvrees(Date debut , int jour){
         if(debut==null)
                return null;
        int nbre = 1;
        Calendar c = Calendar.getInstance();
        c.setTime(debut);
        while(nbre<=jour){
            c.add(Calendar.DATE, 1);
            if(c.get(Calendar.DAY_OF_WEEK)!=Calendar.SUNDAY
                    && c.get(Calendar.DAY_OF_WEEK)!=Calendar.SATURDAY){
                nbre++;
            }//end if(c.get(Calendar.DAY_OF_WEEK)!=Calendar.SUNDAY
        }//end  while(nbre<=jour){        
        return c.getTime();
    }
    
    /**
     * Verifie que une date est comprise entre 2 autres  date
     * @param date
     * @param debut
     * @param fin
     * @return 
     */
    public static Boolean between(Date date , Date debut, Date fin){
        
        if(convertToString(date, "yyyyMMdd").compareTo(convertToString(debut, "yyyyMMdd"))>=0
                &&convertToString(date, "yyyyMMdd").compareTo(convertToString(fin, "yyyyMMdd"))<=0)
            return true;
        return false;
    }
    
    /**
     * 
     * @param begin
     * @param end
     * @param time
     * @return 
     */
    public static long times(Date begin , Date end , Date time){
        
        Calendar c = Calendar.getInstance();
        
        c.setTime(end);
        
        c.set(Calendar.HOUR_OF_DAY, time.getHours());
        c.set(Calendar.MINUTE, time.getMinutes());
        c.set(Calendar.SECOND, time.getSeconds());
        
        long times = c.getTimeInMillis()-begin.getTime();
//        System.out.println("DateHelper.times(Date begin , Date end , Date time) ::::::::::::::::::::::::::::::::::::  "+c.getTime());
        return times;
    }
    
    /**
     * Convertion date en String
     * @param date
     * @param pattern
     * @return 
     */
    public static String convertToString(Date date,String pattern){
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
            return dateFormat.format(date);
    }
    
    public static Date dayofWeek(Date date , int day){
        
         Date result = null ;
        
        Calendar c = Calendar.getInstance();
        
        c.setTime(date);
        
        if(day==Calendar.MONDAY){
            c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            result = c.getTime();
        }else if(day==Calendar.TUESDAY){
            c.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
            result = c.getTime();
        }else if(day==Calendar.WEDNESDAY){
            c.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
            result = c.getTime();
        }else if(day==Calendar.THURSDAY){
            c.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
            result = c.getTime();
        }else if(day==Calendar.FRIDAY){
            c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
            result = c.getTime();
        }else if(day==Calendar.SATURDAY){
            c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
            result = c.getTime();
        }else if(day==Calendar.SUNDAY){
            c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
            result = c.getTime();
        }
        
        if(result.compareTo(date)<0)
            return null;
         //System.out.println("SauvegardeCromHelper.computeSavePeriode(Date debut , Date fin, CycleSauvegarde cycle , SemaineSauvegarde semaine , JourSauvegarde jour) :::::::::::::: "+c.getTime()+"::::::::::::::::::::::: "+date);
        return result;
    }
    /**
     * 
     * @param date 
     */
    public static Date  nextWeek(Date date){
        
        Date result = null ;
        
        Calendar c = Calendar.getInstance();
        
        c.setTime(date);
      
        if(c.get(Calendar.DAY_OF_WEEK)==Calendar.MONDAY){
            result = next(c.getTime(), 6);
        }else if(c.get(Calendar.DAY_OF_WEEK)==Calendar.TUESDAY){
          result = next(c.getTime(), 5);
        }else if(c.get(Calendar.DAY_OF_WEEK)==Calendar.WEDNESDAY){
          result = next(c.getTime(), 4);
        }else if(c.get(Calendar.DAY_OF_WEEK)==Calendar.THURSDAY){
          result = next(c.getTime(), 3);
        }else if(c.get(Calendar.DAY_OF_WEEK)==Calendar.FRIDAY){
          result = next(c.getTime(), 2);
        }else if(c.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY){
          result = next(c.getTime(), 1);
        }
        return result;
    }
    
    /**
     * Renvoide le premier jour du mois suivant
     * @param date
     * @return 
     */
    public static Date nextMonth(Date date){
        
        Date result = null ;
        
        Calendar c = Calendar.getInstance();
        
         c.setTime(date);
         
        c.add(Calendar.MONTH, 1);
        
        c.set(Calendar.DAY_OF_MONTH, 1);
        
        result = c.getTime();
                
        return result;
        
    }
    
    /**
     * Renvoide le premier jour du mois suivant
     * @param date
     * @param horizon
     * @return 
     */
    public static Date nextMonth(Date date , int horizon){
        
        if(horizon<=0){
            return date;
        }
        Date result = null ;
        
        Calendar c = Calendar.getInstance();
        
         c.setTime(date);
         
        c.add(Calendar.MONTH, horizon);
        
        c.set(Calendar.DAY_OF_MONTH, 1);
        
        result = c.getTime();
                
        return result;
        
    }
    
    /**
     * Renvoie le 1er jour de la week semaine du mois a
     * @param date
     * @param week
     * @return 
     */
    public static Date week(Date date , int week){
        
        if(date==null)
            return null ;
        
        Calendar c = Calendar.getInstance();
        
        c.setTime(date);
        
        c.set(Calendar.WEEK_OF_MONTH, week);
        
      
        
       return c.getTime();
    }
    
    /**
     * 
     * @param begin
     * @param end
     * @return 
     */
    public static int numberOfMonth(Date begin , Date end){
        int number = 0;
        
        Date debut = begin;        
        while(convertToString(debut, "yyyy-MM-dd").compareTo(convertToString(end, "yyyy-MM-dd"))<=0){
            number = number+1 ;
            debut = nextMonth(debut);
        }//end while(convertToString(debut, "yyyy-MM-dd").compareTo(convertToString(end, "yyyy-MM-dd"))<=0)
        return number;
    }
    
    /**
     * 
     * @param begin
     * @param end
     * @return 
     */
    public static int numberOfDays(Date begin , Date end){
        int number = 0;
        
        Date debut = begin;        
        while(convertToString(debut, "yyyy-MM-dd").compareTo(convertToString(end, "yyyy-MM-dd"))<=0){
            number = number+1 ;
            debut = next(debut,1);
        }//end while(convertToString(debut, "yyyy-MM-dd").compareTo(convertToString(end, "yyyy-MM-dd"))<=0)
        return number;
    }
    
    /**
     * 
     * @param heure
     * @return 
     */
    public static Double getHours(String heure){
        Double hours = 0.0;
        int index = 1 ;
        String[] bsplit = heure.split(":");
        for(String block:bsplit){
            if(index==1){
                hours+=Double.parseDouble(block)*60*60;
            }else if(index==2){
                hours+=Double.parseDouble(block)*60;
            }else if(index==3){
                hours+=Double.parseDouble(block);
            }
        }//end for(String block:bsplit){
        return hours;
    }
    /**
     * Hours format HH:MM:SS
     * @param begin
     * @param end
     * @return 
     */
    public static Double numberHours(String begin , String end){
        return getHours(end)-getHours(begin);
    }
    
//    /**
//     * 
//     * @param debut
//     * @param fin
//     * @return 
//     */
//  public static Map<int> periodeExercice(Date debut , Date fin){
//      List<String> result = new ArrayList<String>();
//      Date begin = debut;
//      while(begin.compareTo(fin)<=0){
//          StringBuffer buffer = new StringBuffer();
//          buffer.append(getMonthName(begin));
//          buffer.append(begin.getYear()+1900);
//          result.add(buffer.toString());
//          begin = nextMonth(begin);
//      }
//      return result ;
//  }
}
