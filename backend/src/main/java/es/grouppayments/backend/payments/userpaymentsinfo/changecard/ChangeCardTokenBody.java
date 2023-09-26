package es.grouppayments.backend.payments.userpaymentsinfo.changecard;

import es.grouppayments.backend.payments.userpaymentsinfo._shared.domain.Dob;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

@AllArgsConstructor
@ToString
public final class ChangeCardTokenBody {
    @Getter private final String paymentMethod;
    @Getter private final Dob dob;

    public Map<String, Object> toPrimitives(){
        return Map.of(
                "paymentMethod", this.paymentMethod,
                "dob", dob.toPrimitves()
        );
    }

    public static ChangeCardTokenBody fromPrimitives(Map<String, Object> primitives){
        return new ChangeCardTokenBody(
                String.valueOf(primitives.get("paymentMethod")),
                Dob.fromPrimitives((Map<String, Object>) primitives.get("dob"))
        );
    }
}
