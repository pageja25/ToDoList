import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.ComboBox;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * This class is for the application.
 * @version jdk11
 * @author jpage78
**/
public class ToDoList extends Application {
    private static int remaining = 0;
    private static int completed = 0;
    /**
     * This is for forming the actual application scene.
     * @param primaryStage the stage which everything is presented on
    **/
    public void start(Stage primaryStage) {
        primaryStage.setTitle("To Do List");
        ListView<Task> listView = new ListView<>();
        Text text = new Text(100, 150, "To Do List");
        text.setFont(Font.font("Arial", FontWeight.BOLD, 60));
        text.setUnderline(true);
        TextField textField1 = new TextField();
        Label label1 = new Label("Task Name: ");
        Label remaining1 = new Label("Tasks Remaining: " + remaining);
        Label completed1 = new Label("Tasks Completed: " + completed);
        ComboBox<Integer> comboBox = new ComboBox<>();
        comboBox.getItems().add(1);
        comboBox.getItems().add(2);
        comboBox.getItems().add(3);
        comboBox.getItems().add(4);
        comboBox.getItems().add(5);
        comboBox.setOnAction((event) -> {
            int selectedIndex = comboBox.getSelectionModel().getSelectedIndex();
            Object selectedItem = comboBox.getSelectionModel().getSelectedItem();
            System.out.println("Selection made: [" + selectedIndex + "] " + selectedItem);
            System.out.println("   ComboBox.getValue(): " + comboBox.getValue());
        });
        ComboBox<String> comboBox2 = new ComboBox<>();
        comboBox2.getItems().add("Study");
        comboBox2.getItems().add("Shop");
        comboBox2.getItems().add("Cook");
        comboBox2.getItems().add("Sleep");
        comboBox2.setOnAction((event) -> {
            int selectedIndex = comboBox2.getSelectionModel().getSelectedIndex();
            Object selectedItem = comboBox2.getSelectionModel().getSelectedItem();
            System.out.println("Selection made: [" + selectedIndex + "] " + selectedItem);
            System.out.println("   ComboBox2.getValue(): " + comboBox2.getValue());
        });
        Button enqueue = new Button();
        enqueue.setText("Enqueue Task");
        enqueue.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (comboBox2.getSelectionModel().isEmpty()) {
                    Alert error = new Alert(AlertType.ERROR, "Task Type Not Selected!");
                    error.show();
                    return;
                }
                if (comboBox.getSelectionModel().isEmpty()) {
                    Alert error = new Alert(AlertType.ERROR, "Time Needed Not Selected!");
                    error.show();
                    return;
                }
                if (textField1.getText().isEmpty()) {
                    Alert error = new Alert(AlertType.ERROR, "No Task Name!");
                    error.show();
                    return;
                }
                System.out.println("Something is happening!");
                Task task = new Task(textField1.getText(), comboBox2.getValue(), comboBox.getValue());
                for (Task t : listView.getItems()) {
                    if (task.getTaskName().equals(t.getTaskName())) {
                        Alert error = new Alert(AlertType.ERROR, "Duplicate Task Name!");
                        error.show();
                        return;
                    }
                }
                listView.getItems().add(task);
                remaining1.setText("Tasks Remaining: " + ++remaining);
            }
        });
        Button dequeue = new Button();
        dequeue.setText("Dequeue Task");
        dequeue.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Something is happening!");
                listView.getItems().remove(0);
                remaining1.setText("Tasks Remaining: " + --remaining);
                completed1.setText("Tasks Remaining: " + ++completed);
            }
        });
        HBox hbox = new HBox();
        hbox.getChildren().addAll(label1, textField1, comboBox2, comboBox, enqueue, dequeue);
        hbox.setSpacing(10);
        VBox vbox = new VBox();
        vbox.getChildren().addAll(remaining1, completed1);
        BorderPane root = new BorderPane();
        Insets insets = new Insets(10);
        root.setPadding(new Insets(15, 20, 10, 10));
        root.setCenter(listView);
        root.setMargin(listView, insets);
        root.setTop(text);
        root.setMargin(text, insets);
        root.setBottom(hbox);
        root.setMargin(hbox, insets);
        root.setLeft(vbox);
        hbox.setAlignment(Pos.CENTER);
        root.setAlignment(text, Pos.CENTER);
        primaryStage.setScene(new Scene(root, 750, 750));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
class Task {
    private String taskName;
    private String type;
    private int timeToDo;

    Task(String taskName, String type, int timeToDo) {
        this.taskName = taskName;
        this.type = type;
        this.timeToDo = timeToDo;
    }

    public String getTimeDue() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyy HH:mm");
        return LocalDateTime.now().plusHours(timeToDo).format(dtf);
    }

    public String toString() {
        return String.format("Task: %s - Type: %s - Complete by %s", taskName, type, getTimeDue());
    }
    public String getTaskName() {
        return taskName;
    }

}