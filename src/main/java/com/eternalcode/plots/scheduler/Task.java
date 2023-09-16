package com.eternalcode.plots.scheduler;

public interface Task {

    void cancel();

    boolean isCanceled();

    boolean isAsync();

}