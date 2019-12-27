package ua.edu.sumdu.j2se.chesnokov.tasks.model;





import java.util.Arrays;


import java.util.NoSuchElementException;
import java.util.stream.Stream;


public class ArrayTaskList extends AbstractTaskList {

    private Task[] list = new Task[10];




    public Iterator iterator() {
      return new Iterator();
    }

   public class Iterator implements java.util.Iterator<Task> {

        private int index = 0;
        private Task currentTask;

        @Override
    public void remove() {
            if (currentTask == null) {
                throw new IllegalStateException();
            } else {
                ArrayTaskList.this.remove(currentTask);
                currentTask = null;
                index--;
            }
        }


    @Override
    public boolean hasNext() {
            return index < size();
    }

    @Override
    public Task next() {
            if (index < size()) {
                currentTask = list[index];
                index++;
                return currentTask;
            }
            throw new NoSuchElementException();
    }
}

    @Override
    public void add(Task task) {

        if (size() != list.length) {

         if (list[size()] == null ) {

             list[size()] = task;
         }

        } else  {

            Task[] copy = new Task[(int) ( size()*1.5)];

            System.arraycopy(list, 0, copy, 0, size());

            copy[size()] = task;
            list = copy;
        }
    }

    @Override
    public boolean remove(Task task) {

        int size = size();

        for (int i = 0; i < size; i++) {
            if (list[i].equals(task)) {
                list[i] = null;

                if (size - 1 - i >= 0) System.arraycopy(list, i + 1, list, i, size - 1 - i);
                list[size-1] = null;
                Task[] copy = new Task[list.length - 1];

                System.arraycopy(list, 0, copy, 0, size - 1);

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
        if (index < 0 || index > size() - 1) {
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
    public Stream<Task> getStream() {
    Stream.Builder<Task> builder = Stream.builder();
        for (Task task : this) {
            builder.add(task);
        }

        return builder.build();
    }



    public ArrayTaskList clone() throws CloneNotSupportedException {
        ArrayTaskList arrayTaskList = (ArrayTaskList) super.clone();

        arrayTaskList.list = list.clone();
        return arrayTaskList;
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
