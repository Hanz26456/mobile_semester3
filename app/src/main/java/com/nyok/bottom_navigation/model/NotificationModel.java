package com.nyok.bottom_navigation.model;

import android.os.Parcel;
import android.os.Parcelable;

public class NotificationModel implements Parcelable {
    private String title;
    private String message;
    private String date;

    public NotificationModel(String title, String message, String date) {
        this.title = title;
        this.message = message;
        this.date = date;
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }

    // Setters (Opsional, jika data perlu diubah)
    public void setTitle(String title) {
        this.title = title;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDate(String date) {
        this.date = date;
    }

    // Parcelable implementation
    protected NotificationModel(Parcel in) {
        title = in.readString();
        message = in.readString();
        date = in.readString();
    }

    public static final Creator<NotificationModel> CREATOR = new Creator<NotificationModel>() {
        @Override
        public NotificationModel createFromParcel(Parcel in) {
            return new NotificationModel(in);
        }

        @Override
        public NotificationModel[] newArray(int size) {
            return new NotificationModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(title);
        parcel.writeString(message);
        parcel.writeString(date);
    }
}
