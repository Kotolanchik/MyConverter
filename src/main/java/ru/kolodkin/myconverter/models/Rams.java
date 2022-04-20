package ru.kolodkin.myconverter.models;

import java.util.ArrayList;
import java.util.List;

/*
* Оперативки по фирмам.
* */
public class Rams {
    private String firm;
    private List<Ram> ram = new ArrayList<>();

    public String getFirm() {
        return firm;
    }

    public Rams() {
    }

    public Rams(List<Ram> ram, String firm) {
        this.ram = ram;
        this.firm = firm;
    }

    public void setFirm(String firm) {
        this.firm = firm;
    }

    public Rams(List<Ram> ram) {
        this.ram = ram;
    }

    public List<Ram> getRam() {
        return ram;
    }

    public void setRam(List<Ram> ram) {
        this.ram = ram;
    }

    @Override
    public String toString() {
        return "Rams{" +
                "ram=" + ram +
                ", firm='" + firm + '\'' +
                '}';
    }
}

