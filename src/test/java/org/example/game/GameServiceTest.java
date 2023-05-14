package org.example.game;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameServiceTest {
    private final GameService service = new GameService();

    @Test
    public void providedExample() {
        //given
        List<Clan> clans = new ArrayList<>();
        clans.add(new Clan(4, 50));
        clans.add(new Clan(2, 70));
        clans.add(new Clan(6, 60));
        clans.add(new Clan(1, 15));
        clans.add(new Clan(5, 40));
        clans.add(new Clan(3, 45));
        clans.add(new Clan(1, 12));
        clans.add(new Clan(4, 40));

        Players players = new Players(clans,6);

        List<List<Clan>> groups = new ArrayList<>();

        List<Clan> firstGroup = new ArrayList<>();
        firstGroup.add(new Clan(2, 70));
        firstGroup.add(new Clan(4, 50));
        groups.add(firstGroup);

        List<Clan> secondGroup = new ArrayList<>();
        secondGroup.add(new Clan(6, 60));
        groups.add(secondGroup);

        List<Clan> thirdGroup = new ArrayList<>();
        thirdGroup.add(new Clan(3, 45));
        thirdGroup.add(new Clan(1, 15));
        thirdGroup.add(new Clan(1, 12));
        groups.add(thirdGroup);

        List<Clan> fourthGroup = new ArrayList<>();
        fourthGroup.add(new Clan(4, 40));
        groups.add(fourthGroup);

        List<Clan> fifthGroup = new ArrayList<>();
        fifthGroup.add(new Clan(5, 40));
        groups.add(fifthGroup);

        //when
        List<List<Clan>> output = service.calculateOrder(players);

        //then
        for (int i = 0; i < groups.size(); i++) {
            for (int j = 0; j < groups.get(i).size(); j++) {
                assertEquals(groups.get(i).get(j).numberOfPlayers, output.get(i).get(j).numberOfPlayers);
                assertEquals(groups.get(i).get(j).points, output.get(i).get(j).points);
            }
        }

    }
}
