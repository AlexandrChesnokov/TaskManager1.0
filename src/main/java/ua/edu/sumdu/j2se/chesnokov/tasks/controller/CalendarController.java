package ua.edu.sumdu.j2se.chesnokov.tasks.controller;

import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.chesnokov.tasks.model.AbstractTaskList;
import ua.edu.sumdu.j2se.chesnokov.tasks.model.Task;
import ua.edu.sumdu.j2se.chesnokov.tasks.model.Tasks;
import ua.edu.sumdu.j2se.chesnokov.tasks.view.CalendarView;
import ua.edu.sumdu.j2se.chesnokov.tasks.view.MainView;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;


public class CalendarController implements Controller {

    private static Logger logger = Logger.getLogger(CalendarController.class);

    private MainView mainView;
    private CalendarView calendarView;

    public CalendarController(MainView mainView, CalendarView calendarView) {
        this.mainView = mainView;
        this.calendarView = calendarView;
        MainController.controllers.add(this);
    }


    @Override
    public boolean canProcess(int action) {
        return action == CALENDAR_ACTION;
    }

    @Override
    public int process(AbstractTaskList taskList) {
        logger.debug("Запустился Calendar process");
        try {
        calendarView.getMessage("Календарь на неделю: ");
            logger.debug("Попытка запустить цикл вывода календаря");
        for (Map.Entry<LocalDateTime, Set<Task>> pair : Tasks.calendar(taskList, LocalDateTime.now(), LocalDateTime.now().plusDays(7)).entrySet()) {
            LocalDateTime key = pair.getKey();
            Set<Task> value = pair.getValue();
            calendarView.getMessage(key + " : " + value);
        }
            logger.info("Календарь задач выведен");
    } catch (NullPointerException e) {
            calendarView.getMessage("Нет запланированных задач на неделю.");
            String answer = calendarView.getAnswer("1. Вернуться в главное меню");
            logger.debug("Пользователь ввел " + answer);
            if (answer.equals("1"))
                return mainView.printMenu();
            else {
                logger.error("Введена неверная цифра");
                calendarView.getMessage("Ошибка");
                return mainView.printMenu();

            }
        }

        return mainView.printMenu();

    }
}
