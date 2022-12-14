package me.injin.testdrivendevelopment;

public class Sum implements Expression{
    Expression augend;
    Expression addend;

    public Sum(Expression augend, Expression addend) {
        this.augend = augend;
        this.addend = addend;
    }

    public Expression times(int multiplier){
        return new Sum(augend.times(multiplier), addend.times(multiplier));
    }


    @Override
    public Money reduce(Bank bank, String to){
        int amount = augend.reduce(bank, to).amount + addend.reduce(bank, to).amount;
        return new Money(amount, to);
    }

    @Override
    public Expression plus(Expression tenFrancs) {
        return new Sum(this, addend);
    }
}
