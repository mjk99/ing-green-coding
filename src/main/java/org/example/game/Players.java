package org.example.game;

import java.util.List;

public final class Players {
    public final List<Clan> clans;
    public final int groupSize;

    public Players(List<Clan> clans, int groupSize) {
        this.clans = clans;
        this.groupSize = groupSize;
    }
}
