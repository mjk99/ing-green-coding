package org.example.game;

import java.util.List;

public class Players {
    public List<Clan> clans;
    public int groupSize;

    public Players(List<Clan> clans, int groupSize) {
        this.clans = clans;
        this.groupSize = groupSize;
    }
}
