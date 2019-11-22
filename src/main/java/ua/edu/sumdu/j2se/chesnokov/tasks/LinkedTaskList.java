package ua.edu.sumdu.j2se.chesnokov.tasks;


import java.util.Objects;

public class LinkedTaskList extends AbstractTaskList {


    private int size = 0;
    private Node<Task> firstNode;
    private Node<Task> lastNode;

    public LinkedTaskList() {
        lastNode = new Node<Task>(null, firstNode, null);
        firstNode = new Node<Task>(null, null, lastNode);
    }


    public void add(Task task) {
        Node<Task> node = lastNode;
        node.setCurrentElement(task);
        lastNode = new Node<Task>(null, node, null);
        node.setNextElement(lastNode);
        size++;
    }

    public boolean remove(Task task) {
        Node<Task> target = firstNode.getNextElement();
        for (int i = 0; i < size; i++) {

            if (task.equals((target.getCurrentElement())) & i == 0) {

                firstNode.setNextElement(target.getNextElement());
                target.getNextElement().setPrevElement(target.getPrevElement());
                target.setNextElement(null);
                target.setPrevElement(null);
                target.setCurrentElement(null);
                size--;
                return true;

            } else if (task.equals((target.getCurrentElement())) & i > 0) {

                target.getPrevElement().setNextElement(target.getNextElement());
                target.getNextElement().setPrevElement(target.getPrevElement());
                target.setNextElement(null);
                target.setPrevElement(null);
                target.setCurrentElement(null);
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
        return target.getCurrentElement();
    }


    private Node<Task> getNextElement(Node<Task> current) {
        return current.getNextElement();
    }

    private static class Node<Task> {
        private Task currentElement;
        private Node<Task> nextElement;
        private Node<Task> prevElement;


        private Node(Task currentElement, Node<Task> prevElement, Node<Task> nextElement) {
            this.currentElement = currentElement;
            this.nextElement = nextElement;
            this.prevElement = prevElement;


        }

        public Task getCurrentElement() {
            return currentElement;
        }

        public void setCurrentElement(Task currentElement) {
            this.currentElement = currentElement;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LinkedTaskList that = (LinkedTaskList) o;
        return size == that.size &&
                Objects.equals(firstNode, that.firstNode) &&
                Objects.equals(lastNode, that.lastNode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(size, firstNode, lastNode);
    }
}
