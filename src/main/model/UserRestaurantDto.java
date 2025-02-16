package com.techelevator.model;

public class UserRestaurantDto {

    private int userId;
    private int restaurantId;
    private boolean liked;
    private boolean rejected;
    private int visitCount;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public boolean isRejected() {
        return rejected;
    }

    public void setRejected(boolean rejected) {
        this.rejected = rejected;
    }

    public int getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(int visitCount) {
        this.visitCount = visitCount;
    }

    @Override
    public String toString() {
        return "UserRestaurantDto{" +
                "userId=" + userId +
                ", restaurantId=" + restaurantId +
                ", isLiked=" + liked +
                ", isRejected=" + rejected +
                ", visitCount=" + visitCount +
                '}';
    }


}
