package it.hurts.weever.rotp_lovers.action.stand;

import com.github.standobyte.jojo.action.stand.StandEntityAction;

public class NothingForAntiCrash extends StandEntityAction {
    public NothingForAntiCrash(Builder builder) {
        super(builder);
    }
    @Override
    public boolean enabledInHudDefault() {
        return false;
    }
}