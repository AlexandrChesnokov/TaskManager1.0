package ua.edu.sumdu.j2se.chesnokov.tasks.controller;

import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.chesnokov.tasks.model.AbstractTaskList;

/**
 * This class is responsible for exiting the application
 */
public class FinishController implements Controller {


    private static Logger logger = Logger.getLogger(FinishController.class);
    public FinishController() {
       MainController.controllers.add(this);
    }
    /**
     * This method checks 'action' for a match with 'EDIT_ACTION'
     */
    @Override
    public boolean canProcess(int action) {
        if (action == FINISH_ACTION) {
            return true;
        }
        return false;
    }

    /**
     * This method starts the process of exiting the application
     */
    @Override
    public int process(AbstractTaskList taskList) {
        logger.debug("Ретерн FINISH_ACTION");
        return FINISH_ACTION;
    }
}
