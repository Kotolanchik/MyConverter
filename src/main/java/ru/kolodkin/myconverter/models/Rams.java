package ru.kolodkin.myconverter.models;

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
}

