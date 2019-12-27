package ua.edu.sumdu.j2se.chesnokov.tasks.view;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RemoveView implements View {

    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static Logger logger = Logger.getLogger(RemoveView.class);

    @Override
    public void getMessage(String message) {
        System.out.println(message);
    }

    public String getAnswer(String message) {
        System.out.println(message);
        try {
            return reader.readLine();
        } catch (IOException e) {
            logger.error("getAnswermessage IOException");
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
