package ua.edu.sumdu.j2se.chesnokov.tasks.controller;

import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.chesnokov.tasks.model.AbstractTaskList;
import ua.edu.sumdu.j2se.chesnokov.tasks.view.MainView;
import ua.edu.sumdu.j2se.chesnokov.tasks.view.ShowListView;


/**
 * This class is responsible for displaying the task list
 */
public class ShowListController implements Controller {


    private static Logger logger = Logger.getLogger(ShowListController.class);
    private ShowListView showListView;
    private MainView view;


    public ShowListController(ShowListView listView, MainView view) {
        this.showListView = listView;
        this.view = view;
        MainController.controllers.add(this);
    }
/**
 * This method starts the list display process
 */
    @Override
    public int process(AbstractTaskList taskList) {

        if (taskList.size() > 0) {

            showListView.printList(taskList);
            logger.debug("Выведен список задач");
            showListView.getMessage("Введите 'exit' что бы вернуться в главное меню");

                String checkExit = showListView.getAnswer();
                logger.debug("Считан номер задачи -" + checkExit);

                if (checkExit.equals("exit"))
                    return view.printMenu();

        } else {
            showListView.getMessage("Список задач пуст.");
            showListView.getMessage("Возвращение в главное меню ...");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                showListView.getMessage("Ошибка");
                return view.printMenu();
            }
            return view.printMenu();
        }

        return view.printMenu();
    }
    /**
     * This method checks 'action' for a match with 'CHECK_ACTION'
     */
    @Override
    public boolean canProcess(int action) {
        return action == CHECK_ACTION;
    }


}
