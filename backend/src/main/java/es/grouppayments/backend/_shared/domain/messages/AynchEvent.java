package es.grouppayments.backend._shared.domain.messages;

public interface AynchEvent extends AsyncMessage{
    @Override
    default AsyncMessageType type() {
        return AsyncMessageType.EVENT;
    }
}
