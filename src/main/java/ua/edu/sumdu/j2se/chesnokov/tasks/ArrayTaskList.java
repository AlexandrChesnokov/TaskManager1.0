package ua.edu.sumdu.j2se.chesnokov.tasks;


import java.util.Arrays;

public class ArrayTaskList extends AbstractTaskList {

    private Task[] list = new Task[10];

    @Override
    public void add(Task task) {

        if (list == null || list.length == 0) {
            list = new Task[1];
            list[0] = task;
        } else if (list[size()] == null) {

            list[size()] = task;

        } else {

            Task[] copy = new Task[list.length+10];

            System.arraycopy(list, 0, copy, 0, list.length);

            copy[size()] = task;
            list = copy;
        }
    }

    @Override
    public boolean remove(Task task) {

        for (int i = 0; i < list.length; i++) {
            if (list[i].equals(task)) {
                list[i] = null;

                if (list.length - 1 - i >= 0) System.arraycopy(list, i + 1, list, i, list.length - 1 - i);

                Task[] copy = new Task[list.length - 1];

                if (list.length - 1 >= 0) System.arraycopy(list, 0, copy, 0, list.length - 1);
                list = copy;
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {

        int count = 0;
        for (Task task : list) {
            if (task != null) {
                count++;
            }
        }

        return count;
    }

    @Override
    public Task getTask(int index) {
        if (index < 0 || index > list.length - 1) {
            throw new IndexOutOfBoundsException();
        } else {
            return list[index];
        }
    }

    @Override
    public AbstractTaskList createList() {
        return new ArrayTaskList();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArrayTaskList that = (ArrayTaskList) o;
        return Arrays.equals(list, that.list);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(list);
    }
}
