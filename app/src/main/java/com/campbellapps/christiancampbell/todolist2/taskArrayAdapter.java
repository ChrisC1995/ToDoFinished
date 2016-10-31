package com.campbellapps.christiancampbell.todolist2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by christiancampbell on 10/25/16.
 */

public class taskArrayAdapter extends ArrayAdapter<listItems> {

    private SimpleDateFormat formatter;

    public taskArrayAdapter(Context context, ArrayList<listItems> items){
        super(context, 0, items); //constructor for adapter

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){ // creates the view
        listItems groc = getItem(position);
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_list_item, parent, false); //inflates list item
        }

        TextView title = (TextView) convertView.findViewById(R.id.grocery_list_item_title);
        TextView info = (TextView) convertView.findViewById(R.id.grocery_list_item_info);
        TextView date = (TextView) convertView.findViewById(R.id.grocery_list_item_date); // puts these fields onto the view
        TextView category = (TextView) convertView.findViewById(R.id.grocery_list_item_category);
        TextView currentDate = (TextView) convertView.findViewById(R.id.grocery_list_item_current_date);
        formatter = new SimpleDateFormat("MM-dd-yyyy hh:mm aaa", Locale.getDefault()); // sets up the date format.

        Date date2 = new Date(); // sets the date
        title.setText(groc.getTitle()); // sets the title
        info.setText(groc.getText()); //sets the text
        date.setText("Due Date: " + groc.getDate()); // sets the date
        category.setText("Category: " + groc.getCategory().toLowerCase()); // sets the category
        currentDate.setText("Date Last Modified:" + (formatter.format(date2))); // sets the date last modified.

        return convertView;
    }

}