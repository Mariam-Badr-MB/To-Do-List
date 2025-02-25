import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

// Enum to define task priorities
enum Priority {
    LOW, MEDIUM, HIGH
}

// Class representing a task
class Task {
    private String descr;
    private boolean completed;
    private LocalDate date;
    private Priority priority;
    private String cate;
    private String notes;

    public Task(String descr, LocalDate date, Priority priority, String cate) {
        this.descr = descr;
        this.date = date;
        this.priority = priority;
        this.cate = cate;
        this.notes = "";
        this.completed = false;
    }

    public void setDescr(String descr) { 
        this.descr = descr;
     }
    public String getDescr() { 
        return descr;
     }
    public void setCompleted(boolean completed) {
         this.completed = completed; 
        }
    public boolean isCompleted() {
         return completed; 
        }
    public void setDate(LocalDate date) { 
        this.date = date;
     }
    public LocalDate getDate() {
         return date; 
        }
    public void setPriority(Priority priority) {
         this.priority = priority;
         }
    public Priority getPriority() {
         return priority; 
        }
    public void setCate(String cate) {
         this.cate = cate; }
    public String getCate() { return cate;
     }
    public void setNotes(String notes) { 
        this.notes = notes; }
    public String getNotes() { return notes;
     }
    public void complete() { 
        this.completed = true; 
    }

    public void edit_Task(String new_des, LocalDate new_date, Priority new_Priority, String new_cate) {
        this.descr = new_des;
        this.date = new_date;
        this.priority = new_Priority;
        this.cate = new_cate;
    }
    public void add_notes(String newNotes) {
         this.notes = newNotes;
         }

    @Override
    public String toString() {
        String status = completed ? "[✔]" : "[ ]";
        return status + " " + descr + "\nDue: " + date + "\nPriority: " + priority + "\nCategory: " + cate + "\nNotes: " + notes;
    }
    public LocalDate get_date() {
         return date;
         }
    public Priority get_Priority() { 
        return priority; 
    }
}

// Class to manage the list of tasks
class ToDoList {
    private List<Task> tasks = new ArrayList<>();

    public boolean empty() {
         return tasks.isEmpty(); 
        }
    public boolean validate(int index) {
        if (index >= 0 && index < tasks.size()) {
            return true;
        } else {
            System.out.println("Invalid task index!");
            return false;
        }
    }
    public void add_Task(String descr, LocalDate date, Priority priority, String cate) {
        tasks.add(new Task(descr, date, priority, cate));
        System.out.println("Task added Successfully!");
    }
    public void edit_task(int index, String new_des, LocalDate new_date, Priority new_Priority, String new_cate) {
        if (validate(index)) {
            tasks.get(index).edit_Task(new_des, new_date, new_Priority, new_cate);
            System.out.println("Task edited Successfully!");
        }
    }
    public void completed(int index) {
        if (validate(index)) {
            tasks.get(index).complete();
            System.out.println("Task marked as completed Successfully!");
        }
    }
    public void rem_tsk(int index) {
        if (validate(index)) {
            tasks.remove(index);
            System.out.println("Task removed Successfully!");
        }
    }
    public void display() {
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
    }
    public void search(String keyword) {
        System.out.println("Search results: ");
        tasks.stream()
             .filter(task -> task.toString().toLowerCase().contains(keyword.toLowerCase()))
             .forEach(System.out::println);
    }
    public void sort_by_date() {
        tasks.sort(Comparator.comparing(Task::get_date));
        System.out.println("Tasks sorted by due date Successfully!");
    }
    public void sort_by_prio() {
        tasks.sort(Comparator.comparing(Task::get_Priority));
        System.out.println("Tasks sorted by priority Successfully!");
    }
    public Task getTask(int index) {
        if (validate(index)) {
            return tasks.get(index);
        }
        throw new IndexOutOfBoundsException("Invalid task index!");
    }
}

// Main class to run the application
public class To_Do_List {
    
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        ToDoList toDoList = new ToDoList();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        System.out.println("\n==================== Welcome to This To Do List ====================\n");

