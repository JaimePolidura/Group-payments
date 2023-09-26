package es.grouppayments.backend.payments.userpaymentsinfo._shared.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
public final class Dob {
    @Getter private final long year;
    @Getter private final long month;
    @Getter private final long day;

    public Map<String, Object> toPrimitves(){
        return Map.of(
                "year", this.year,
                "month", this.month,
                "day", this.day
        );
    }

    public static Dob fromPrimitives(Map<String, Object> primitives){
        return new Dob(
                Long.parseLong(String.valueOf(primitives.get("year"))),
                Long.parseLong(String.valueOf(primitives.get("month"))),
                Long.parseLong(String.valueOf(primitives.get("day")))
        );
    }

}
