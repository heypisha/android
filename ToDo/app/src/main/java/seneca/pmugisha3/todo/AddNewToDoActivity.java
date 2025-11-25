package seneca.pmugisha3.todo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class AddNewToDoActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_add_new_to_do);
    Button save = findViewById(R.id.saveButton);
    this.setTitle("Add New Task");

    Button cancel = findViewById(R.id.cancelButton);
    EditText taskText = findViewById(R.id.taskText);
    SwitchCompat isurgent = findViewById(R.id.isUrgentSwitch);
    DatePicker taskDatePicker = findViewById(R.id.taskDatePicker);

    cancel.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        finish();
      }
    });

    save.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (!taskText.getText().toString().isEmpty()) {
          String taskDate = taskDatePicker.getYear() + "/" + (taskDatePicker.getMonth() + 1) + "/" + taskDatePicker.getDayOfMonth();
          LocalDate localDate = LocalDate.of(taskDatePicker.getYear(), taskDatePicker.getMonth() + 1, taskDatePicker.getDayOfMonth());
          Date utilDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

          ToDo newTodo = new ToDo(taskText.getText().toString(), utilDate, isurgent.isChecked());
          Intent resultIntent = new Intent();
          resultIntent.putExtra("newtodo",newTodo);
          setResult(RESULT_OK, resultIntent);
          finish();
        }
      }
    });
  }
}