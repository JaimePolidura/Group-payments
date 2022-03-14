package es.grouppayments.backend.groups._shared.domain;

public enum GroupState {
    PROCESS(false, true),
    PAYING(true, false),
    CONFIRMING(true, false),
    PAID(true, false);

    private final boolean isWriteBlocked;
    private final boolean canMakePayments;

    GroupState(boolean isWriteBlocked, boolean canMakePayments) {
        this.isWriteBlocked = isWriteBlocked;
        this.canMakePayments = canMakePayments;
    }

    public boolean isWriteBlocked(){
        return this.isWriteBlocked;
    }

    public boolean canMakePayments() {
        return canMakePayments;
    }
}
