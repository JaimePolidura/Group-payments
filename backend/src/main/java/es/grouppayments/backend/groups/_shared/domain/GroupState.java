package es.grouppayments.backend.groups._shared.domain;

public enum GroupState {
    PROCESS(true, true, true),
    PAYING(false, false, false);

    //Except changing the state
    private final boolean isEditable;
    private final boolean canMakePayments;
    private final boolean canMembersJoinLeave;

    GroupState(boolean isEditable, boolean canMakePayments, boolean canMembersJoinLeave) {
        this.isEditable = isEditable;
        this.canMakePayments = canMakePayments;
        this.canMembersJoinLeave = canMembersJoinLeave;
    }

    public boolean isEditable(){
        return this.isEditable;
    }

    public boolean canMakePayments() {
        return canMakePayments;
    }

    public boolean canMembersJoinLeave() {
        return canMembersJoinLeave;
    }
}
