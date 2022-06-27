package ru.kolodkin.myconverter.model;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@RequiredArgsConstructor
public class Ram {
    @NonNull
    private int idRam;
    private String firm;
    @NonNull
    private String title;
    @NonNull
    private int releaseYear;
    @NonNull
    private Specifications specifications;

    public Ram(@NonNull int idRam, String firm, @NonNull String title, @NonNull int releaseYear, @NonNull Specifications specifications) {
        this.idRam = idRam;
        this.firm = firm;
        this.title = title;
        this.releaseYear = releaseYear;
        this.specifications = specifications;
    }

    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor
    public static class Specifications {
        @NonNull
        private int clockFrequency;
        @NonNull
        private int memory;
    }
}




