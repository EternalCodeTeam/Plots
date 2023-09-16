package com.eternalcode.plots.notgood.plot.old.protection;

import com.eternalcode.plots.notgood.configuration.implementation.ProtectionConfiguration;
import panda.std.Option;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Protection {

    private Set<Flag> flags = new HashSet<>();

    public Protection(ProtectionConfiguration protectionConfiguration) {
        for (ProtectionConfiguration.ConfigProtection configProtection : protectionConfiguration.getProtections().values()) {
            this.flags.add(new Flag(UUID.randomUUID(), configProtection.getType(), configProtection.getDefaultProtection()));
        }
    }

    public Protection(Set<Flag> flags) {
        this.flags = flags;
    }

    public void setProtection(FlagType flagType, boolean status) {
        for (Flag flag : this.flags) {
            if (flag.getFlagType() == flagType) {
                flag.setStatus(status);
                return;
            }
        }

        this.flags.add(new Flag(UUID.randomUUID(), flagType, status));
    }

    public Option<Boolean> getFlagState(FlagType flagType) {

        for (Flag flag : this.flags) {
            if (flag.getFlagType() == flagType) {
                return Option.of(flag.getStatus());
            }
        }

        return Option.none();
    }

    public Set<Flag> getFlags() {
        return new HashSet<>(this.flags);
    }

}
