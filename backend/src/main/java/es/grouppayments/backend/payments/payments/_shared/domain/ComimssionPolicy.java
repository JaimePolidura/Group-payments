package es.grouppayments.backend.payments.payments._shared.domain;

public interface ComimssionPolicy {
    double collecteFee(double money);

    default double deductCommission(double money) {
        return money - collecteFee(money);
    }
}
