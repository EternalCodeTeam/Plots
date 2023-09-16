package com.eternalcode.plots.notgood;

import com.eternalcode.plots.good.util.InstantFormatUtil;
import org.jetbrains.annotations.ApiStatus;
import panda.utilities.text.Formatter;

import java.util.ArrayList;
import java.util.List;

@Deprecated
@ApiStatus.ScheduledForRemoval
public final class VariablesUtils {

    private VariablesUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static List<String> parsePlotVars(Plot plot, List<String> texts) {
        List<String> output = new ArrayList<>();

        for (String text : texts) {
            output.add(parsePlotVars(plot, text));
        }

        return output;
    }

    public static String parsePlotVars(Plot plot, String text) {
        Formatter register = new Formatter()
            .register("{PLOT_NAME}", plot.getName())
            .register("{PLOT_UUID}", plot.getUuid().toString())
            .register("{PLOT_OWNER_NAME}", plot.getOwner().getUser().getName())
            .register("{PLOT_OWNER_UUID}", plot.getOwner().getUser().getUuid().toString())
            .register("{PLOT_MEMBERS}", String.valueOf(plot.getMembers().size()))
            .register("{PLOT_CREATED}", InstantFormatUtil.format(plot.getCreated()))
            .register("{PLOT_EXPIRES}", InstantFormatUtil.format(plot.getExpires()))
            .register("{PLOT_SIZE}", String.valueOf(plot.getRegion().getSize()))
            .register("{PLOT_RANGE}", String.valueOf(plot.getRegion().getRange()));

        return register.format(text);
    }

    public static List<String> parseVariable(List<String> list, String target, String replacement) {
        List<String> output = new ArrayList<>();

        for (String text : list) {
            output.add(text.replace(target, replacement));
        }

        return output;
    }

}
