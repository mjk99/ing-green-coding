package org.example.atm;

import java.util.Objects;

public final class Task {
    public final int atmId;
    public final int region;
    public final int priority;


    public Task(int atmId, int region, int priority) {
        this.atmId = atmId;
        this.region = region;
        this.priority = priority;
    }

    @Override
    public int hashCode() {
        return Objects.hash(atmId, region, priority);
    }

    @Override
    public boolean equals(Object obj) {
        Task task = (Task) obj;
        return atmId == task.atmId
                && region == task.region
                && priority == task.priority;
    }
}