package ua.edu.sumdu.j2se.chesnokov.tasks;

public class Main {

	public static void main(String[] args) {

LinkedTaskList linkedTaskList = new LinkedTaskList();
ArrayTaskList arrayTaskList = new ArrayTaskList();
Task task1 = new Task("1", 12,44,2);
		Task task2 = new Task("2", 55,55,10);
		Task task3 = new Task("3", 1,12,2);
		Task task4 = new Task("4", 12,44,2);
		Task task5 = new Task("5", 57);
		Task task6 = new Task("6", 12,44,2);
		Task task7 = new Task("7", 12,44,2);
		Task task8 = new Task("8", 51,58,2);
		Task task9 = new Task("9", 51);
		Task task10 = new Task("10", 55);
		Task task11 = new Task("11", 51);
		Task task12 = new Task("12", 51);

		task10.setActive(true);
		task2.setActive(true);
		task3.setActive(true);
		task8.setActive(true);
		task5.setActive(true);




		arrayTaskList.add(task5);
		arrayTaskList.add(task10);
		arrayTaskList.add(task8);


		System.out.println(arrayTaskList.incoming(50,60).size());





       for (int i = 0; i < arrayTaskList.incoming(50,60).size(); i++) {
		   System.out.println(arrayTaskList.incoming(50,60).getTask(i));
	   }










	}
}
