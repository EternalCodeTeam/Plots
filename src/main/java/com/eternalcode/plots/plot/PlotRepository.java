package com.eternalcode.plots.plot;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface PlotRepository {

    CompletableFuture<Plot> loadPlot(UUID plotUUID);

    void savePlot(Plot plot);

    void deletePlot(Plot plot);

}
