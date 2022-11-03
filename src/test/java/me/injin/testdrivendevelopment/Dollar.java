package me.injin.testdrivendevelopment;

public class Dollar extends Money{
    public Dollar(int amount) {
        super(amount);
    }

    Money times(int multiplier) {
        return new Dollar(amount * multiplier);
    }

}
