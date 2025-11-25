package seneca.pmugisha3.todo;

import java.util.ArrayList;

public class ToDoManager {
  ArrayList<ToDo> tasks = new ArrayList<>(0);

  void addTask(ToDo newTodo){
    tasks.add(newTodo);
  }
  void deleteTask(int i){
    tasks.remove(i);
  }
  void updateTask(int i){
    tasks.get(i).isUrgent = !tasks.get(i).isUrgent;
  }
}
