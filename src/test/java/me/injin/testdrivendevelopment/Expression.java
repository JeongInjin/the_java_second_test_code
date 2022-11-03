package me.injin.testdrivendevelopment;

public interface Expression {
    Money reduce(Bank bank, String to);
}
