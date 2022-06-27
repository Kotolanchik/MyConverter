package ru.kolodkin.myconverter.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Rams {
    @NonNull
    private String firm;
    @NonNull
    private List<Ram> ram = new ArrayList<>();

    public Rams(@NonNull String firm, @NonNull List<Ram> ram) {
        this.firm = firm;
        this.ram = ram;
    }
}


