package ua.edu.sumdu.j2se.chesnokov.tasks.controller;


/**
 * This class is responsible for creating the user
 */
public class Subscriber implements Observer {


    @Override
    public void notification(String message) {
        System.out.println(message);
    }
}
