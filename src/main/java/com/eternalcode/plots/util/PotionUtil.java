package com.eternalcode.plots.util;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.ApiStatus;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public final class PotionUtil {

    private static final List<PotionEffectType> NEGATIVE_EFFECTS = Arrays.stream(NegativeEffects.values())
        .map(NegativeEffects::getEffectType)
        .toList();

    public PotionUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static boolean isBadPotion(Collection<PotionEffect> effects) {
        return effects.stream().noneMatch(effect -> NEGATIVE_EFFECTS.contains(effect.getType()));
    }

    @ApiStatus.Internal
    enum NegativeEffects {
        BAD_OMEN(PotionEffectType.BAD_OMEN),
        BLINDNESS(PotionEffectType.BLINDNESS),
        CONFUSION(PotionEffectType.CONFUSION),
        HARM(PotionEffectType.HARM),
        HUNGER(PotionEffectType.HUNGER),
        INCREASE_DAMAGE(PotionEffectType.INCREASE_DAMAGE),
        POISON(PotionEffectType.POISON),
        SLOW(PotionEffectType.SLOW),
        SLOW_DIGGING(PotionEffectType.SLOW_DIGGING),
        UN_LUCK(PotionEffectType.UNLUCK),
        WEAKNESS(PotionEffectType.WEAKNESS),
        WITHER(PotionEffectType.WITHER);

        final PotionEffectType effectType;

        NegativeEffects(PotionEffectType effectType) {
            this.effectType = effectType;
        }

        public PotionEffectType getEffectType() {
            return effectType;
        }
    }

}