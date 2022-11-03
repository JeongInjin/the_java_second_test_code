package me.injin.testdrivendevelopment;

import java.util.Objects;

public class Dollar {
    private int amount;
    public Dollar(int amount) {
        this.amount = amount;
    }

    Dollar times(int mutiplier) {
        return new Dollar(amount * mutiplier);
    }

    @Override
    public boolean equals(Object object) {
        Dollar dollar = (Dollar) object;
        return amount == dollar.amount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }
}
