package com.nyok.bottom_navigation.menu_dalam;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.nyok.bottom_navigation.adapter.NotificationAdapter;
import com.nyok.bottom_navigation.databinding.FragmentNotificationBinding;
import com.nyok.bottom_navigation.model.NotificationModel;
import java.util.ArrayList;
import java.util.List;

public class Notification_Activity extends AppCompatActivity {

    private FragmentNotificationBinding binding;
    private NotificationAdapter adapter;
    private List<NotificationModel> notificationList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Menggunakan view binding
        binding = FragmentNotificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        initNotificationList();
        setupRecyclerView();
    }

    private void initNotificationList() {
        // Contoh data notifikasi
        notificationList = new ArrayList<>();
        notificationList.add(new NotificationModel("Pemberitahuan 1", "pusing woy", "01-11-2024"));
        notificationList.add(new NotificationModel("Pemberitahuan 2", "Polije sip deh", "02-11-2024"));
        notificationList.add(new NotificationModel("Pemberitahuan 3", "Bondowoso Tape", "03-11-2024"));
    }

    private void setupRecyclerView() {
        adapter = new NotificationAdapter(notificationList);
        binding.notificationRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.notificationRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null; // Bersihkan binding untuk menghindari memory leak
    }
}

