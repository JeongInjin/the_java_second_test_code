package me.injin.testdrivendevelopment;

public interface Expression {
    Money reduce(Bank bank, String to);

    Expression plus(Expression tenFrancs);
}
