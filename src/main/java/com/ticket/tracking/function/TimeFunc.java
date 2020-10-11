package com.ticket.tracking.function;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class TimeFunc {

    public String localDateTime() {

        Instant instant = Instant.now();
        long timeStampMillis = instant.toEpochMilli();
        LocalDate ld = Instant.ofEpochMilli(timeStampMillis)
                .atZone(ZoneId.systemDefault()).toLocalDate();

        return ld.toString();
    }
}
