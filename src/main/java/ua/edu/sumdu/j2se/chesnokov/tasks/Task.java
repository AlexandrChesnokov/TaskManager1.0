package ua.edu.sumdu.j2se.chesnokov.tasks;

import java.time.LocalDateTime;
import java.util.Objects;

public class Task implements Cloneable {

    private String title;
    private LocalDateTime time;
    private LocalDateTime start;
    private LocalDateTime end;
    private int interval;
    private boolean active;


    public Task(String title, LocalDateTime time) throws IllegalArgumentException {
        this.title = title;
        if (time == null) {
            throw new IllegalArgumentException();
        } else {
            this.time = time;
        }
        this.interval = 0;
        this.active = false;
    }

    public Task(String title, LocalDateTime start, LocalDateTime end, int interval) throws IllegalArgumentException {
        this.active = false;
        this.title = title;
        if (start == null) {
            throw new IllegalArgumentException();
        } else {
            this.start = start;
        }

        if (end == null) {
            throw new IllegalArgumentException();
        } else {
            this.end = end;
        }

        if (interval <= 0) {
            throw new IllegalArgumentException();
        } else {
            this.interval = interval;
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDateTime getTime() {

        if (this.isRepeated()) {
            return start;
        } else {
            return time;
        }
    }

    public void setTime(LocalDateTime time) {

        if (this.isRepeated()) {
            this.interval = 0;
        }
        this.time = time;
    }

    public LocalDateTime getStartTime() {
        if (!this.isRepeated()) {
            return time;
        } else {
            return start;
        }
    }

    public LocalDateTime getEndTime() {
        if (!this.isRepeated()) {
            return time;
        } else {
            return end;
        }
    }

    public int getRepeatInterval() {
        if (!this.isRepeated()) {
            return 0;
        } else {
            return interval;
        }
    }

    public void setTime(LocalDateTime start, LocalDateTime end, int interval) {
        this.start = start;
        this.end = end;



            this.interval = interval;

    }

    public boolean isRepeated() {
        if (this.interval <= 0) {
            return false;
        } else {
            return true;
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return time == task.time &&
                start == task.start &&
                end == task.end &&
                interval == task.interval &&
                active == task.active &&
                Objects.equals(title, task.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, time, start, end, interval, active);
    }

    public LocalDateTime nextTimeAfter(LocalDateTime current) {

        if (this.isActive() & !this.isRepeated() & current.isBefore(this.getTime())) {

            return this.getTime();
        }

        if (this.isActive() & this.isRepeated()) {

            for (LocalDateTime begin = this.getStartTime(); begin.compareTo(this.getEndTime()) <= 0 ; begin = begin.plusSeconds(this.getRepeatInterval())) {
                if (current.isBefore(begin)) {
                    return begin;
                }
            }
        }
        return null;
    }


    @Override
    public Task clone() throws CloneNotSupportedException {
        return (Task) super.clone();
    }

    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", time=" + time +
                ", start=" + start +
                ", end=" + end +
                ", interval=" + interval +
                ", active=" + active +
                '}';
    }
}


