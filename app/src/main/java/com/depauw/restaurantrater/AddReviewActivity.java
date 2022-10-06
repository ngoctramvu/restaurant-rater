package com.depauw.restaurantrater;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.depauw.restaurantrater.databinding.ActivityAddReviewBinding;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

public class AddReviewActivity extends AppCompatActivity {

    private ActivityAddReviewBinding binding;

    public static final String EXTRA_REVIEW = "com.depauw.restaurantrater.review";

    private DatePickerDialog.OnDateSetListener datepicker_review_date_dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            binding.edittextReviewDate.setText(i1+"/"+i2+"/"+i);
        }
    };

    private View.OnClickListener edittext_review_date_clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Calendar myCalendar = Calendar.getInstance();
            DatePickerDialog myPicker = new DatePickerDialog(AddReviewActivity.this,
                    datepicker_review_date_dateSetListener, myCalendar.get(Calendar.YEAR),
                    myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
            myPicker.show();
        }
    };

    private TimePickerDialog.OnTimeSetListener timepicker_review_time_timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int i, int i1) {
            binding.edittextReviewTime.setText(timeToString(i,i1));
        }
    };

    private View.OnClickListener edittext_review_time_clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Calendar myCalendar = Calendar.getInstance();
            TimePickerDialog myPicker = new TimePickerDialog(AddReviewActivity.this,
                    timepicker_review_time_timeSetListener, myCalendar.get(Calendar.HOUR_OF_DAY),
                    myCalendar.get(Calendar.MINUTE), true);
            myPicker.show();
        }
    };

    private View.OnClickListener button_add_review_clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //read variable from edit text
            String restaurantName = binding.edittextRestaurantName.getText().toString();
            String date = binding.edittextReviewDate.getText().toString();
            String time = binding.edittextReviewTime.getText().toString();
            String meal = getSelectedMeal();
            int rating = binding.seekbarRating.getProgress();
            int isFavorite = binding.checkboxFavorite.isChecked() ? 1 : 0;
            //create review object
            Review review = new Review(restaurantName, date, time, meal, rating, isFavorite);

            //append to csv file
            File myFile = new File(getFilesDir(), "reviews.csv");
            try(FileWriter myWriter = new FileWriter(myFile, true)){
                myWriter.write(review.toString() + System.lineSeparator());
            }
            catch(IOException e){
                e.printStackTrace();
            }

            //return intent to ViewReviewsActivity
            Intent returnIntent = new Intent();
            returnIntent.putExtra(EXTRA_REVIEW, review.toString());
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddReviewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.edittextReviewDate.setOnClickListener(edittext_review_date_clickListener);
        binding.edittextReviewTime.setOnClickListener(edittext_review_time_clickListener);
        binding.buttonAddReview.setOnClickListener(button_add_review_clickListener);
    }

    //format time HH:MM AM or HH:MM PM
    private String timeToString(int hour, int minute){
        if(hour == 0){
            return (hour + 12) + ":" + padTime(minute) + " AM";
        }
        if(hour < 12){
            return (hour) + ":" + padTime(minute) + " AM";
        }
        if(hour == 12){
            return (hour) + ":" + padTime(minute) + " PM";
        }
        return (hour-12) + ":" + padTime(minute) + " PM";

    }

    private String padTime(int time){
        if(time == 0)
            return "00";
        if(time < 10)
            return "0" + time;
        return "" + time;
    }

    private String getSelectedMeal(){
        switch(binding.radiogroupMeals.getCheckedRadioButtonId()){
            case R.id.radio_breakfast:
                return "Breakfast";
            case R.id.radio_lunch:
                return "Lunch";
            case R.id.radio_dinner:
                return "Dinner";
        };
        return "";
    }
}