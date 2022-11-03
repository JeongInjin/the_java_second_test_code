package me.injin.testdrivendevelopment;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class MoneyTest {

    @Test
    void testMultiplication() {
        Money five = Money.dollar(5);
        assertThat(Money.dollar(10)).isEqualTo(five.times(2));
        assertThat(Money.dollar(15)).isEqualTo(five.times(3));
    }

    @Test
    void testFrancMultiplication() {
        Money five = Money.franc(5);
        assertThat(Money.franc(10)).isEqualTo(five.times(2));
        assertThat(Money.franc(15)).isEqualTo(five.times(3));
    }

    @Test
    void testEquality() {
        assertThat(Money.dollar(5).equals(Money.dollar(5))).isTrue();
        assertThat(Money.dollar(5).equals(Money.dollar(6))).isFalse();
        assertThat(Money.franc(5).equals(Money.franc(5))).isTrue();
        assertThat(Money.franc(5).equals(Money.franc(6))).isFalse();
        assertThat(Money.franc(5).equals(Money.dollar(5))).isFalse();
    }

    @Test
    void testCurrency() {
        assertThat(Money.dollar(1).currency()).isEqualTo("USD");
        assertThat(Money.franc(1).currency()).isEqualTo("CHF");
    }

    @Test
    void testDifferentClassEquality() {
        assertThat(new Money(10, "CHF").equals(new Franc(10, "CHF"))).isTrue();
    }
}
