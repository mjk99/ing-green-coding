package org.example.atm;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AtmServiceTest {
    private final AtmService atmService = new AtmService();

    @Test
    public void providedExample1() {
        //given
        List<Task> exampleInput = new ArrayList<>();

        exampleInput.add(new Task(1, 4, priorityOf("STANDARD")));
        exampleInput.add(new Task(1, 1, priorityOf("STANDARD")));
        exampleInput.add(new Task(1, 2, priorityOf("STANDARD")));
        exampleInput.add(new Task(2, 3, priorityOf("PRIORITY")));
        exampleInput.add(new Task(1, 3, priorityOf("STANDARD")));
        exampleInput.add(new Task(1, 2, priorityOf("SIGNAL_LOW")));
        exampleInput.add(new Task(2, 5, priorityOf("STANDARD")));
        exampleInput.add(new Task(1, 5, priorityOf("FAILURE_RESTART")));

        List<Task> exampleOutput = new ArrayList<>();
        exampleOutput.add(new Task(1, 1, 0));
        exampleOutput.add(new Task(1, 2, 0));
        exampleOutput.add(new Task(2, 3, 0));
        exampleOutput.add(new Task(1, 3, 0));
        exampleOutput.add(new Task(1, 4, 0));
        exampleOutput.add(new Task(1, 5, 0));
        exampleOutput.add(new Task(2, 5, 0));

        //when
        List<Task> output = atmService.calculateOrder(exampleInput);

        //then
        for (int i = 0; i < output.size(); i++) {
            assertEquals(exampleOutput.get(i).region, output.get(i).region);
            assertEquals(exampleOutput.get(i).atmId, output.get(i).atmId);
        }
    }

    @Test
    public void providedExample2() {
        //given
        List<Task> exampleInput = new ArrayList<>();
        exampleInput.add(new Task(2, 1, priorityOf("STANDARD")));
        exampleInput.add(new Task(1, 1, priorityOf("STANDARD")));
        exampleInput.add(new Task(3, 2, priorityOf("PRIORITY")));
        exampleInput.add(new Task(4, 3, priorityOf("STANDARD")));
        exampleInput.add(new Task(5, 4, priorityOf("STANDARD")));
        exampleInput.add(new Task(2, 5, priorityOf("PRIORITY")));
        exampleInput.add(new Task(1, 5, priorityOf("STANDARD")));
        exampleInput.add(new Task(2, 3, priorityOf("SIGNAL_LOW")));
        exampleInput.add(new Task(1, 2, priorityOf("SIGNAL_LOW")));
        exampleInput.add(new Task(1, 3, priorityOf("FAILURE_RESTART")));

        List<Task> exampleOutput = new ArrayList<>();
        exampleOutput.add(new Task(2, 1, 0));
        exampleOutput.add(new Task(1, 1, 0));
        exampleOutput.add(new Task(3, 2, 0));
        exampleOutput.add(new Task(1, 2, 0));
        exampleOutput.add(new Task(1, 3, 0));
        exampleOutput.add(new Task(2, 3, 0));
        exampleOutput.add(new Task(4, 3, 0));
        exampleOutput.add(new Task(5, 4, 0));
        exampleOutput.add(new Task(2, 5, 0));
        exampleOutput.add(new Task(1, 5, 0));

        //when
        List<Task> output = atmService.calculateOrder(exampleInput);

        //then
        for (int i = 0; i < output.size(); i++) {
            assertEquals(exampleOutput.get(i).region, output.get(i).region);
            assertEquals(exampleOutput.get(i).atmId, output.get(i).atmId);
        }
    }

    private int priorityOf(String status) {
        return switch (status) {
            case "STANDARD" -> 0;
            case "SIGNAL_LOW" -> 1;
            case "PRIORITY" -> 2;
            case "FAILURE_RESTART" -> 3;
            default -> 0;
        };
    }
}
