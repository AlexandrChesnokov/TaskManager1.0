package ua.edu.sumdu.j2se.chesnokov.tasks;

import javax.xml.stream.StreamFilter;
import java.util.stream.Stream;

public abstract class AbstractTaskList {

    public abstract void add(Task task);

    public abstract boolean remove(Task task);

    public abstract int size();

    public abstract Task getTask(int index);

    public abstract AbstractTaskList createList();

    public AbstractTaskList incoming(int from, int to) {

        AbstractTaskList abstractTaskList = createList();

        //AbstractTaskList abstractTaskList = TaskListFactory.createTaskList(this instanceof ArrayTaskList ? ListTypes.types.ARRAY : ListTypes.types.LINKED);

       // abstractTaskList.filter(el -> el.isActive() & !el.isRepeated() & el.getTime() > from & el.getTime() <= to).forEach(abstractTaskList::add);

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
        }
        return abstractTaskList;
    }
}
