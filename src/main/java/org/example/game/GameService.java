package org.example.game;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

final class GameService {

    /**
     * 1. Sorts clans by points, if points are equal then decreasingly by number of members
     * 2. Assigns each clan to a bin, which is a group of clans that will be let into event together
     * 3. If bin is full, creates a new bin
     * 4. Unpacks bins to get a list of groups of clans
     *
     * The problem solved by this method is variation of Knapsack and Bin Packing problems.
     * The differences between this and classic knapsack problem is
     * 1) list of items in the bin has to be returned, instead of just value of items in each bin
     * 2) items have to be inserted in certain order, instead of just maximizing value
     *
     *  Time complexity O(n^2)
     *
     * @param players {@link Players}
     * @return ordered list of groups to let into event {@link List<List<Clan>>}
     */
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
        final List<Clan> items;

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

