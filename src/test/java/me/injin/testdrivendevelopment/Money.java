package me.injin.testdrivendevelopment;

abstract class Money {
    protected int amount;

    public Money(int amount) {
        this.amount = amount;
    }

    static Money dollar(int amount) {
        return new Dollar(amount);
    }
    static Money franc(int amount) {
        return new Franc(amount);
    }

    @Override
    public boolean equals(Object object) {
        Money money = (Money) object;
        return amount == money.amount && getClass().equals(money.getClass());
    }

    abstract Money times(int multiplier);
}
