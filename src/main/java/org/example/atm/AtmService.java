package org.example.atm;

import java.util.*;
import java.util.stream.Stream;

class AtmService {
    public List<Task> calculateOrder(List<Task> tasks) {
        // 1. Split By Regions
        // 2. Sort Map by Region
        // 3. Sort tasks within region by priority
        // 4. Remove duplicates within region
        return tasksByRegion(tasks).entrySet()
                .stream()
                .sorted(Comparator.comparingInt(Map.Entry::getKey))
                .map(entry -> sortByPriorityWithinRegion(entry.getValue()))
                .flatMap(this::removeDuplicatesInRegion)
                .toList();
    }

    private Stream<Task> removeDuplicatesInRegion(List<Task> tasksInRegion) {
        // important to leave first occurrence of task when removing duplicates
        // otherwise task might be less prioritized than it should
        Set<Integer> ids = new HashSet<>();

        ListIterator<Task> taskIterator = tasksInRegion.listIterator();

        while (taskIterator.hasNext()) {
            Task task = taskIterator.next();
            if (ids.contains(task.atmId)) {
                taskIterator.remove();
            } else {
                ids.add(task.atmId);
            }
        }
        return tasksInRegion.stream();
    }

    private List<Task> sortByPriorityWithinRegion(List<Task> tasks) {
        List<Task> result = new ArrayList<>();

        List<Task>[] byPriorityWithinRegion = new List[]{
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>()
        };

        for (Task task : tasks) {
            byPriorityWithinRegion[task.priority].add(task);
        }

        // Highest priority goes to the end of array so iterate backwards
        for (int i = byPriorityWithinRegion.length - 1; i >= 0; i--) {
            result.addAll(byPriorityWithinRegion[i]);
        }

        return result;
    }

    private Map<Integer, List<Task>> tasksByRegion(List<Task> tasks) {
        Map<Integer, List<Task>> tasksByRegion = new HashMap<>();

        for (Task task : tasks) {
            List<Task> regionList = tasksByRegion.computeIfAbsent(task.region,
                    t -> new ArrayList<>());
            regionList.add(task);
        }

        return tasksByRegion;
    }
}
