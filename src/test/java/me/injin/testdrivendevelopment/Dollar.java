package me.injin.testdrivendevelopment;

import java.util.Objects;

public class Dollar extends Money{
    public Dollar(int amount) {
        super(amount);
    }

    Dollar times(int multiplier) {
        return new Dollar(amount * multiplier);
    }

}
