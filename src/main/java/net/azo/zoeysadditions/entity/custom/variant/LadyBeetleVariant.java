package net.azo.zoeysadditions.entity.custom.variant;

import java.util.Arrays;
import java.util.Comparator;

public enum LadyBeetleVariant {
    GOLD(0),
    ONE_DOT(1),
    TWO_DOT(2),
    THREE_DOT(3),
    FOUR_DOT(4),
    FIVE_DOT(5),
    SIX_DOT(6),
    TRANS(7);

    private static final LadyBeetleVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.
            comparingInt(LadyBeetleVariant::getId)).toArray(LadyBeetleVariant[]::new);
    private final int id;

    LadyBeetleVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static LadyBeetleVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}