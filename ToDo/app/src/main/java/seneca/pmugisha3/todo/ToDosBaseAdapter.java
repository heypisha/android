package seneca.pmugisha3.todo;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

public class ToDosBaseAdapter extends BaseAdapter {
  ArrayList<ToDo> toDos;
  Context context;

  interface UpdateTaskListener{
    void updateTask(int i);
  }
  interface DeleteTaskListener{
    void deleteTask(int i);
  }

  UpdateTaskListener updateListener;
  DeleteTaskListener deleteListener;

  ToDosBaseAdapter(ArrayList<ToDo> list, Context c){
    toDos = list;
    context = c;
  }

  @Override
  public int getCount() {
    return toDos.size() ;
  }

  @Override
  public ToDo getItem(int i) {
    return toDos.get(i);
  }

  @Override
  public long getItemId(int i) {
    return 0;
  }

  @Override
  public View getView(int i, View view, ViewGroup viewGroup) {
    View v = LayoutInflater.from(context).inflate(R.layout.todo_row_layout,viewGroup,false);
    TextView text = v.findViewById(R.id.taskname);
    TextView date = v.findViewById(R.id.taskdate);
    ImageButton deleteButton = v.findViewById(R.id.delete_task);
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch urgency_switch = v.findViewById(R.id.urgency_update);

    text.setText(toDos.get(i).task);
    date.setText(toDos.get(i).date_time.toString());
    if (toDos.get(i).isUrgent) {
      v.setBackgroundColor(context.getResources().getColor(R.color.red));
      urgency_switch.setChecked(toDos.get(i).isUrgent);
    } else {
      v.setBackgroundColor(context.getResources().getColor(R.color.green));
      urgency_switch.setChecked(toDos.get(i).isUrgent);
    }

    // delete one task
    deleteButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        // I need to delete this task (i)
        deleteListener.deleteTask(i);
      }
    });

    urgency_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        updateListener.updateTask(i);
      }
    });
    return v;
  }
}
