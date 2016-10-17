package com.ejercicio05.model;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author mario
 */
public class Nodo implements Cloneable, Serializable {

    private String addr;
    private String obj_name;
    private int timestamp;

    public Nodo(String addr, String obj_name) {
        this.addr = addr;
        this.obj_name = obj_name;
        this.timestamp = 0;
    }

    public static String getRMIurl(String addr, String obj_name) {
        return "rmi://" + addr + "/" + obj_name;
    }

    public String getRMIurl() {
        return "rmi://" + this.addr + "/" + this.obj_name;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getObj_name() {
        return obj_name;
    }

    public void setObj_name(String obj_name) {
        this.obj_name = obj_name;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Nodo other = (Nodo) obj;
        if (!Objects.equals(this.addr, other.addr)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Nodo{" + "addr=" + addr + ", obj_name=" + obj_name + ", timestamp=" + timestamp + '}';
    }

}
