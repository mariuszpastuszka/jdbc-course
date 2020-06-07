package pl.mpas.jdbc_course.model;

import java.util.stream.Stream;

public enum Sex {
    MALE('M'), FEMALE('F');
    private char sexMark;

    Sex(char sexMark) {
        this.sexMark = sexMark;
    }

    public char getSexMark() {
        return sexMark;
    }

    public static Sex fromChar(char sexMark) {
        return Stream.of(Sex.values())
                .filter(sex -> sex.sexMark == sexMark)
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Wrong constant used: " + sexMark));
    }
}
