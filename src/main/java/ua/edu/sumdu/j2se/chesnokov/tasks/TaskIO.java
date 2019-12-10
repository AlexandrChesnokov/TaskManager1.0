package ua.edu.sumdu.j2se.chesnokov.tasks;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.time.*;
import java.time.temporal.TemporalAccessor;

public class TaskIO {

    public static void write(AbstractTaskList tasks, OutputStream out) {
        TemporalAccessor temporalAccessor = ZonedDateTime.now();
        ObjectOutputStream dataOut = null;
        try  {
            dataOut = new ObjectOutputStream(out);
            if (tasks.size() > 0) {
                dataOut.writeInt(tasks.size());
                for (int i = 0; i < tasks.size(); i++) {
                    if (tasks.getTask(i).getTitle() != null) {
                        dataOut.writeInt(tasks.getTask(i).getTitle().length());       //check for null title
                        dataOut.writeChars(tasks.getTask(i).getTitle());
                    } else {
                        dataOut.writeInt(0);
                    }
                    dataOut.writeInt(tasks.getTask(i).isActive() ? 1 : 0);

                    dataOut.writeInt(tasks.getTask(i).getRepeatInterval());

                    dataOut.writeLong(tasks.getTask(i).getStartTime().toInstant(ZoneOffset.from(temporalAccessor)).toEpochMilli());

                    if (tasks.getTask(i).isRepeated()) {
                        dataOut.writeLong(tasks.getTask(i).getEndTime().toInstant(ZoneOffset.from(temporalAccessor)).toEpochMilli());
                    }
                }
            } else {
                dataOut.writeInt(tasks.size());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                dataOut.flush();
                dataOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    public static void read(AbstractTaskList tasks, InputStream in)  {
        ObjectInputStream dataIn = null;
        TemporalAccessor temporalAccessor = ZonedDateTime.now();
      try {
          dataIn = new ObjectInputStream(in);
        int num = dataIn.readInt();

        if (num > 0) {
            for (int i = 0; i < num; i++) {
                StringBuilder string = new StringBuilder();
                int num2 = dataIn.readInt();
                String title = null;
                if (num2 > 0) {                              //check for null title
                    for (int q = 0; q < num2; q++) {
                        string.append(dataIn.readChar());
                    }
                    title = new String(string);
                }
                int actives = dataIn.readInt();
                boolean active = false;
                if (actives == 1)
                    active = true;

                int interval = dataIn.readInt();

                long startTime = dataIn.readLong();

                LocalDateTime start = Instant.ofEpochMilli(startTime).atZone(ZoneId.systemDefault()).toLocalDateTime();
                if (interval != 0) {
                    long endTime = dataIn.readLong();
                    LocalDateTime end = Instant.ofEpochMilli(endTime).atZone(ZoneId.systemDefault()).toLocalDateTime();
                    tasks.add(new Task(title, start, end, interval));
                } else {
                    tasks.add(new Task(title, start));
                }
                tasks.getTask(i).setActive(active);
            }
        }

      } catch (IOException e) {
          e.printStackTrace();
      }
      finally {
          try {

              dataIn.close();
          } catch (IOException e) {
              e.printStackTrace();
          }
      }
    }
    public static void writeBinary(AbstractTaskList tasks, File file)  {
        ObjectOutputStream objOut = null;
        try {
           objOut = new ObjectOutputStream(new FileOutputStream(file));
            write(tasks, objOut);
        }
        catch (FileNotFoundException e) {
            System.out.println("Файл не найден - " + file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                objOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void readBinary(AbstractTaskList tasks, File file)  {
        ObjectInputStream objIn = null;
        try {
            objIn = new ObjectInputStream(new FileInputStream(file));
            read(tasks, objIn);
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден - " + file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                objIn.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void write(AbstractTaskList tasks, Writer out) {

        Gson gson = new Gson();
             try {
                 String result = gson.toJson(tasks);
                    out.write(result);
                    out.flush();


            } catch (IOException e) {
                e.printStackTrace();
            }


    }

    public static void read(AbstractTaskList tasks, Reader in) {
        Gson gson = new Gson();
       if (tasks instanceof ArrayTaskList) {
           ArrayTaskList list = new ArrayTaskList();
           list = gson.fromJson(in, ArrayTaskList.class);
            for (Task task : list) {
                tasks.add(task);
            }
       } else if (tasks instanceof LinkedTaskList){
           LinkedTaskList list = new LinkedTaskList();
           list = gson.fromJson(in, LinkedTaskList.class);
           for (Task task : list) {
               tasks.add(task);
           }

       }

    }

    public static void writeText(AbstractTaskList tasks, File file) {
        Gson gson = new Gson();
        FileWriter writer = null;
        try {
            writer = new FileWriter(file);
            write(tasks, writer);
        } catch (IOException e) {
            System.out.println("Файл не найден - " + file);
        }
    }

    public static 	void readText(AbstractTaskList tasks, File file) {
        Gson gson = new Gson();

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            read(tasks, reader);

        } catch (IOException e) {
            System.out.println("1");
        }
    }

}
