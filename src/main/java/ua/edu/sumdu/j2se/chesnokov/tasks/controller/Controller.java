package ua.edu.sumdu.j2se.chesnokov.tasks.controller;

import ua.edu.sumdu.j2se.chesnokov.tasks.model.AbstractTaskList;

public interface Controller {

    final int CREATE_ACTION = 1;
    final int REMOVE_ACTION = 2;
    final int EDIT_ACTION = 3;
    final int CHECK_ACTION = 4;
    final int CALENDAR_ACTION = 5;
    final int FINISH_ACTION = 6;



    public boolean canProcess(int action);

    public int process(AbstractTaskList taskList);
}
