package ua.edu.sumdu.j2se.chesnokov.tasks.view;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * This class is responsible for displaying the task editing menu
 */
public class EditView implements View {
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static Logger logger = Logger.getLogger(EditView.class);

    public void getCheckRepeatMessage(boolean repeat) {
        if (repeat) {
            System.out.println("1. Изменить название");
            System.out.println("2. Изменить время старта, конца и интервал");
            System.out.println("3. Изменить активность");
        } else  {
            System.out.println("1. Изменить название");
            System.out.println("2. Изменить время старта");
            System.out.println("3. Изменить активность");
        }
    }

    public String enterTime(String message) {
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
            e.printStackTrace();
        }
        return null;
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
          e.printStackTrace();
      }
return null;

  }

    public String getAnswer() {

        try {
            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }



}
