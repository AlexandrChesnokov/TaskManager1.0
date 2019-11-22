package ua.edu.sumdu.j2se.chesnokov.tasks;

import java.util.Objects;

public class Task {

    private String title;
    private int time;
    private int start;
    private int end;
    private int interval;
    private boolean active;


    public Task(String title, int time) throws IllegalArgumentException {
        this.title = title;
        if (time < 0) {
            throw new IllegalArgumentException();
        } else {
            this.time = time;
        }
        this.interval = 0;
        this.active = false;
    }

    public Task(String title, int start, int end, int interval) throws IllegalArgumentException {
        this.active = false;
        this.title = title;
        if (start < 0) {
            throw new IllegalArgumentException();
        } else {
            this.start = start;
        }

        if (end < 0) {
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
        if (active == true) {
            return true;
        } else {
            return false;
        }
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getTime() {

        if (this.isRepeated()) {
            return start;
        } else {
            return time;
        }
    }

    public void setTime(int time) {

        if (this.isRepeated()) {
            this.interval = 0;
        }
        this.time = time;
    }

    public int getStartTime() {
        if (!this.isRepeated()) {
            return time;
        } else {
            return start;
        }
    }

    public int getEndTime() {
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

    public void setTime(int start, int end, int interval) {
        this.start = start;
        this.end = end;


        if (!this.isRepeated()) {
            this.interval = interval;
        }
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

    public int nextTimeAfter(int current) {

        if (this.isActive() & !this.isRepeated() & current < this.time) {

            return this.time;

        }

        if (this.isActive() & this.isRepeated()) {

            for (int begin = this.start; begin <= this.end; begin += this.interval) {
                if (current < begin) {
                    return begin;
                }
            }
        }
        return -1;
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


