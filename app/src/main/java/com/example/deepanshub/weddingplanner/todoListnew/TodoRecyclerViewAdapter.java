package com.example.deepanshub.weddingplanner.todoListnew;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.deepanshub.weddingplanner.R;

import java.util.List;


/**
 * Created by deepanshub on 23/4/18.
 */

public class TodoRecyclerViewAdapter extends RecyclerView.Adapter<TodoRecyclerViewAdapter.BindingHolder> {


    newTodo newTodoobj;
    Context context;
    List<TodoModel> todoModels;


    public TodoRecyclerViewAdapter(List<TodoModel> todoModels, newTodo newTodoobj) {
        this.todoModels = todoModels;
        //this.context=context;
        this.newTodoobj = newTodoobj;
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(newTodoobj.getActivity()).inflate(R.layout.new_todo_recycler, parent, false);
        return new BindingHolder(view);
    }

    @Override
    public void onBindViewHolder(final BindingHolder holder, int position) {

        final TodoModel modelPos = todoModels.get(position);
        holder.title.setText(modelPos.getTitle());
        holder.description.setText(modelPos.getDescription());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                newTodoobj.title.setText(modelPos.getTitle());
                newTodoobj.description.setText(modelPos.getDescription());
                newTodoobj.isUpdate = true;
                newTodoobj.idUpdate = modelPos.getId();
                Log.e("", "" + newTodoobj.title.getText());

            }
        });
//        holder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Log.e("tag",""+newTodoobj.title.getText());
//                Log.e("tag", "onClick: "+modelPos.getTitle() );
//                holder.appBarTitle.setText(modelPos.getTitle());
//                holder.appBarDescription.setText(modelPos.getDescription());
//                newTodoobj.title.setText(modelPos.getTitle());
//                newTodoobj.description.setText(modelPos.getDescription());
//                newTodoobj.isUpdate=true;
//                newTodoobj.idUpdate=modelPos.getId();
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return todoModels.size();
    }

    public class BindingHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener, View.OnCreateContextMenuListener {

        ItemClickListener itemClickListener;
        CardView cardView;
        TextView title, description;
        EditText appBarTitle, appBarDescription;

        public BindingHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
            cardView = itemView.findViewById(R.id.todocardview);
            appBarDescription = itemView.findViewById(R.id.newdescription);
            appBarTitle = itemView.findViewById(R.id.newtitle);
            title = itemView.findViewById(R.id.todoitem_title);
            description = itemView.findViewById(R.id.todoitem_description);

        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;

        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), false);


        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Select the action");
            menu.add(0, 0, getAdapterPosition(), "DELETE");

        }
    }
}