        while (true) {
            int choice = 0, index = 0;

            // Display the menu
            System.out.println("\nTo-Do List Menu: \n");
            System.out.println("1)  Add Task");
            System.out.println("2)  Edit Task");
            System.out.println("3)  Mark Task as Completed");
            System.out.println("4)  Remove Task");
            System.out.println("5)  View Tasks");
            System.out.println("6)  Search Tasks");
            System.out.println("7)  Sort Tasks");
            System.out.println("8)  Add Notes to Task");
            System.out.println("9) Exit\n");

            System.out.print("Please, enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            while (choice > 9 || choice < 1) {
                System.out.println("Invalid input, Try again: ");
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character
            }

            if (choice >= 2 && choice <= 8) {
                if (toDoList.empty()) {
                    System.out.println("\nYour To Do list is Empty!\n");
                    System.out.println("Please, enter tasks first\n\n");
                    continue;
                }

                System.out.print("Please, enter your task number: ");
                index = scanner.nextInt() - 1;
                scanner.nextLine(); // Consume the newline character

                while (!toDoList.validate(index)) {
                    System.out.print("Invalid input, Try again: ");
                    index = scanner.nextInt() - 1;
                    scanner.nextLine(); // Consume the newline character
                }
            }

            switch (choice) {
                case 1: // Add a new task
                    System.out.print("Please, enter your task description: ");
                    String descr = scanner.nextLine();

                    System.out.print("Please, enter your due date (yyyy-MM-dd): ");
                    LocalDate date = LocalDate.parse(scanner.nextLine(), dateFormatter);

                    System.out.print("Please, enter your priority (LOW, MEDIUM, HIGH): ");
                    Priority priority = Priority.valueOf(scanner.nextLine().toUpperCase());

                    System.out.print("Please, enter your task category (Work, Personal, etc.): ");
                    String cate = scanner.nextLine();

                    toDoList.add_Task(descr, date, priority, cate);
                    break;

                case 2: // Edit an existing task
                    System.out.print("Please, enter your new description: ");
                    String new_des = scanner.nextLine();

                    System.out.print("Please, enter your new due date (yyyy-MM-dd): ");
                    LocalDate new_date = LocalDate.parse(scanner.nextLine(), dateFormatter);

                    System.out.print("Please, enter your new priority (LOW, MEDIUM, HIGH): ");
                    Priority new_Priority = Priority.valueOf(scanner.nextLine().toUpperCase());

                    System.out.print("Please, enter your new category: ");
                    String new_cate = scanner.nextLine();

                    toDoList.edit_task(index, new_des, new_date, new_Priority, new_cate);
                    break;

                case 3: // Mark a task as completed
                    toDoList.completed(index);
                    break;

                case 4: // Remove a task
                    toDoList.rem_tsk(index);
                    break;

                case 5: // View all tasks
                    toDoList.display();
                    break;

                case 6: // Search tasks
                    System.out.print("Please, enter your keyword to search: ");
                    String keyword = scanner.nextLine();
                    toDoList.search(keyword);
                    break;

                case 7: // Sort tasks
                    System.out.println("Sort by: \n 1) Due Date  \n 2) Priority");
                    int sortChoice = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character

                    while (sortChoice != 1 && sortChoice != 2) {
                        System.out.println("Invalid input, Try again: ");
                        sortChoice = scanner.nextInt();
                        scanner.nextLine(); // Consume the newline character
                    }

                    if (sortChoice == 1) {
                        toDoList.sort_by_date();
                    } else if (sortChoice == 2) {
                        toDoList.sort_by_prio();
                    }
                    break;

                case 8: // Add notes to a task
                    System.out.print("Please, enter your notes: ");
                    String notes = scanner.nextLine();
                    toDoList.getTask(index).add_notes(notes);
                    System.out.println("Notes added Successfully!");
                    break;

                case 9: // Exit the program
                    System.out.println("Thanks for using This To Do List!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}