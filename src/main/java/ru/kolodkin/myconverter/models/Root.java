package ru.kolodkin.myconverter.models;

import java.util.List;

/*
* Обёртка.
* */
public class Root {
    private List<Rams> rams;

    public Root() {
    }

    public Root(List<Rams> rams) {
        this.rams = rams;
    }

    public List<Rams> getRams() {
        return rams;
    }

    public void setRams(List<Rams> rams) {
        this.rams = rams;
    }
}
