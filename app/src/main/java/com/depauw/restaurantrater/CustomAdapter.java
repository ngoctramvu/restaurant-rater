package com.depauw.restaurantrater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter extends BaseAdapter {

    private Context context;
    private List<Review> reviews;

    public CustomAdapter(Context context, List<Review> reviews){
        this.context = context;
        this.reviews = reviews;
    }

    @Override
    public int getCount() {
        return reviews.size();
    }

    @Override
    public Object getItem(int position) {
        return reviews.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.listview_reviews_row, parent, false);
        }
        Review review = reviews.get(position);

        TextView reviewRestaurantName = convertView.findViewById(R.id.textview_review_restaurant_name);
        RadioGroup reviewMeal = convertView.findViewById(R.id.radiogroup_review_meal);
        RatingBar reviewIsFavorite = convertView.findViewById(R.id.ratingbar_review_is_favorite);
        ProgressBar reviewRating = convertView.findViewById(R.id.progressbar_review_rating);

        reviewRestaurantName.setText(review.getRestaurantName());

        int id = -1;
        if(review.getMeal().equals("Breakfast")){
            id = R.id.radiobutton_review_breakfast;
        }else if(review.getMeal().equals("Lunch")){
            id = R.id.radiobutton_review_lunch;
        }else if(review.getMeal().equals("Dinner")){
            id = R.id.radiobutton_review_dinner;
        }

        reviewMeal.check(id);
        reviewIsFavorite.setRating(review.getIsFavorite());
        reviewRating.setProgress(review.getRating());

        return convertView;
    }
}
