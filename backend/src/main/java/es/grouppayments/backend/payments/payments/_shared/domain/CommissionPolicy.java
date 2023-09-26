package es.grouppayments.backend.payments.payments._shared.domain;

public interface CommissionPolicy {
    /**
     * Expressed as % of total volume
     */
    double fee();

    /**
     * Take the money and returns all the fee
     */
    default double collecteFee(double money) {
        return (fee() / 100) * money;
    }

    /**
     * Net of money fee
     */
    default double deductFromFee(double money) {
        return money - collecteFee(money);
    }
}
