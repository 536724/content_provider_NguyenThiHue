package com.example.messagereader;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    private ArrayList<String> messages;

    public MessageAdapter(ArrayList<String> messages) {
        this.messages = messages;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        String message = messages.get(position);
        String[] parts = message.split("\n"); // Tách sender và body
        holder.textSender.setText(parts[0]);
        holder.textBody.setText(parts[1]);

        if (position % 2 == 0) {
            holder.itemView.setBackgroundColor(0xFFE0E0E0); // Màu xám sáng
        } else {
            holder.itemView.setBackgroundColor(0xFFFFFFFF); // Màu trắng
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    static class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView textSender;
        TextView textBody;

        public MessageViewHolder(View itemView) {
            super(itemView);
            textSender = itemView.findViewById(R.id.textSender);
            textBody = itemView.findViewById(R.id.textBody);
        }
    }
}
