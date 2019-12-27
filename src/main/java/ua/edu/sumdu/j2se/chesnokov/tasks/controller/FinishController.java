package ua.edu.sumdu.j2se.chesnokov.tasks.controller;

import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.chesnokov.tasks.model.AbstractTaskList;


public class FinishController implements Controller {


    private static Logger logger = Logger.getLogger(FinishController.class);
    public FinishController() {
       MainController.controllers.add(this);
    }

    @Override
    public boolean canProcess(int action) {
        if (action == FINISH_ACTION) {
            return true;
        }
        return false;
    }

    @Override
    public int process(AbstractTaskList taskList) {
        logger.debug("Ретерн FINISH_ACTION");
        return FINISH_ACTION;
    }
}
