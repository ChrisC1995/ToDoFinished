package com.campbellapps.christiancampbell.todolist2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.main_button_groceries);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                groceryIntent(); // sends user to taskList class and activity
            }
        });

    }

    public void groceryIntent(){
        Intent i;
        i = new Intent (MainActivity.this, taskList.class);
        startActivity(i);
    }
}
