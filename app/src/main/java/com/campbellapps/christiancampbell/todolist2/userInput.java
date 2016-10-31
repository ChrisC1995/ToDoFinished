package com.campbellapps.christiancampbell.todolist2;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.media.Image;
import android.net.Uri;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class userInput extends AppCompatActivity {
    private static final int SELECTED_IMAGE = 1;
    ImageView imageView;

    private Button save;
    private EditText userInputTitle;
    private EditText userInputDay;
    private EditText userInputMonth;
    private EditText userInputTime;
    private EditText userInputInfo;
    private EditText userInputCategory;
    private Button imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_input);

        imageView = (ImageView) findViewById(R.id.imageView_input);

        save = (Button) findViewById(R.id.user_input_save);
        userInputTitle = (EditText) findViewById(R.id.user_input_title);
        userInputDay = (EditText) findViewById(R.id.user_input_day); // setting all fields with an id from xml file.
        userInputMonth = (EditText) findViewById(R.id.user_input_month);
        userInputTime= (EditText) findViewById(R.id.user_input_time);
        userInputInfo = (EditText) findViewById(R.id.user_input_info);
        userInputCategory = (EditText) findViewById(R.id.user_input_category);
        imageButton = (Button) findViewById(R.id.user_input_image);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnClick(); // calls btnClick method for pictures.
            }
        });

        Intent intent = getIntent(); //gets intent from listItems

        userInputTitle.setText(intent.getStringExtra("titleResult"));
        userInputInfo.setText(intent.getStringExtra("infoResult"));
        userInputCategory.setText(intent.getStringExtra("categoryResult"));
        userInputDay.setText(intent.getStringExtra("dayResult"));
        userInputMonth.setText(intent.getStringExtra("monthResult"));
        userInputTime.setText(intent.getStringExtra("timeResult")); // sets text is new task from the data in old task.

        save.setOnClickListener(new View.OnClickListener() { // when you click save, it will import new info back to the original intent.
            @Override
            public void onClick(View v) {

                String title = userInputTitle.getText().toString();
                String text = userInputInfo.getText().toString();
                String category = userInputCategory.getText().toString();

                String month = userInputMonth.getText().toString();
                String day = userInputDay.getText().toString();
                String time = userInputTime.getText().toString();
                String totalTime = month + "/" + day + ", " + time;

                Intent returnIntent = new Intent();

                if(title.equals("") || text.equals("") || totalTime.equals("")|| category.equals("") || day.equals("") || month.equals("") || time.equals("") ) {
                    Toast.makeText(userInput.this, "You Didn't Enter All The Fields", Toast.LENGTH_SHORT).show(); // if the user did not enter all fields, it will prompt this.
                }
                else{
                returnIntent.putExtra("titleResult", title);
                returnIntent.putExtra("textResult", text);
                returnIntent.putExtra("dateResult", totalTime);
                returnIntent.putExtra("categoryResult", category);
                returnIntent.putExtra("dayResult", day);
                returnIntent.putExtra("monthResult", month);
                returnIntent.putExtra("timeResult", time);

                    setResult(Activity.RESULT_OK, returnIntent);
                finish();}

//                listItems inputObject = new listItems(title, text, totalTime);
//                //inflate this to grocery list
            }
        });
    }

    public void btnClick() { //btnClick method called from above.
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, SELECTED_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) { // lets user enter an image from their gallary
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case SELECTED_IMAGE:
                if(resultCode ==RESULT_OK) {
                    Uri uri = data.getData();
                    String[]projection={MediaStore.Images.Media.DATA};

                    Cursor cursor=getContentResolver().query(uri, projection, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(projection[0]);
                    String filePath=cursor.getString(columnIndex);
                    cursor.close();

                    Bitmap yourSelectedImage = BitmapFactory.decodeFile(filePath);
                    Drawable d = new BitmapDrawable(yourSelectedImage);

                    imageView.setImageURI(uri);
                }
                break;
        }
    }
}
