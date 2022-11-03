package me.injin.testdrivendevelopment;

import java.util.Objects;

public class Dollar {
    int amount;
    public Dollar(int amount) {
        this.amount = amount;
    }

    Dollar times(int mutiplier) {
        return new Dollar(amount * mutiplier);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Dollar)) return false;
        Dollar dollar = (Dollar) o;
        return amount == dollar.amount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }
}
