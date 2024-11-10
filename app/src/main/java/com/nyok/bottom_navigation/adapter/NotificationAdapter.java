package com.nyok.bottom_navigation.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nyok.bottom_navigation.databinding.ItemNotificationBinding;
import com.nyok.bottom_navigation.model.NotificationModel;

import java.util.ArrayList;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private List<NotificationModel> notificationList;
    private OnItemClickListener listener;

    // Constructor
    public NotificationAdapter(List<NotificationModel> notificationList) {
        this.notificationList = new ArrayList<>(notificationList);
    }

    // Interface untuk Click Listener
    public interface OnItemClickListener {
        void onItemClick(NotificationModel notification);
    }

    // Setter untuk OnItemClickListener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemNotificationBinding binding;

        public ViewHolder(ItemNotificationBinding binding, final OnItemClickListener listener) {
            super(binding.getRoot());
            this.binding = binding;

            // Mengatur click listener pada item
            binding.getRoot().setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick((NotificationModel) v.getTag());
                    }
                }
            });
        }

        public void bind(NotificationModel notification, OnItemClickListener listener) {
            // Validasi untuk menghindari NullPointerException
            binding.titleTxt.setText(notification.getTitle() != null ? notification.getTitle() : "");
            binding.messageTxt.setText(notification.getMessage() != null ? notification.getMessage() : "");
            binding.timeTxt.setText(notification.getDate() != null ? notification.getDate() : "");

            // Set tag pada item untuk mendapatkan objek notification pada listener
            itemView.setTag(notification);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemNotificationBinding binding = ItemNotificationBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(notificationList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    // Metode untuk memperbarui data notifikasi secara dinamis
    public void updateData(List<NotificationModel> newNotificationList) {
        notificationList.clear();
        notificationList.addAll(newNotificationList);
        notifyDataSetChanged();
    }
}
