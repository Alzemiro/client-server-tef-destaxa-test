package com.destaxa.payment.api.infraestructure.util;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.util.concurrent.atomic.AtomicInteger;

public class NsuGenerator {

    private static final AtomicInteger counter = new AtomicInteger(0);

    public static String generateNsu() {
        int current = counter.incrementAndGet();
        return String.format("%06d", current);
    }
}
