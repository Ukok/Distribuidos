package com.ejercicio01.model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author mario
 */
public class Clock {

    private int _hh, _mm, _ss;

    public Clock() {

    }

    public int get_hh() {
        return _hh;
    }

    public void set_hh(int _hh) {
        this._hh = _hh;
    }

    public int get_mm() {
        return _mm;
    }

    public void set_mm(int _mm) {
        this._mm = _mm;
    }

    public int get_ss() {
        return _ss;
    }

    public void set_ss(int _ss) {
        this._ss = _ss;
    }

    /*
    * 
    *
     */
    public void resetClock() {
        this.set_hh(0);
        this.set_mm(0);
        this.set_ss(0);
    }

    public void updateClock(int _hh, int _mm, int _ss) {
        this.set_hh(_hh);
        this.set_mm(_mm);
        this.set_ss(_ss);
    }

    public void systemClock() {
        Date curDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
        String DateToStr = format.format(curDate);
        String[] time = DateToStr.split(":");
        this.set_hh(Integer.parseInt(time[0]));
        this.set_mm(Integer.parseInt(time[1]));
        this.set_ss(Integer.parseInt(time[2]));
    }
    
    public String getStringValue(){
        return String.format("%02d",_hh) + ":" + String.format("%02d",_mm) + ":" + String.format("%02d",_ss);
    }

    @Override
    public String toString() {
        return "Clock{" + _hh + " : " + _mm + " : " + _ss + '}';
    }

}
