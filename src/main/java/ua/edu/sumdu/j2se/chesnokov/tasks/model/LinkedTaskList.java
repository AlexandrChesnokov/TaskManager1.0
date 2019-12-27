package ua.edu.sumdu.j2se.chesnokov.tasks.model;


import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Stream;

public class LinkedTaskList extends AbstractTaskList {

    @Override
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
                LinkedTaskList.this.remove(currentTask);
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
                currentTask = getTask(index);
                index++;
                return currentTask;
            }
            throw new NoSuchElementException();
        }
    }

    private int size = 0;
    private Node<Task> firstNode;
    private Node<Task> lastNode;

    public LinkedTaskList() {
        lastNode = new Node<Task>(null, firstNode, null);
        firstNode = new Node<Task>(null, null, lastNode);
    }


    public void add(Task task) {
        Node<Task> node = lastNode;
        node.setItem(task);
        lastNode = new Node<Task>(null, node, null);
        node.setNextElement(lastNode);
        size++;
    }

    public boolean remove(Task task) {
        Node<Task> target = firstNode.getNextElement();
        for (int i = 0; i < size; i++) {

            if (task.equals((target.getItem())) & i == 0) {

                firstNode.setNextElement(target.getNextElement());
                target.getNextElement().setPrevElement(target.getPrevElement());
                target.setNextElement(null);
                target.setPrevElement(null);
                target.setItem(null);
                size--;
                return true;

            } else if (task.equals((target.getItem())) & i > 0) {

                target.getPrevElement().setNextElement(target.getNextElement());
                target.getNextElement().setPrevElement(target.getPrevElement());
                target.setNextElement(null);
                target.setPrevElement(null);
                target.setItem(null);
                size--;
                return true;

            }
            target = getNextElement(target);
        }

        return false;
    }

    public int size() {
        return size;
    }

    public Task getTask(int index) {
        Node<Task> target = firstNode.getNextElement();
        for (int i = 0; i < index; i++) {
            target = getNextElement(target);
        }
        return target.getItem();
    }


    private Node<Task> getNextElement(Node<Task> current) {
        return current.getNextElement();
    }

    private static class Node<Task> {
        private Task item;
        private Node<Task> nextElement;
        private Node<Task> prevElement;



        private Node(Task item, Node<Task> prevElement, Node<Task> nextElement) {
            this.item = item;
            this.nextElement = nextElement;
            this.prevElement = prevElement;


        }

        public Task getItem() {
            return item;
        }

        public void setItem(Task item) {
            this.item = item;
        }

        public Node<Task> getNextElement() {
            return nextElement;
        }

        public void setNextElement(Node<Task> nextElement) {
            this.nextElement = nextElement;
        }

        public Node<Task> getPrevElement() {
            return prevElement;
        }

        public void setPrevElement(Node<Task> prevElement) {
            this.prevElement = prevElement;
        }
    }

    @Override
    public AbstractTaskList createList() {
        return new LinkedTaskList();
    }

    @Override
    public Stream<Task> getStream() {
        Stream.Builder<Task> builder = Stream.builder();
        for (Task task : this) {
            builder.add(task);
        }

        return builder.build();
    }

    public LinkedTaskList clone() throws CloneNotSupportedException {
        LinkedTaskList cloneList = (LinkedTaskList) super.clone();


        Iterator iterator = this.iterator();

        cloneList.size = 0;


        while (iterator.hasNext()) {
            cloneList.add(iterator.next().clone());
        }
        return cloneList;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LinkedTaskList that = (LinkedTaskList) o;
        if (size == 0 & that.size == 0) return true;
        if (size == that.size) {
            int counter = 0;
            for (int i = 0; i < size; i++) {
                if (getTask(i).equals(that.getTask(i)))
                    counter++;
            }
            return counter == size;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        Iterator iterator = this.iterator();
        while (iterator.hasNext()) {
          hash +=  Objects.hash(iterator.next());
        }
        hash+= size;
        return hash;
    }
}
