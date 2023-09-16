package com.eternalcode.plots.util;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class PotionUtilTest {

    @Test
    @DisplayName("Return false when collection contains a negative effect")
    void whenCollectionContainsNegativeEffectReturnFalse() {
        PotionEffect badEffect = Mockito.mock(PotionEffect.class);
        Mockito.when(badEffect.getType()).thenReturn(PotionEffectType.POISON);

        boolean result = PotionUtil.isBadPotion(Collections.singletonList(badEffect));

        assertFalse(result, "Expected false when collection contains a negative effect");
    }

    @Test
    @DisplayName("Return true when collection doesn't contain a negative effect")
    void whenCollectionDoesNotContainNegativeEffectReturnTrue() {
        PotionEffect goodEffect = Mockito.mock(PotionEffect.class);
        Mockito.when(goodEffect.getType()).thenReturn(PotionEffectType.FAST_DIGGING);

        boolean result = PotionUtil.isBadPotion(Collections.singletonList(goodEffect));

        assertTrue(result, "Expected true when collection doesn't contain a negative effect");
    }

    @Test
    @DisplayName("Return true when collection is empty")
    void whenCollectionIsEmptyReturnTrue() {
        boolean result = PotionUtil.isBadPotion(Collections.emptyList());

        assertTrue(result, "Expected true when collection is empty");
    }

    @Test
    @DisplayName("Return false when collection contains both negative and positive effects")
    void whenCollectionContainsBothEffectsReturnFalse() {
        PotionEffect badEffect = Mockito.mock(PotionEffect.class);
        Mockito.when(badEffect.getType()).thenReturn(PotionEffectType.POISON);

        PotionEffect goodEffect = Mockito.mock(PotionEffect.class);
        Mockito.when(goodEffect.getType()).thenReturn(PotionEffectType.FAST_DIGGING);

        boolean result = PotionUtil.isBadPotion(Arrays.asList(badEffect, goodEffect));

        assertFalse(result, "Expected false when collection contains both negative and positive effects");
    }
}