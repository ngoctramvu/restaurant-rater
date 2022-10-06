package com.depauw.restaurantrater;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.depauw.restaurantrater.databinding.ActivityViewReviewsBinding;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ViewReviewsActivity extends AppCompatActivity {

    private ActivityViewReviewsBinding binding;

    private List<Review> reviews;

    public static int FROM_ADD_REVIEW_ACTIVITY = 1;

    private View.OnClickListener button_add_review_clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent theIntent = new Intent(ViewReviewsActivity.this, AddReviewActivity.class);
            startActivityForResult(theIntent, FROM_ADD_REVIEW_ACTIVITY);
        }
    };

    private AdapterView.OnItemClickListener listviewReviews_itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Review review = (Review) parent.getItemAtPosition(position);

            AlertDialog.Builder myBuilder = new AlertDialog.Builder(ViewReviewsActivity.this);
            myBuilder.setTitle("Review Details")
                    .setMessage("This review was created on " + review.getDate() + " at " + review.getTime());

            AlertDialog myDialog = myBuilder.create();
            myDialog.show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewReviewsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        reviews = getReviewsList();
        populateListView();

        binding.buttonAddReview.setOnClickListener(button_add_review_clickListener);
    }

//    @Override
//    protected void onRestart(){
//        super.onRestart();
//        populateListView();
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == FROM_ADD_REVIEW_ACTIVITY && resultCode == Activity.RESULT_OK){
            String addedReview = data.getStringExtra(AddReviewActivity.EXTRA_REVIEW);
            addReviewToList(addedReview);
            populateListView();
        }
    }

    //populate list view in ViewReviewsActivity with current List<Review> reviews
    private void populateListView(){
        CustomAdapter myAdapter = new CustomAdapter(this, reviews);
        binding.listviewReviews.setAdapter(myAdapter);
        binding.listviewReviews.setOnItemClickListener(listviewReviews_itemClickListener);
    }

    /*
        add review string to List<Review> reviews instance variable
        params: review string (str) separated by ","
     */
    private void addReviewToList(String str){
        String[] tokens = str.split(",");
        Review review = new Review(tokens);
        reviews.add(review);
    }

    //read all reviews from reviews.csv
    private List<Review> getReviewsList(){
        reviews = new ArrayList<>();
        File myFile = new File(getFilesDir(), "reviews.csv");

        try(Scanner myScanner = new Scanner(myFile)){
            while(myScanner.hasNextLine()){
                addReviewToList(myScanner.nextLine());
            }
        } catch(IOException e){
            e.printStackTrace();
        }
        return reviews;
    }
}