package ua.edu.sumdu.j2se.chesnokov.tasks.model;

import java.io.Serializable;
import java.util.stream.Stream;

/**
 * This class is an abstract list
 */
public abstract class AbstractTaskList implements Iterable<Task>, Cloneable, Serializable {


    public abstract void add(Task task);

    public abstract boolean remove(Task task);

    public abstract int size();

    public abstract Task getTask(int index);

    public abstract AbstractTaskList createList();

    public abstract Stream<Task> getStream();
}



        /*

        for (int i = 0; i < this.size(); i++) {
            if (this.getTask(i).isActive() & !this.getTask(i).isRepeated() & this.getTask(i).getTime() > from & this.getTask(i).getTime() <= to) {
                abstractTaskList.add(this.getTask(i));



            } else if (this.getTask(i).isActive() & this.getTask(i).isRepeated() & this.getTask(i).getEndTime() > from) {

                for (int k = this.getTask(i).getStartTime(); k <= to; k += this.getTask(i).getRepeatInterval()) {
                    if (k > from) {
                        abstractTaskList.add(this.getTask(i));
                        break;
                    }
                }
            }
        }*/

