package com.depauw.restaurantrater;

public class Review {

    private String restaurantName;
    private String date;
    private String time;
    private String meal;
    private int rating;
    private int isFavorite;

    private static final int INDEX_RESTAURANT_NAME = 0;
    private static final int INDEX_DATE = 1;
    private static final int INDEX_TIME = 2;
    private static final int INDEX_MEAL = 3;
    private static final int INDEX_RATING = 4;
    private static final int INDEX_IS_FAVORITE = 5;

    public Review(String[] review){
        this.restaurantName = review[INDEX_RESTAURANT_NAME];
        this.date = review[INDEX_DATE];
        this.time = review[INDEX_TIME];
        this.meal = review[INDEX_MEAL];
        this.rating = Integer.valueOf(review[INDEX_RATING]);
        this.isFavorite = Integer.valueOf(review[INDEX_IS_FAVORITE]);
    }

    public Review(String restaurantName, String date, String time, String meal, int rating, int isFavorite) {
        this.restaurantName = restaurantName;
        this.date = date;
        this.time = time;
        this.meal = meal;
        this.rating = rating;
        this.isFavorite = isFavorite;
    }

    @Override
    public String toString() {
        return  restaurantName + "," + date + "," + time + "," + meal + "," + rating + "," + isFavorite;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getMeal() {
        return meal;
    }

    public int getRating() {
        return rating;
    }

    public int getIsFavorite() {
        return isFavorite;
    }
}
