package com.ndimension.smartlibrary.module;

public class FeedbackModule {
    private String feedback_content,feedback_created_date,book_title,book_author,book_img;

    public String getFeedback_content() {
        return feedback_content;
    }

    public void setFeedback_content(String feedback_content) {
        this.feedback_content = feedback_content;
    }

    public String getFeedback_created_date() {
        return feedback_created_date;
    }

    public void setFeedback_created_date(String feedback_created_date) {
        this.feedback_created_date = feedback_created_date;
    }

    public String getBook_title() {
        return book_title;
    }

    public void setBook_title(String book_title) {
        this.book_title = book_title;
    }

    public String getBook_author() {
        return book_author;
    }

    public void setBook_author(String book_author) {
        this.book_author = book_author;
    }

    public String getBook_img() {
        return book_img;
    }

    public void setBook_img(String book_img) {
        this.book_img = book_img;
    }
}
