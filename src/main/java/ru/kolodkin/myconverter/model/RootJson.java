package ru.kolodkin.myconverter.model;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class RootJson {
    @NonNull
    private List<Rams> rams;
}
