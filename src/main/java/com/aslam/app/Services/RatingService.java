package com.aslam.app.Services;

import com.aslam.app.Interfaces.IFeedback;

public class RatingService implements IFeedback {

    private IFeedback feedback;
    private int rating;

    public RatingService(IFeedback feedback, int rating) {
        this.feedback = feedback;
        this.rating = rating;
    }

    public int getRating() {
        return rating;
    }

    @Override
    public String getFeedback() {
        return feedback.getFeedback();
    }
}
