package com.ejercicio01.model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author mario
 */
public class Clock {

    private int _hh, _mm, _ss, _ms;

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

    public int get_ms() {
        return _ms;
    }

    public void set_ms(int _ms) {
        this._ms = _ms;
    }

    public void resetClock() {
        this.set_hh(0);
        this.set_mm(0);
        this.set_ss(0);
        this.set_ms(0);
    }

    public void updateClock(int _hh, int _mm, int _ss) {
        this.set_hh(_hh);
        this.set_mm(_mm);
        this.set_ss(_ss);
    }
    
    public void updateClock(int _hh, int _mm, int _ss, int _ms) {
        this.set_hh(_hh);
        this.set_mm(_mm);
        this.set_ss(_ss);
        this.set_ms(_ms);
    }

    public void systemClock() {
        Date curDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss:SSS");
        String DateToStr = format.format(curDate);
        System.out.println(DateToStr);
        String[] time = DateToStr.split(":");
        this.set_hh(Integer.parseInt(time[0]));
        this.set_mm(Integer.parseInt(time[1]));
        this.set_ss(Integer.parseInt(time[2]));
        this.set_ms(Integer.parseInt(time[3]));
    }

    public String getStringValue() {
        return String.format("%02d", _hh) + ":" 
                + String.format("%02d", _mm) + ":" 
                + String.format("%02d", _ss);
//                + ":" + String.format("%03d", _ms);
    }

    @Override
    public String toString() {
        return "Clock{" + _hh + " : " + _mm + " : " + _ss + ":" + _ms + '}';
    }
    
    
    public int ms_sum(int i){
        this._ms += i;
        return _ms;
    }
    public int ss_sum(int i){
        this._ss += i;
        return _ss;
    }
    public int mm_sum(int i){
        this._mm += i;
        return _mm;
    }
    public int hh_sum(int i){
        this._hh += i;
        return _hh;
    }
}
