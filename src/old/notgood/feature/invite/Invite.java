package com.eternalcode.plots.notgood.feature.invite;

import com.eternalcode.plots.good.user.User;

public class Invite {

    private final User invited;
    private final Plot plot;

    public Invite(User invited, Plot plot) {
        this.invited = invited;
        this.plot = plot;
    }

    public User getInvited() {
        return this.invited;
    }

    public Plot getPlot() {
        return this.plot;
    }
}
