package me.injin.testdrivendevelopment;

public class Money {
    protected int amount;

    public Money(int amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object object) {
        Money money = (Money) object;
        return amount == money.amount;
    }
}
