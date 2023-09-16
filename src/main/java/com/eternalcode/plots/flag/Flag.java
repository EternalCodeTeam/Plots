package com.eternalcode.plots.flag;

import java.util.UUID;

public record Flag(UUID plotId, FlagType flagType, boolean value) {
}