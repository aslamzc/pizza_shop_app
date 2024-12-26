package com.aslam.app.Services;

import com.aslam.app.Interfaces.IFeedback;

public class FeedbackService implements IFeedback {

    private String message;

    public FeedbackService(String message) {
        this.message = message;
    }

    @Override
    public String getFeedback() {
        return message;
    }
}
