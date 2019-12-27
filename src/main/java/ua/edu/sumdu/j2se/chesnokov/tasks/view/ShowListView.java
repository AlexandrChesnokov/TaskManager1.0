package ua.edu.sumdu.j2se.chesnokov.tasks.view;

import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.chesnokov.tasks.model.AbstractTaskList;
import ua.edu.sumdu.j2se.chesnokov.tasks.model.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ShowListView implements View {
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static Logger logger = Logger.getLogger(RemoveView.class);
    public void printList(AbstractTaskList taskList) {
         int count = 0;
        System.out.println("Текущий список задач: ");
        for (Task task : taskList) {
            count++;
            System.out.println(count + ". " +task);
        }
    }
    @Override
    public void getMessage(String message) {
        System.out.println(message);
    }

    public String getAnswer(String message) {
        System.out.println(message);
        try {
            return reader.readLine();
        } catch (IOException e) {
            logger.error("getAnswer IOException");
        }
        return null;
    }

    public String getAnswer() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            logger.error("getAnswer IOException");
        }
        return null;
    }
}
