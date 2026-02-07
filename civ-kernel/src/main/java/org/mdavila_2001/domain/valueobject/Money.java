package org.mdavila_2001.domain.valueobject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

public record Money(BigDecimal amount, Currency currency) {
    private static final int SCALE = 2;
    private static final RoundingMode ROUNDING = RoundingMode.HALF_UP;

    public Money {
        if (amount == null) throw new IllegalArgumentException("El monto no puede ser nulo");
        if (currency == null) throw new IllegalArgumentException("La moneda no puede ser nula");
        amount = amount.setScale(SCALE, ROUNDING);
    }

    public static Money bob(double amount) {
        return new Money(BigDecimal.valueOf(amount), Currency.getInstance("BOB"));
    }

    public static Money zero(String currencyCode) {
        return new Money(BigDecimal.ZERO, Currency.getInstance(currencyCode));
    }

    public Money add(Money other) {
        checkCurrency(other);
        return new Money(this.amount.add(other.amount), this.currency);
    }

    public Money subtract(Money other) {
        checkCurrency(other);
        return new Money(this.amount.subtract(other.amount), this.currency);
    }

    public Money multiply(double factor) {
        return new Money(this.amount.multiply(BigDecimal.valueOf(factor)), this.currency);
    }

    public Money divide(double divisor) {
        return new Money(this.amount.divide(BigDecimal.valueOf(divisor), SCALE, ROUNDING), this.currency);
    }

    private void checkCurrency(Money other) {
        if (!this.currency.equals(other.currency)) {
            throw new IllegalArgumentException("Error de Divisa: No se puede operar " + this.currency + " con " + other.currency);
        }
    }
}
