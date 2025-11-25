package seneca.pmugisha3.todo;

import java.io.Serializable;
import java.util.Date;

public class ToDo implements Serializable {
  String task;
  Date date_time;
  Boolean isUrgent;

  public ToDo(String task, Date date_time, Boolean isUrgent) {
    this.task = task;
    this.date_time = date_time;
    this.isUrgent = isUrgent;
  }
}
