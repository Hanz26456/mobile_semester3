package com.nyok.bottom_navigation.login;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.nyok.bottom_navigation.R;
import com.nyok.bottom_navigation.database.DatabaseHelperLogin;

public class Register extends BottomSheetDialogFragment {

    private DatabaseHelperLogin db;

    public static final String TAG = "Register";
    public static Register newInstance() {
        return new Register();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EditText email = view.findViewById(R.id.email);
        EditText username = view.findViewById(R.id.username);
        EditText password = view.findViewById(R.id.password);
        EditText repassword = view.findViewById(R.id.konfirmasipassword);

        Button daftar = view.findViewById(R.id.btnRegister);

        db = new DatabaseHelperLogin(getActivity());

        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inEmail = email.getText().toString();
                String Username = username.getText().toString();
                String inPassword = password.getText().toString();
                String inRepassword = repassword.getText().toString();  // Perbaikan di sini

                if (!inRepassword.equals(inPassword)) {
                    repassword.setError("Password Tidak Sama");
                } else {
                    Boolean daftar = db.simpanUser(inEmail, Username, inPassword);
                    if (daftar) {
                        Toast.makeText(getActivity(), "Daftar Berhasil", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "Daftar Gagal", Toast.LENGTH_SHORT).show();
                    }
                    dismiss();
                }
            }
        });
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Activity activity = getActivity();
        if (activity instanceof OnDialogCloseListener) {
            ((OnDialogCloseListener) activity).onDialogClose(dialog);
        }
    }
}
