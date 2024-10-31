package com.example.messagereader;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final int SMS_PERMISSION_CODE = 1;
    private RecyclerView recyclerView;
    private MessageAdapter messageAdapter;
    private ArrayList<String> messages;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView studentInfo = findViewById(R.id.textStudentInfo);
        studentInfo.setText("Nguyen Thi Hue, 22115053122215 ");

        messages = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        messageAdapter = new MessageAdapter(messages);
        recyclerView.setAdapter(messageAdapter);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS}, SMS_PERMISSION_CODE);
        } else {
            fetchMessages();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == SMS_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                fetchMessages();
            } else {
                Toast.makeText(this, "Cần quyền đọc tin nhắn để sử dụng ứng dụng.", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void fetchMessages() {
        Uri inboxUri = Uri.parse("content://sms/inbox");
        Cursor cursor = getContentResolver().query(inboxUri, null, null, null, null);

        Log.d("MessageReader", "Đang truy vấn tin nhắn...");

        if (cursor != null) {
            Log.d("MessageReader", "Cursor không null, số lượng tin nhắn: " + cursor.getCount());
            if (cursor.moveToFirst()) {
                do {
                    String sender = cursor.getString(cursor.getColumnIndexOrThrow("address"));
                    String body = cursor.getString(cursor.getColumnIndexOrThrow("body"));
                    messages.add("Từ: " + sender + "\nTin nhắn: " + body);
                } while (cursor.moveToNext());
            } else {
                messages.add("Không có tin nhắn nào.");
            }
            cursor.close();
        } else {
            messages.add("Không thể truy vấn tin nhắn.");
        }
        messageAdapter.notifyDataSetChanged();
    }




}
