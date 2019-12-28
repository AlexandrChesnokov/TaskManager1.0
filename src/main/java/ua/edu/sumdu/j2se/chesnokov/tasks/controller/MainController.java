package ua.edu.sumdu.j2se.chesnokov.tasks.controller;

import org.apache.log4j.Logger;

import ua.edu.sumdu.j2se.chesnokov.tasks.model.AbstractTaskList;
import ua.edu.sumdu.j2se.chesnokov.tasks.model.TaskIO;
import ua.edu.sumdu.j2se.chesnokov.tasks.view.CheckIncomingView;
import ua.edu.sumdu.j2se.chesnokov.tasks.view.MainView;


import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for calling other controllers
 */
public class MainController implements Controller  {
    private static Logger logger = Logger.getLogger(MainController.class);

    private File file = new File(System.getProperty("user.dir").concat("/save.json"));
   private boolean wasSuccessful;

    {
        try {
            logger.info("Проверка наличия файла save");
            wasSuccessful = file.createNewFile();
            logger.info("Ответ - " + !wasSuccessful);
        } catch (IOException e) {
            logger.fatal("Ошибка создания файла");
        }
    }



    public MainController(MainView view) {
        this.view = view;
    }

    /**
     This is a list for all controllers
     */
    public static ArrayList<Controller> controllers = new ArrayList<>();
    /**
     This is a list for all observers
     */
    public static List<Observer> observers = new ArrayList<>();

    public void attach(Observer observer) {
        observers.add(observer);
    }



   private MainView view;






    @Override
    public boolean canProcess(int action) {
        return false;
    }
    /**
     * This method starts the process of searching for the desired controller
     */
    @Override
    public  int process(AbstractTaskList taskList)  {
        CheckIncoming checkIncoming = new CheckIncoming(taskList, new CheckIncomingView());
        int action = view.printMenu();
        if (action > 0 & action < 7) {
        checkIncoming.start();
        logger.info("Запустился поток checkIncoming");



            for (; ; ) {
                for (Controller controller : controllers) {
                    logger.debug("Поиск нужного контроллера по номеру  " + action + " " + controller.canProcess(action));
                    if (controller.canProcess(action)) {
                        logger.debug("Контроллер найден -" + controller);
                        action = controller.process(taskList);
                        logger.debug("Контроллер отработал и вернул " + action);
                    }
                }

                if (action == FINISH_ACTION) {
                    logger.debug("Производится интерапт checkIncoming");
                    checkIncoming.interrupt();
                    logger.debug("Сохранение данных в файл");
                    TaskIO.writeText(taskList, file);
                    logger.debug("Данные сохранены. Выход из программы");
                    break;
                }
            }
            return FINISH_ACTION;

        } else {
            System.out.println("Выберите сущевствующий вариант");
        process(taskList);
        }
return FINISH_ACTION;
    }












}
