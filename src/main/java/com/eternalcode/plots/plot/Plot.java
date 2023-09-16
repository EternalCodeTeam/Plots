package com.eternalcode.plots.plot;

import java.time.Instant;
import java.util.UUID;

public record Plot(
    UUID plotId,
    String name,

    Instant createdAt,
    Instant expireAt) {
}