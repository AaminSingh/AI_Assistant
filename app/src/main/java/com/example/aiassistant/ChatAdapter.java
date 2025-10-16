package com.example.aiassistant;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aiassistant.models.MessageModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    private List<MessageModel> messageList;
    private static final int USER_MESSAGE = 1;
    private static final int BOT_MESSAGE = 2;

    public ChatAdapter(List<MessageModel> messageList) {
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == USER_MESSAGE) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.user_message_item, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.bot_message_item, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MessageModel message = messageList.get(position);
        holder.messageText.setText(message.getMessage());

        // Format timestamp
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        holder.timeText.setText(sdf.format(new Date(message.getTimestamp())));

        if (getItemViewType(position) == BOT_MESSAGE) {
            if (message.getMessage().contains("```")) {
                holder.codeActionLayout.setVisibility(View.VISIBLE);
                holder.runCodeButton.setOnClickListener(v -> {
                    Toast.makeText(v.getContext(), "Running code...", Toast.LENGTH_SHORT).show();
                });
                holder.acceptCodeButton.setOnClickListener(v -> {
                    Toast.makeText(v.getContext(), "Accepting code...", Toast.LENGTH_SHORT).show();
                });
            } else {
                holder.codeActionLayout.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (messageList.get(position).getSender().equals("user")) {
            return USER_MESSAGE;
        } else {
            return BOT_MESSAGE;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText;
        LinearLayout codeActionLayout;
        Button runCodeButton, acceptCodeButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.messageText);
            timeText = itemView.findViewById(R.id.timeText);
            codeActionLayout = itemView.findViewById(R.id.codeActionLayout);
            runCodeButton = itemView.findViewById(R.id.runCodeButton);
            acceptCodeButton = itemView.findViewById(R.id.acceptCodeButton);
        }
    }
}
