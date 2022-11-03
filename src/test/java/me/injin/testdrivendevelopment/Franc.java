package me.injin.testdrivendevelopment;

import java.util.Objects;

public class Franc extends Money {
    public Franc(int amount) {
        super(amount);
    }

    Money times(int multiplier) {
        return new Franc(amount * multiplier);
    }
}
