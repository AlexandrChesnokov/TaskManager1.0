package ua.edu.sumdu.j2se.chesnokov.tasks.controller;

import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.chesnokov.tasks.model.AbstractTaskList;
import ua.edu.sumdu.j2se.chesnokov.tasks.model.TaskIO;
import ua.edu.sumdu.j2se.chesnokov.tasks.view.ShowListView;
import ua.edu.sumdu.j2se.chesnokov.tasks.view.RemoveView;
import ua.edu.sumdu.j2se.chesnokov.tasks.view.MainView;


import java.io.File;
import java.io.IOException;


public class RemoveController implements Controller, Observed{

    private File file = new File(System.getProperty("user.dir").concat("/save.json"));
    private boolean wasSuccessful;

    {
        try {
            logger.debug("Проверка наличия файла save");
            wasSuccessful = file.createNewFile();
            logger.debug("Ответ - " + !wasSuccessful);
        } catch (IOException e) {
            logger.error("Ошибка создания файла");
        }
    }
    private static Logger logger = Logger.getLogger(RemoveController.class);

    private ShowListView listview;
    private MainView view;
    private RemoveView removeView;


public RemoveController(ShowListView listview, MainView view, RemoveView removeView) {
    this.listview = listview;
    this.view = view;
    this.removeView = removeView;
    MainController.controllers.add(this);
}

    @Override
    public boolean canProcess(int action) {
        return action == REMOVE_ACTION;
    }

    @Override
    public int process(AbstractTaskList taskList) {

        removeView.getMessage("Выберите задачу");

        listview.printList(taskList);

        String answer = removeView.getAnswer();
        logger.debug("Считан номер задачи - " + answer);
        if (Integer.parseInt(answer) <= taskList.size()) {
            removeView.getMessage("Будет удалена задача номер " + answer + ". Вы уверены?");

            String secondAnswer = removeView.getAnswer("1. Да" + "\n" + "Вернуться в главное меню");
            logger.debug("Считан ответ - " + secondAnswer);
            if (secondAnswer.equals("1")) {
                taskList.remove(taskList.getTask(Integer.parseInt(answer)-1));
                logger.info("Задача [" + (taskList.getTask(Integer.parseInt(answer)-1).getTitle()) + "] удалена");
                notifyUser();
                TaskIO.writeText(taskList, file);
                return view.printMenu();
            } else if (secondAnswer.equals("2")) {
                return view.printMenu();
            } else {
                logger.error("Неверный ответ");
                removeView.getMessage("Ошибка");
                return view.printMenu();
            }
        } else {
           removeView.getMessage("Задачи не найдено");
        }
        return view.printMenu();
    }



    @Override
    public void notifyUser() {
        for (Observer observer : MainController.observers) {
            observer.notification("Задача удалена успешно ✔");
        }
    }
}
