package ua.edu.sumdu.j2se.chesnokov.tasks.controller;

import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.chesnokov.tasks.model.AbstractTaskList;
import ua.edu.sumdu.j2se.chesnokov.tasks.model.Task;
import ua.edu.sumdu.j2se.chesnokov.tasks.model.TaskIO;
import ua.edu.sumdu.j2se.chesnokov.tasks.view.CreateView;
import ua.edu.sumdu.j2se.chesnokov.tasks.view.MainView;


import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;


public class CreateController implements Controller, Observed{

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

    private static Logger logger = Logger.getLogger(CreateController.class);

    private MainView view;
    private CreateView createView;



public CreateController(MainView view, CreateView createView) {
    this.view = view;
    this.createView = createView;
    MainController.controllers.add(this);
}


@Override
    public boolean canProcess(int action) {
    return action == CREATE_ACTION;
}


    @Override
    public int process(AbstractTaskList taskList) {

        String title = null;

        int interval = 0;

        try {
       boolean repeat =  createView.repeated();      //Показывает вью и считывает.
            logger.info("Показ вью, проверка на повторяемость");
            if (repeat) {
                title = createView.enterData("Введите название задачи");
                logger.debug("Название введено верно");
                LocalDateTime startTask = LocalDateTime.parse(createView.enterTime("Введите время старта"));
                logger.debug("Время старта введно верно");
                LocalDateTime endTask = LocalDateTime.parse(createView.enterTime("Введите время конца"));
                logger.debug("Время конца введено верно");
                interval = Integer.parseInt(createView.enterData("Введите интервал"));
                logger.debug("Интервал введен верно, идет создание задачи");
                taskList.add(new Task(title, startTask, endTask, interval));
                logger.info("Задача создана");
                notifyUser();
                TaskIO.writeText(taskList, file);
                String answer = createView.enterData("1. Вернуться в главное меню" + "\n" +"2. Выход");
                logger.debug("Ожидается: 1 или 2. Введено - " + answer);
                if (answer.equals("1")) {
                    return view.printMenu();

                }
                else if (answer.equals("2")) {
                    return FINISH_ACTION;
                }
            } else {
                title = createView.enterData("Введите название задачи");
                logger.debug("Название введено верно");
                LocalDateTime startTask = LocalDateTime.parse(createView.enterTime("Введите время старта"));
                logger.debug("Время старта введно верно");
                taskList.add(new Task(title, startTask));
                logger.info("Задача создана");
                notifyUser();
                TaskIO.writeText(taskList, file);
                String answer = createView.enterData("1. Вернуться в главное меню");
                logger.debug("Ожидается: 1. Введено - " + answer);
                if (answer.equals("1"))
                   return view.printMenu();

            }

            createView.getMessage("Выберите сущевствующий вариант!");
            process(taskList);

        } catch (IOException e) {
            System.out.println("Ошибка!");
            return view.printMenu();
        } catch (DateTimeParseException e) {
            System.out.println("Введена неверная дата, попробуйте снова!");
            process(taskList);
        }

        return view.printMenu();

}



    @Override
    public void notifyUser() {
     for (Observer observer : MainController.observers) {
         observer.notification("В вашем списке появилась новая задача ");
     }
    }
}
