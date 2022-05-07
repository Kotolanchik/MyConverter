package ru.kolodkin.myconverter.models;

/*
* Оперативная память.
* */
public class Ram {
    private int idRam;
    private String firm;
    private String title;
    private int releaseYear;
    private Specifications specifications = new Specifications();

    public Ram() {
    }

    public Ram(int idRam, String title, int releaseYear, Specifications specifications) {
        this.idRam = idRam;
        this.title = title;
        this.releaseYear = releaseYear;
        this.specifications = specifications;
    }

    public Specifications getSpecifications() {
        return specifications;
    }

    public int getIdRam() {
        return idRam;
    }

    public void setIdRam(int idRam) {
        this.idRam = idRam;
    }

    public String getFirm() {
        return firm;
    }

    public void setFirm(String firm) {
        this.firm = firm;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public void setSpecifications(Specifications specifications) {
        this.specifications = specifications;
    }

    /*Особенности оперативной памяти.*/
    public static class Specifications {
        private int clockFrequency;
        private int memory;

        public Specifications() {
        }

        public int getClockFrequency() {
            return clockFrequency;
        }
        public void setClockFrequency(int clockFrequency) {
            this.clockFrequency = clockFrequency;
        }
        public int getMemory() {
            return memory;
        }
        public void setMemory(int memory) {
            this.memory = memory;
        }
    }

    @Override
    public String toString() {
        return "Ram{" +
                "idRam=" + idRam +
                ", firm='" + firm + '\'' +
                ", title='" + title + '\'' +
                ", releaseYear=" + releaseYear +
                ", specifications= {memory: " + specifications.getMemory() + "  clockFrequency: " + specifications.getClockFrequency() +
                "}}";
    }
}




