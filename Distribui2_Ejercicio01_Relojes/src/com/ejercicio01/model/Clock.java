package com.ejercicio01.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author mario
 */
public class Clock implements Serializable {

   private int _hh, _mm, _ss, _ms;
   private double ciclo;

   public Clock() {
      _hh = _mm = _ss = _ms = 0;
      this.ciclo = 10;
   }

   public Clock(int _hh, int _mm, int _ss, int _ms) {
      this._hh = _hh;
      this._mm = _mm;
      this._ss = _ss;
      this._ms = _ms;
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
      this.ciclo = 10;
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

   /**
    * Establece el valor del reloj del sistema.
    */
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

   /**
    * Regresa el valor string del reloj con formato HH:mm:ss.
    *
    * @return Valor String del reloj con formato HH:mm:ss.
    */
   public String getStringValue() {
      return String.format("%02d", _hh) + ":"
              + String.format("%02d", _mm) + ":"
              + String.format("%02d", _ss);
      // + ":" + String.format("%03d", _ms);
   }

   @Override
   public String toString() {
      return "Clock{" + _hh + " : " + _mm + " : " + _ss + " : " + _ms + '}';
   }

   public int ms_sum(int i) {
      this._ms += i;
      return _ms;
   }

   public int ss_sum(int i) {
      this._ss += i;
      return _ss;
   }

   public int mm_sum(int i) {
      this._mm += i;
      return _mm;
   }

   public int hh_sum(int i) {
      this._hh += i;
      return _hh;
   }

   public void clickClack() {
      if (ms_sum(10) >= 1000) {
         set_ms(0);
         if (ss_sum(1) == 60) {
            set_ss(0);
            if (mm_sum(1) == 60) {
               set_mm(0);
               if (hh_sum(1) == 24) {
                  set_hh(0);
               }
            }
         }
      }
   }

   /**
    * Convierte el valor del reloj a milisegundos.
    *
    * @return milisecondsValue.
    */
   public int getMilisecondsValue() {
      int milisecondsValue = 0;
      milisecondsValue = _hh * 60; // horas a minutos.
      milisecondsValue += _mm;
      milisecondsValue *= 60;      // minutos a segundos.
      milisecondsValue += _ss;
      milisecondsValue *= 1000;    // segundos a milisegundos.
      milisecondsValue += _ms;
      return milisecondsValue;
   }

   /**
    * Convierte el valor del reloj a segundos.
    *
    * @return secondsValue.
    */
   public double getSecondsValue() {
      double secondsValue;
      secondsValue = (_hh * 60);      // horas a minutos.
      secondsValue += _mm;
      secondsValue *= 60.0;           // minutos a segundos.
      secondsValue += _ss;
      secondsValue += (_ms / 1000.0); // milisegundos a segundos y se suma. 
      return secondsValue;
   }

   /**
    * Convierte un valor en milisegundos a un valor de reloj.
    *
    * @param milisecondsValue
    */
   public void setWithMilisecondsValue(int milisecondsValue) {
      // Obtener milisegundos.
      _ms = milisecondsValue % 1000;
      //milisecondsValue = (milisecondsValue - _ms) / 1000;
      milisecondsValue /= 1000;

      // Obtener segundos.
      _ss = milisecondsValue % 60;
      //milisecondsValue = (milisecondsValue - _ss) / 60;
      milisecondsValue /= 60;

      // Obtener minutos.
      _mm = milisecondsValue % 60;

      // Obtener horas.
      //_hh = (milisecondsValue - _mm) / 60;
      _hh = milisecondsValue / 60;
   }

   /**
    * Convierte un valor en segundos a un valor de reloj.
    *
    * @param secondsValue
    */
   public void setWithSecondsValue(double secondsValue) {
      secondsValue *= 1000;
      setWithMilisecondsValue((int) secondsValue);
   }

   public double getCiclo() {
      return ciclo;
   }

   public void setCiclo(double ciclo) {
      this.ciclo = ciclo;
   }

}
