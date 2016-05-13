package com.ljq.sushi.entity;

/**
 * Created by Administrator on 2016/5/13.
 */
public class Restaurant {

    private String imgUrl;
    private String restaurantName;
    private String address;

    public Restaurant() {
    }

    public Restaurant(String imgUrl, String address, String restaurantName) {
        this.imgUrl = imgUrl;
        this.address = address;
        this.restaurantName = restaurantName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }
}
