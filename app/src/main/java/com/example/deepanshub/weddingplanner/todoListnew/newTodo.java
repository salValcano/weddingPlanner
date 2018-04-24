package com.example.deepanshub.weddingplanner.todoListnew;


import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.deepanshub.weddingplanner.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import dmax.dialog.SpotsDialog;


public class newTodo extends Fragment implements ItemClickListener {


    List<TodoModel> todoModelList = new ArrayList<>();
    RecyclerView todoRecyclerView;
    RecyclerView.LayoutManager todolayoutManager;
    TodoRecyclerViewAdapter todoadapter;
    SharedPreferences preferences;
    FirebaseFirestore db;
    FloatingActionButton fab;
    EditText title, description;
    boolean isUpdate = false;
    String idUpdate = "";
    AlertDialog dialog;

    public newTodo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        preferences = getActivity().getSharedPreferences("Sharedpref", Context.MODE_PRIVATE);

        final View view = inflater.inflate(R.layout.fragment_new_todo, container, false);
        db = FirebaseFirestore.getInstance();
        dialog = new SpotsDialog(getActivity());
        title = view.findViewById(R.id.newtitle);
        description = view.findViewById(R.id.newdescription);
        fab = view.findViewById(R.id.newfab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (title.getText().toString().equals("") && description.getText().toString().equals("")) {

                    Snackbar snackbar = Snackbar.make(view.findViewById(R.id.coordinatorlayoutnewtodo), "You Have to Do Something", Snackbar.LENGTH_LONG);
                    snackbar.show();
                    Toast.makeText(getActivity(), "You Have to Do Something", Toast.LENGTH_LONG).show();

                } else {
                    if (!isUpdate) {


                        setData(title.getText().toString(), description.getText().toString());
                    } else {
                        updateData(title.getText().toString(), description.getText().toString());
                        isUpdate = !isUpdate;
                    }
                }
            }
        });
        todoRecyclerView = view.findViewById(R.id.todoRecycler);
        todolayoutManager = new LinearLayoutManager(this.getActivity());
        todoRecyclerView.setLayoutManager(todolayoutManager);
        loadData();
        return view;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle().equals("DELETE")) {
            deleteItem(item.getOrder());
        }
        return super.onContextItemSelected(item);
    }

    private void deleteItem(int index) {
        db.collection("ToDoList")
                .document(todoModelList.get(index).getId())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        loadData();
                    }
                });
    }

    private void updateData(String title, String description) {
        db.collection("ToDoList").document(idUpdate)
                .update("title", title, "description", description)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getActivity(), "update", Toast.LENGTH_LONG).show();

                    }
                });
        db.collection("ToDoList").document(idUpdate)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                        loadData();
                    }
                });
    }

    private void setData(String title, String description) {
        String id = UUID.randomUUID().toString();
        Map<String, Object> todo = new HashMap<>();
        todo.put("id", id);
        todo.put("title", title);
        todo.put("description", description);

        db.collection("ToDoList").document(id)
                .set(todo).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                loadData();
            }
        });
    }

    public void loadData() {
        dialog.show();

        db.collection("ToDoList")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                           @Override
                                           public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                               final int[] inc = {0};
                                               for (DocumentSnapshot doc : task.getResult()) {
                                                   TodoModel todo = new TodoModel(doc.getString("id"),
                                                           doc.getString("title"),
                                                           doc.getString("description"));
                                                   todoModelList.add(todo);

                                                   if (doc.getString("id") != null && doc.getString("id") != "") {
                                                       inc[0]++;
                                                   }
                                               }

                                               SharedPreferences.Editor editor = preferences.edit();

                                               editor.putInt("count", inc[0]);
                                               editor.commit();

                                               Log.e("------", "" + inc[0]);
                                               //Log.e("-sp--",""+preferences.getInt("count",0));
                                               todoadapter = new TodoRecyclerViewAdapter(todoModelList, newTodo.this);
                                               todoRecyclerView.setAdapter(todoadapter);
                                               dialog.dismiss();
                                           }

                                       }
                )
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "" + e.toString(), Toast.LENGTH_LONG).show();
                    }
                });


        Log.e("count", "" + preferences.getInt("count", 0));
    }

    @Override
    public void onClick(View view, int position, boolean isLongClick) {

    }
}
