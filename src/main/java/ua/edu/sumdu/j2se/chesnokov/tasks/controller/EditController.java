package ua.edu.sumdu.j2se.chesnokov.tasks.controller;

import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.chesnokov.tasks.model.AbstractTaskList;
import ua.edu.sumdu.j2se.chesnokov.tasks.model.TaskIO;
import ua.edu.sumdu.j2se.chesnokov.tasks.view.EditView;
import ua.edu.sumdu.j2se.chesnokov.tasks.view.ShowListView;
import ua.edu.sumdu.j2se.chesnokov.tasks.view.MainView;


import java.io.File;
import java.io.IOException;

import java.time.LocalDateTime;

/**
 * This class is responsible for editing tasks
 * editing title, start time, end time, interval or activity.
 */
public class EditController implements Controller, Observed{

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

    private static Logger logger = Logger.getLogger(EditController.class);

    private ShowListView listview;
    private MainView view;
    private EditView editView;

    public EditController(ShowListView listView, MainView view, EditView editView){
        this.listview = listView;
        this.view = view;
        this.editView = editView;
        MainController.controllers.add(this);
    }


    /**
     * This method checks 'action' for a match with 'EDIT_ACTION'
     */
    @Override
    public boolean canProcess(int action) {
        return action == EDIT_ACTION;
    }

    /**
     * This method starts the tasks editing process
     */
    @Override
    public int process(AbstractTaskList taskList) {

        editView.getMessage("Выберите задачу");
        listview.printList(taskList);

        String answer = editView.getAnswer();
        logger.debug("Считан номер задачи - " + answer);
        if (Integer.parseInt(answer) <= taskList.size()) {
            editView.getCheckRepeatMessage(taskList.getTask(Integer.parseInt(answer)-1).isRepeated());
            String secondAnswer = editView.getAnswer();
            logger.debug("Считан выбор пользователя - " + secondAnswer);
            switch (secondAnswer) {
                case "1":
                    taskList.getTask(Integer.parseInt(answer)-1)
                            .setTitle(editView.getAnswer("Введите новое название"));
                    logger.info("Введено новое название");
                    notifyUser();
                    TaskIO.writeText(taskList, file);
                    break;
                case "2": if (taskList.getTask(Integer.parseInt(answer)-1).isRepeated()) {

                    LocalDateTime startTask = LocalDateTime.parse(editView.enterTime("Введите стартовое время"));
                    logger.debug("Введено стартовое время " + startTask);

                    LocalDateTime endTask = LocalDateTime.parse(editView
                            .enterTime("Введите время конца"));
                    logger.debug("Введено время конца " + endTask );

                    int interval = Integer.parseInt(editView.getAnswer("Введите интервал(сек)"));
                    logger.debug("Введен интервал " + interval);
                    taskList.getTask(Integer.parseInt(answer)-1).setTime(startTask, endTask, interval);
                    logger.info("Время задачи [" + taskList.getTask(Integer.parseInt(answer)-1).getTitle() + "] изменено");
                    notifyUser();
                    TaskIO.writeText(taskList, file);
                    break;
                } else {
                    LocalDateTime startTask = LocalDateTime.parse(editView
                            .enterTime("Введите время стартовое время"));

                    taskList.getTask(Integer.parseInt(answer)-1).setTime(startTask);
                    logger.info("Время задачи [" + taskList.getTask(Integer.parseInt(answer)-1).getTitle() + "] изменено");
                    notifyUser();
                    TaskIO.writeText(taskList, file);
                    break;
                }
                case "3":
                    String thirdAnswer = editView.getAnswer("1. Активировать" + "\n" + "2. Деактивировать");
                    logger.debug("Пользователь выбрал пункт " + thirdAnswer);
                    if (thirdAnswer.equals("1")) {
                        taskList.getTask(Integer.parseInt(answer) - 1).setActive(true);
                        logger.info("Активность задачи [" + taskList.getTask(Integer.parseInt(answer)-1).getTitle() + "] изменена");
                        notifyUser();
                        TaskIO.writeText(taskList, file);
                        break;
                    }
                    else if (thirdAnswer.equals("2")) {
                        taskList.getTask(Integer.parseInt(answer) - 1).setActive(false);
                        logger.info("Активность задачи [" + taskList.getTask(Integer.parseInt(answer)-1).getTitle() + "] изменена");
                        notifyUser();
                        TaskIO.writeText(taskList, file);
                        break;
                    }
                    else { System.out.println("Ошибка, попробуйте снова");
                    process(taskList);}
            }
        } else {

            System.out.println("Задачи не найдено");
        }
        return view.printMenu();
    }

    /**
     * This method is responsible for notifying the user
     */
    @Override
    public void notifyUser() {
        for (Observer observer : MainController.observers) {
            observer.notification("Изменения внесены ");
        }
    }


    }

