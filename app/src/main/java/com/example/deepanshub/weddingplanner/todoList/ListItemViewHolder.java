package com.example.deepanshub.weddingplanner.todoList;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.deepanshub.weddingplanner.R;

import java.util.List;


/**
 * Created by deepanshub on 23/4/18.
 */

public class ListItemViewHolder extends RecyclerView.ViewHolder implements
        View.OnClickListener, View.OnCreateContextMenuListener {

    ItemClickListener itemClickListener;
    TextView item_title, item_description;


    public ListItemViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        itemView.setOnCreateContextMenuListener(this);
        item_title = itemView.findViewById(R.id.item_title);
        item_description = itemView.findViewById(R.id.item_description);

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

class ListItemAdapter extends RecyclerView.Adapter<ListItemViewHolder> {
    TodoList context;
    List<ToDo> todoList;


    public ListItemAdapter(Context context, List<ToDo> todoList) {

    }

    @Override
    public ListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context.getActivity());
        View view = layoutInflater.inflate(R.layout.todo_recycler_, parent, false);

        return new ListItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListItemViewHolder holder, int position) {
        holder.item_title.setText(todoList.get(position).getTitle());
        holder.item_description.setText(todoList.get(position).getTitle());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                context.title.setText(todoList.get(position).getTitle());
                context.description.setText(todoList.get(position).getTitle());

                context.isUpdate = true;
                context.idUpdate = todoList.get(position).getId();

            }
        });
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }
}