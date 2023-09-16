package com.eternalcode.plots.notgood.feature.invite;

import com.eternalcode.plots.notgood.plot.old.PlotManager;
import com.eternalcode.plots.good.user.User;
import panda.std.Option;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class InviteManager {

    private final PlotManager plotManager;
    private final Set<Invite> invites = new HashSet<>();

    public InviteManager(PlotManager plotManager) {
        this.plotManager = plotManager;
    }

    public void create(User invited, Plot plot) {
        this.invites.add(new Invite(invited, plot));
    }

    public void accept(User invited, Plot plot) {
        this.plotManager.addMember(plot, invited);

        this.removeInvite(invited, plot);
    }

    public void decline(User invited, Plot plot) {
        this.removeInvite(invited, plot);
    }

    public boolean hasInvite(User invited, Plot plot) {
        return this.getInvite(invited, plot).isPresent();
    }

    private void removeInvite(User invited, Plot plot) {
        this.invites.remove(this.getInvite(invited, plot).get());
        //this.getInvite(invited, plot).peek(this.invites::remove);
    }

    private Option<Invite> getInvite(User invited, Plot plot) {
        for (Invite invite : this.invites) {
            if (invite.getInvited().equals(invited) && invite.getPlot().equals(plot)) {
                return Option.of(invite);
            }
        }

        return Option.none();
    }

    public List<Plot> getInviters(User user) {
        List<Plot> inviters = new ArrayList<>();

        for (Invite invite : invites) {
            if (user.equals(invite.getInvited())) {
                inviters.add(invite.getPlot());
            }
        }

        return inviters;
    }

}
