package ua.edu.sumdu.j2se.chesnokov.tasks.controller;

import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.chesnokov.tasks.model.AbstractTaskList;
import ua.edu.sumdu.j2se.chesnokov.tasks.model.Task;
import ua.edu.sumdu.j2se.chesnokov.tasks.model.Tasks;
import ua.edu.sumdu.j2se.chesnokov.tasks.view.CheckIncomingView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CheckIncoming extends Thread {
    private static Logger logger = Logger.getLogger(CheckIncoming.class);
    AbstractTaskList taskList;
    CheckIncomingView checkIncomingView;

    public CheckIncoming(AbstractTaskList taskList, CheckIncomingView checkIncomingView) {
        this.taskList = taskList;
        this.checkIncomingView = checkIncomingView;
    }

    @Override
    public void run() {
        try {
            logger.info("CHECK ALERT - запускается поток алерта");
            while (true) {
                logger.debug("CHECK ALERT - запустился цикл алерта");
                if (!isInterrupted()) {

                    for (Task task : Tasks.incoming(taskList, LocalDateTime.now().minusSeconds(1), LocalDateTime.now().plusMinutes(3))) {
                        logger.debug("CHECK ALERT - проверка на совпадение времени. Время выполнения задачи " + task.getTitle() +
                                " " + task.getStartTime().withNano(0) + ". Текущее время: " + LocalDateTime.now().withNano(0));
                        if (task.getStartTime().withNano(0).isEqual(LocalDateTime.now().withNano(0))) {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy - HH:mm");
                            logger.debug("CHECK ALERT - Задача обнаружена, запускается АЛЕРТ");
                            checkIncomingView.alert(LocalDateTime.now().format(formatter) + ". " + "\n" + "Время выполнения задачи " + task.getTitle());
                            logger.info("CHECK ALERT - Алерт запущен");
                        }
                    }
                } else {
                    logger.error("CHECK ALERT - поток прерван пользователем, идет выброс исключения");
                    throw new InterruptedException();
                }

               Thread.sleep(1000);

            }
        } catch (InterruptedException e) {
            logger.debug("CHECK ALERT - выброшено исключение");
        } catch (NullPointerException e) {
            logger.debug("CHECK ALERT - Обработан NPE, запуск метода run()");
            run();
            logger.debug("CHECK ALERT - run() запущен");
        }


    }
}
