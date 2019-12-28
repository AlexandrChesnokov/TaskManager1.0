package ua.edu.sumdu.j2se.chesnokov.tasks.view;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * This class is responsible for displaying the task creation menu
 */
public class CreateView implements View {
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static Logger logger = Logger.getLogger(CreateView.class);

    public boolean repeated() throws IOException {
        System.out.println("1. Создать задачу с повторением");
        System.out.println("2. Создать задачу без повторения");
        int a = Integer.parseInt(reader.readLine());
        if (a == 1) {
            return true;
        } else if (a == 2) {
            return false;
        } else {
            System.out.println("Выберите сущевствующий вариант!");
            repeated();
        }
        return false;
    }


    public String enterTime(String message) {
        System.out.println(message);
        try {
            StringBuilder stringBuilder = new StringBuilder();
            System.out.println("Введите год");
             stringBuilder.append(reader.readLine()+"-");
            System.out.println("Введите месяц");
            stringBuilder.append(reader.readLine()+"-");
            System.out.println("Введите число");
            stringBuilder.append(reader.readLine()+"T");
            System.out.println("Введите время в формате '00:00:00'");
            stringBuilder.append(reader.readLine());
            String string = new String(stringBuilder);
            return string;

        } catch (IOException e) {
            logger.error("getAnswer IOException");
        }
        return null;
    }

    public String enterData(String data) {
        System.out.println(data);
        try {
            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



    @Override
    public void getMessage(String message) {
        System.out.println(message);
    }
}
