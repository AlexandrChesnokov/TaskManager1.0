package ua.edu.sumdu.j2se.chesnokov.tasks.controller;



public class Subscriber implements Observer {


    @Override
    public void notification(String message) {
        System.out.println(message);
    }
}
