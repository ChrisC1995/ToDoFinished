package com.campbellapps.christiancampbell.todolist2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class taskList extends AppCompatActivity {

    private Button button;
    ArrayList<listItems> arrayOfItems = new ArrayList<listItems>(); // Name of arraylist that will become adapter.

    String filename = "helloFile"; //name of file that will be saved used GSON and JSON
    List<listItems> todos = new ArrayList<>(); //name of list that will be saved with the contents of arrayOfItems
    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        setupNotes(); // calls setupNotes method that is used to save.

        final taskArrayAdapter adapter = new taskArrayAdapter(this, arrayOfItems);
        ListView listView = (ListView) findViewById(R.id.grocery_listview);
        listView.setAdapter(adapter); // sets the custom adapter up.

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listItems item = arrayOfItems.get(position);
                Intent detailScreen = new Intent(taskList.this, userInput.class);
                detailScreen.putExtra("titleResult", item.getTitle());
                detailScreen.putExtra("infoResult", item.getText());
                detailScreen.putExtra("categoryResult", item.getCategory());
                detailScreen.putExtra("dayResult", item.getDay());
                detailScreen.putExtra("monthResult", item.getMonth());
                detailScreen.putExtra("timeResult", item.getTime()); // All of these lets the user edit their own tasks.

                adapter.remove(arrayOfItems.get(position));
                adapter.notifyDataSetChanged(); // updates the adapter

                startActivityForResult(detailScreen, 1);

            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(taskList.this);
                alertBuilder.setTitle("Delete/Complete This Task");
                alertBuilder.setMessage("Are you sure you are done with it?");
                alertBuilder.setNegativeButton("Get Me Outta Here", null);
                alertBuilder.setPositiveButton("Delete/Mark As Completed", new DialogInterface.OnClickListener() { // sets a dialog box for deleting/completing
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listItems item = arrayOfItems.get(position);

                        arrayOfItems.remove(item); //removes the task.
                        adapter.notifyDataSetChanged();
                        writeTodos(); // calls the method to write the file.
                    }
                });
                alertBuilder.create().show();
                return true;
            }
        });

        button = (Button) findViewById(R.id.grocery_addButton); // defines the button using a button id.

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addIntent(); // calls the addIntent to send the page to the userInput activity.
            }
        });
    }

    public void addIntent() {
        Intent i;
        i = new Intent(taskList.this, userInput.class);
        startActivityForResult(i, 1); // sends user to userInput class
    }

    protected void onActivityResult(int requestCode, final int resultCode, Intent view) {
        super.onActivityResult(requestCode, resultCode, view);
        getIntent(); // recieves the intent from the userInput class

        if (resultCode == RESULT_OK) { // if the result code is RESULT_OK, do these tasks.
            final String title = view.getStringExtra("titleResult");
            String text = view.getStringExtra("textResult");
            String date = view.getStringExtra("dateResult");
            String category = view.getStringExtra("categoryResult");
            String day = view.getStringExtra("dayResult");
            String month = view.getStringExtra("monthResult");
            String time = view.getStringExtra("timeResult"); // gets the extras from the userInput class

            final taskArrayAdapter adapter = new taskArrayAdapter(this, arrayOfItems);
            ListView listView = (ListView) findViewById(R.id.grocery_listview);
            listView.setAdapter(adapter); //sets up the adapter if it is not set up already. This helps with scope problems and does not present any problems.

            Date date2 = new Date(); // sets the date
            listItems newItem = new listItems(title, text, date, category, date2, day, month, time); // creates the item.
            adapter.add(newItem); // adds the item to the adapter

            Collections.sort(arrayOfItems); // sorts the arrayList (adapter) by category.

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) { // same as on item click above, but after creation.
                    listItems item = arrayOfItems.get(position);
                    Intent detailScreen = new Intent(taskList.this, userInput.class);
                    detailScreen.putExtra("titleResult", item.getTitle());
                    detailScreen.putExtra("infoResult", item.getText());
                    detailScreen.putExtra("categoryResult", item.getCategory());
                    detailScreen.putExtra("dayResult", item.getDay());
                    detailScreen.putExtra("monthResult", item.getMonth());
                    detailScreen.putExtra("timeResult", item.getTime());

                    adapter.remove(arrayOfItems.get(position));
                    adapter.notifyDataSetChanged();

                    startActivityForResult(detailScreen, 1);
//                    adapter.remove(arrayOfItems.get(position));
//                    adapter.notifyDataSetChanged();
                }
            });


            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() { // same as on long item click above, but after creation.
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(taskList.this);
                    alertBuilder.setTitle("Delete/Complete This Task");
                    alertBuilder.setMessage("Are you sure you are done with it?");
                    alertBuilder.setNegativeButton("Get Me Outta Here", null);
                    alertBuilder.setPositiveButton("Delete/Mark As Completed", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            listItems item = arrayOfItems.get(position);

                            arrayOfItems.remove(item);
                            adapter.notifyDataSetChanged();
                            writeTodos();
                        }
                    });
                    alertBuilder.create().show();
                    return true;
                }
            });
        } else {
            Toast.makeText(this, "Add Canceled", Toast.LENGTH_SHORT).show(); //displays that their adding of an object has been canceled.
        }
        writeTodos(); //calls writeToDos method.
    }

    public void setupNotes() { //lets user save file
        arrayOfItems = new ArrayList<>();

        File filesDir = this.getFilesDir();
        File todoFile = new File(filesDir + File.separator + filename);
        if (todoFile.exists()) {
            readTodos(todoFile);

            for (listItems todo : todos) {
                arrayOfItems.add(todo);
            }
        } else {
            // If the file doesn't exist, create it
//            Date date = new Date();
//            todos.add(new listItems("todo 1","A todo", "thing", "thing", date));
//            todos.add(new listItems("todo 2","Another todo", "thing", "thing", date));
//            todos.add(new listItems("todo 3","One more todo", "thing", "thing", date));

            writeTodos();
        }
    }


    private void readTodos(File todoFile) {
        FileInputStream inputStream = null;
        String todosText = "";
        try {
            inputStream = openFileInput(todoFile.getName());
            byte[] input = new byte[inputStream.available()];
            while (inputStream.read(input) != -1) {}
            todosText = new String(input);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            listItems[] todoList = gson.fromJson(todosText, listItems[].class);
            todos = Arrays.asList(todoList);
        }
    }

    private void writeTodos() {
        FileOutputStream outputStream = null;
        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);

            String json = gson.toJson(arrayOfItems);
            byte[] bytes = json.getBytes();
            outputStream.write(bytes);

            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
            } catch (Exception ignored) {}
        }
    }
}
