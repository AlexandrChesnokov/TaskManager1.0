package ua.edu.sumdu.j2se.chesnokov.tasks.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * This class is responsible for displaying the main menu
 */
public class MainView implements View {
BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    public int printMenu() {
        System.out.println("                         Task Manager v1.0");
        System.out.println("1. Добавить задачу");
        System.out.println("2. Удалить задачу");
        System.out.println("3. Изменить задачу");
        System.out.println("4. Просмотреть список задач");
        System.out.println("5. Календарь");
        System.out.println("6. Выход");

        int num = 0;
        try{
            num = Integer.parseInt(reader.readLine());
        } catch (IOException e) {
            System.out.println("Ошибка. Неверная команда");
        }
        return  num;
    }

    @Override
    public void getMessage(String message) {

    }
}
