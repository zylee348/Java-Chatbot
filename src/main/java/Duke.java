import java.io.IOException;
<<<<<<< HEAD
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
=======
>>>>>>> 8bf586af9f2e50859f248573fcfc59edffd3c894
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Duke {

<<<<<<< HEAD
    private static ArrayList<Task> taskList;
    private static String filePath = "data/duke.txt";
    private static final String LINE = "____________________________________________________________";

    private static final String LOGO = "Hello from\n" +
            " ____        _        \n"
            + "|  _ \\ _   _| | _____ \n"
            + "| | | | | | | |/ / _ \\\n"
            + "| |_| | |_| |   <  __/\n"
            + "|____/ \\__,_|_|\\_\\___|\n";
    private static final String MESSAGE_LOGO = "Hello from\n" + LOGO;

    private static final String GREETING = "Hello! I'm Duke\n" +
            "What can I do for you?";

    private static final String GOODBYE_MESSAGE = "Bye, Hope to see you again soon!";

    //method to print in list format
    public static void printList(ArrayList<Task> taskArrayList) {
        int len = taskArrayList.size();
        if (len < 1) {
            throw new DukeException("List is empty, you have no tasks!");
        }
        int counter = 0;
        int numbering = 1;
        System.out.println(LINE);
        while (counter < len) {
            Task temp = taskArrayList.get(counter);
            System.out.println(numbering + "." + temp);
            counter++; numbering++;
        }
        System.out.println(LINE);
    }

    //method to mark as done
    public static void markAsDone(ArrayList<Task> taskArrayList, String removeTaskNumberString) {
        String numberToRemove = removeTaskNumberString.replaceAll("[^0-9]", "");
        int numberToRemoveInt = Integer.parseInt(numberToRemove) - 1;
        Task tsk = taskArrayList.get(numberToRemoveInt);
        tsk.markAsDone();
        System.out.println(LINE);
        System.out.println("Nice! I've marked this task as done:\n" +
                "  " + tsk);
        System.out.println(LINE);
    }

    public static void markAsUndone(ArrayList<Task> taskArrayList, String addTaskNumberString) {
        String numberToAddAgain = addTaskNumberString.replaceAll("[^0-9]", "");
        int numberToRemoveInt = Integer.parseInt(numberToAddAgain) - 1;
        Task tsk = taskArrayList.get(numberToRemoveInt);
        tsk.markAsUndone();
        System.out.println(LINE);
        System.out.println("OK, I've marked this task as not done yet:\n" +
                "  " + tsk);
        System.out.println(LINE);
    }

    public static void printAddition(Task task) {
        int tasksLeft = taskList.size();
        System.out.println(LINE);
        System.out.println("Got it. I've added this task:\n " + " " + task.toString() +
                "\nNow you have " + tasksLeft + " tasks in the list.");
        System.out.println(LINE);

    }

    public static void addTaskToArray(String s, Task.TYPE type) {
        Task t;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        try {
            switch (type) {
            case DEADLINE:
                if (s.length() < 1) {
                    throw new DukeException("☹ OOPS!!! The description of a deadline cannot be empty.");
                }
                String[] splitStringDL = s.split(" /by ");
                if (splitStringDL.length < 2) {
                    throw new DukeException("☹ Deadline requires a BY time typed correctly.");
                }
                String taskStringDL = splitStringDL[0];
                String by = splitStringDL[1];
                //for date only
                String[] dateDeadlineOnly = by.split(" ");
                if (dateDeadlineOnly.length == 1) {
                    throw new DukeException("Time required!");
                } else {
                    LocalDateTime dateBy = LocalDateTime.parse(by, formatter);
                    t = new Deadline(taskStringDL, dateBy);
                    break;
                }

            case TODO:
                if (s.length() < 1) {
                    throw new DukeException("☹ OOPS!!! The description of a todo cannot be empty.");
                }
                t = new Todo(s);
                break;

            case EVENT:
                if (s.length() < 1) {
                    throw new DukeException("☹ OOPS!!! The description of an event cannot be empty.");
                }
                String[] splitStringTD = s.split(" /at ");

                if (splitStringTD.length < 2) {
                    System.out.println(splitStringTD.length);
                    throw new DukeException("☹ Event requires an AT time typed correctly.");
                }
                String taskStringTD = splitStringTD[0];
                String at = splitStringTD[1];
                String[] dateEventOnly = at.split(" ");
                if (dateEventOnly.length == 1) {
                    throw new DukeException("Time required!");
                } else {
                    LocalDateTime dateAt = LocalDateTime.parse(at, formatter);
                    t = new Event(taskStringTD, dateAt);
                    break;
                }

            default:
                throw new DukeException("☹ OOPS!!! I'm sorry, but I don't know what that means :-(");
            }
            taskList.add(t);
            printAddition(t);
        } catch (DateTimeParseException e) {
            throw new DukeException("Date/Time not in correct format!");
        }
    }

    public static void deleteTaskfromArray(ArrayList<Task> taskArrayList, String taskNumber) {
        String numberToRemove = taskNumber.replaceAll("[^0-9]", "");
        int numberToRemoveInt = Integer.parseInt(numberToRemove) - 1;
        Task t = taskArrayList.get(numberToRemoveInt);
        taskArrayList.remove(numberToRemoveInt);
        System.out.println(LINE);
        System.out.println("Noted. I've removed this task:\n " + " " + t.toString() +
                "\nNow you have " + taskArrayList.size() + " tasks in the list.");
        System.out.println(LINE);
    }

    public static void end() {
        System.out.println(LINE);
        System.out.println(GOODBYE_MESSAGE);
        System.out.println(LINE);
    }

    public static ArrayList<Task> loadTask() throws IOException {
        try {
            File taskFile = new File(filePath);
            if (!taskFile.exists()) {
                return taskList;
            } else {
                Scanner sc = new Scanner(taskFile);
                taskList = new ArrayList<>();
                while (sc.hasNext()) {
                    String taskString = sc.nextLine();
                    String[] taskStringInArray = taskString.split(" \\| ");
                    String taskType = taskStringInArray[0];
                    if (taskStringInArray.length > 1) {
                        boolean isDone = taskStringInArray[1].equals("1");
                        String taskDesription = taskStringInArray[2];
                        Task t;

                        switch (taskType) {
                        case "T":
                            t = new Todo(taskDesription);
                            break;

                        case "D":
                            if (taskStringInArray.length < 3) {
                                throw new DukeException("Deadline required.");
                            }
                            String deadline = taskStringInArray[3];
                            LocalDateTime deadlineDate = LocalDateTime.parse(deadline);
                            t = new Deadline(taskDesription, deadlineDate);
                            break;

                        case "E":
                            if (taskStringInArray.length < 3) {
                                throw new DukeException("Event time required.");
                            }
                            String eventTime = taskStringInArray[3];
                            LocalDateTime eventTimeInDate = LocalDateTime.parse(eventTime);
                            t = new Event(taskDesription, eventTimeInDate);
                            break;
                        default:
                            throw new DukeException("File error");
                        }


                        if (isDone) {
                            t.markAsDone();
                        }

                        taskList.add(t);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("no file");
        } catch (DateTimeParseException e) {
            System.out.println("Check date format.");
        }
        return taskList;
=======
    public void run() {
        UI.getGREETING();
        UI.getLINE();
        Parser.parseInput();
>>>>>>> 8bf586af9f2e50859f248573fcfc59edffd3c894
    }


    public static void main(String[] args) {
        new Duke().run();
    }
}
