package seneca.pmugisha3.todo;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewActivity extends AppCompatActivity
    implements RecyclerViewAdapter.UpdateTaskListener,
    RecyclerViewAdapter.DeleteTaskListener
{
  RecyclerView recyclerList;
  RecyclerViewAdapter adapter;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_recycler_view);
    recyclerList = findViewById(R.id.rlist);
    this.setTitle("Recycler View List");
    adapter = new RecyclerViewAdapter(((MyApp)getApplication()).todoManager.tasks,this);
    adapter.deleteListener = this;
    adapter.updateListener = this;
    recyclerList.setAdapter(adapter);
    recyclerList.setLayoutManager(new LinearLayoutManager(this));

  }

  @Override
  public void deleteTask(int i) {
    ((MyApp)getApplication()).todoManager.deleteTask(i);
    adapter.notifyDataSetChanged();
  }

  @Override
  public void updateTask(int i) {
    ((MyApp)getApplication()).todoManager.updateTask(i);
    adapter.notifyDataSetChanged();
  }
}