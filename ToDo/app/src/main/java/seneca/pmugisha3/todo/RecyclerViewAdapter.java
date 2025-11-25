package seneca.pmugisha3.todo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends
    RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
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

  public RecyclerViewAdapter(ArrayList<ToDo> toDos, Context context) {
    this.toDos = toDos;
    this.context = context;
  }

  public class MyViewHolder extends RecyclerView.ViewHolder {
    TextView taskText;
    TextView dateText;
    Switch isurgent;
    ImageButton deletebutton;

    public MyViewHolder(View view) {
      super(view);
      // Define click listener for the ViewHolder's View
      taskText = view.findViewById(R.id.rtaskname);
      dateText = view.findViewById(R.id.rtaskdate);
      isurgent = view.findViewById(R.id.rurgency_update);
      deletebutton = view.findViewById(R.id.rdelete_task);
      deletebutton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          // notify the listener that this action happened
          int i = getBindingAdapterPosition();
          deleteListener.deleteTask(getBindingAdapterPosition());
        }
      });
      isurgent.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          int i = getBindingAdapterPosition();
          updateListener.updateTask(getBindingAdapterPosition());
        }
      });
    }
  }


  @NonNull
  @Override
  public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                         int viewType) {
    View v = LayoutInflater.from(context).inflate(R.layout.recycler_view_row,parent,false);
    return new MyViewHolder(v);
  }
  @Override
  public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
    holder.taskText.setText(toDos.get(position).task);
    holder.dateText.setText(toDos.get(position).date_time.toString());
    holder.isurgent.setChecked(toDos.get(position).isUrgent);

    if (toDos.get(position).isUrgent)
      holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.red));
    else
      holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.green));

  }
  @Override
  public int getItemCount() {
    return toDos.size();
  }
}
