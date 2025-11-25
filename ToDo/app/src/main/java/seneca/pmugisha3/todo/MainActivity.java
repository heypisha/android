package seneca.pmugisha3.todo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
    implements ToDosBaseAdapter.UpdateTaskListener,
    ToDosBaseAdapter.DeleteTaskListener
{
  private ActivityResultLauncher<Intent> myLauncher;

  ListView tasksTable;
  FloatingActionButton addNewTask;
  ArrayList<ToDo> mainActivityList;
  ToDosBaseAdapter adapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toast.makeText(this,"Welcome to your list",Toast.LENGTH_LONG).show();
    this.setTitle("BaseAdapter List");

    mainActivityList =  ((MyApp)getApplication()).todoManager.tasks;
    FloatingActionButton toList = findViewById(R.id.gotorecyceler);
    toList.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent i = new Intent(MainActivity.this, RecyclerViewActivity.class);
        startActivity(i);
      }
    });


    tasksTable = findViewById(R.id.taskstable);
    addNewTask = findViewById(R.id.addnewtask);
    adapter = new ToDosBaseAdapter(mainActivityList,this);
    adapter.updateListener = this;
    adapter.deleteListener = this;

    tasksTable.setAdapter(adapter);
    tasksTable.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(MainActivity.this,mainActivityList.get(i).task,Toast.LENGTH_LONG).show();
      }
    });


    myLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
      if (result.getResultCode() == RESULT_OK && result.getData() != null) {
        Intent data = result.getData();
        ToDo d = (ToDo) data.getSerializableExtra("newtodo");
        ((MyApp)getApplication()).todoManager.addTask(d);
        adapter.notifyDataSetChanged();
      }
    });

    addNewTask.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent i = new Intent(MainActivity.this, AddNewToDoActivity.class);
        myLauncher.launch(i);

      }
    });
  }

  @Override
  public void deleteTask(int i) {
    // if the tasks are in database
    ((MyApp)getApplication()).todoManager.deleteTask(i);
    adapter.notifyDataSetChanged();

  }

  @Override
  public void updateTask(int i) {
    ((MyApp)getApplication()).todoManager.updateTask(i);
    adapter.notifyDataSetChanged();
  }
}