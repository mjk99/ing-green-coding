package org.example.game;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GameService {

    public List<List<Clan>> calculateOrder(Players players) {
        List<Clan> clans = players.clans;
        int binSize = players.groupSize;

        clans.sort(new ClanComparator());

        List<Bin> bins = new ArrayList<>();
        bins.add(new Bin(binSize));

        for (Clan clan : clans) {
            boolean packed = false;

            for (Bin bin : bins) {
                if (bin.addItem(clan)) {
                    packed = true;
                    break;
                }
            }

            if (!packed) {
                Bin newBin = new Bin(binSize);
                newBin.addItem(clan);
                bins.add(newBin);
            }
        }

        return bins.stream().map(bin -> bin.items).toList();
    }

    private static class ClanComparator implements Comparator<Clan> {
        @Override
        public int compare(Clan clan1, Clan clan2) {
            int result = Integer.compare(clan2.points, clan1.points);
            if (result != 0) {
                return result;
            }
            return clan1.numberOfPlayers - clan2.numberOfPlayers;
        }
    }

    private static class Bin {
        int remainingCapacity;
        List<Clan> items;

        public Bin(int capacity) {
            this.remainingCapacity = capacity;
            this.items = new ArrayList<>();
        }

        public boolean addItem(Clan clan) {
            if (clan.numberOfPlayers <= remainingCapacity) {
                items.add(clan);
                remainingCapacity -= clan.numberOfPlayers;
                return true;
            } else {
                return false;
            }
        }
    }
}

