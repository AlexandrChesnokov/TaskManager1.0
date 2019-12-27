package ua.edu.sumdu.j2se.chesnokov.tasks;


import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.chesnokov.tasks.controller.*;
import ua.edu.sumdu.j2se.chesnokov.tasks.model.ArrayTaskList;

import ua.edu.sumdu.j2se.chesnokov.tasks.model.TaskIO;
import ua.edu.sumdu.j2se.chesnokov.tasks.view.*;

import java.io.File;
import java.io.IOException;


public class Main {
    private static Logger logger = Logger.getLogger(MainController.class);
    public static void main(String[] args) throws IOException {
        ArrayTaskList arrayTaskList = new ArrayTaskList();

        File file = new File(System.getProperty("user.dir").concat("/save.json"));

        if (file.exists())
            logger.info("Загрузка данных с файла");
            TaskIO.readText(arrayTaskList, file);

        boolean wasSuccessful = file.createNewFile();






        Observer user = new Subscriber();

        MainController mainController = new MainController(new MainView());
        ShowListController showListController = new ShowListController(new ShowListView(), new MainView());
        FinishController finishController = new FinishController();
        CreateController createController = new CreateController(new MainView(), new CreateView());
        RemoveController removeController = new RemoveController(new ShowListView(), new MainView(), new RemoveView());
        EditController editController = new EditController(new ShowListView(), new MainView(), new EditView());
        CalendarController calendarController = new CalendarController(new MainView(), new CalendarView());

        mainController.attach(user);










        mainController.process(arrayTaskList);


    }
}


