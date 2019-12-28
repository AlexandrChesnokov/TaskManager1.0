package ua.edu.sumdu.j2se.chesnokov.tasks.model;



import java.time.LocalDateTime;
import java.util.*;

/**
 * This class is responsible for the calendar
 */
public class Tasks {

    /**
     * This method returns a list of tasks that start at the specified time interval
     */
    public static Iterable<Task> incoming(Iterable<Task> tasks, LocalDateTime start, LocalDateTime end) {

        AbstractTaskList iterableList = TaskListFactory.createTaskList(tasks instanceof ArrayTaskList ? ListTypes.types.ARRAY : ListTypes.types.LINKED);

        for (Task task : tasks) {
            if (task.nextTimeAfter(start) != null && task.nextTimeAfter(start).compareTo(end) <= 0) {
                iterableList.add(task);
            }
        }
        return iterableList;
    }

    /**
     * This method returns a calendar list
     */
    public static SortedMap<LocalDateTime, Set<Task>> calendar(Iterable<Task> tasks, LocalDateTime start, LocalDateTime end) {

        SortedMap<LocalDateTime, Set<Task>> map = new TreeMap<>();

        for (Task task : tasks) {

            if (task.isActive() & !task.isRepeated() & task.nextTimeAfter(start).compareTo(end) <= 0) {
                map.put(task.getTime(), new HashSet<>());
            }
            if (task.isActive() & task.isRepeated()) {

                for (LocalDateTime begin = task.getStartTime(); begin.compareTo(end) <= 0; begin = begin.plusSeconds(task.getRepeatInterval())) {
                    if (begin.compareTo(start) >= 0) {
                        map.put(begin, new HashSet<>());
                    }
                }
            }
        }

        for (Task task : tasks) {

            if (task.isActive() & !task.isRepeated() & task.nextTimeAfter(start).compareTo(end) <= 0) {
                for (Map.Entry<LocalDateTime, Set<Task>> pair : map.entrySet()) {
                    LocalDateTime key = pair.getKey();
                    Set<Task> value = pair.getValue();
                    if (task.getStartTime() == key) {
                        value.add(task);
                    }
                }
            }
            if (task.isActive() & task.isRepeated()) {
                for (Map.Entry<LocalDateTime, Set<Task>> pair : map.entrySet()) {
                    LocalDateTime key = pair.getKey();
                    Set<Task> value = pair.getValue();
                    for (LocalDateTime begin = task.getStartTime(); begin.compareTo(end) <= 0; begin = begin.plusSeconds(task.getRepeatInterval())) {
                        if (begin.compareTo(start) >= 0 & begin.equals(key)) {
                            value.add(task);
                        }
                    }
                }
            }
        }
        return map;
    }
}


