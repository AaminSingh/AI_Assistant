package com.example.aiassistant;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.aiassistant.adapters.ChatAdapter;
import com.example.aiassistant.models.MessageModel;

import java.util.ArrayList;
import java.util.List;

public class AIAssistantActivity extends AppCompatActivity {
    private RecyclerView chatRecyclerView;
    private EditText messageInput;
    private ImageButton sendButton;
    private ImageView backButton;
    private LinearLayout typingIndicator;
    private ChatAdapter chatAdapter;
    private List<MessageModel> messageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai_assistant);

        try {
            initializeViews();
            setupRecyclerView();
            setupClickListeners();
            addWelcomeMessage();
        } catch (Exception e) {
            Toast.makeText(this, "Error initializing AI Assistant: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private void initializeViews() {
        chatRecyclerView = findViewById(R.id.chatRecyclerView);
        messageInput = findViewById(R.id.messageInput);
        sendButton = findViewById(R.id.sendButton);
        backButton = findViewById(R.id.backButton);
        typingIndicator = findViewById(R.id.typingIndicator);
        messageList = new ArrayList<>();
    }

    private void setupRecyclerView() {
        chatAdapter = new ChatAdapter(messageList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        chatRecyclerView.setLayoutManager(layoutManager);
        chatRecyclerView.setAdapter(chatAdapter);
    }

    private void setupClickListeners() {
        sendButton.setOnClickListener(v -> sendMessage());

        messageInput.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                sendMessage();
                return true;
            }
            return false;
        });

        backButton.setOnClickListener(v -> finish());
    }

    private void addWelcomeMessage() {
        String welcomeMessage = "Hi! I'm your personal safety assistant 👋\n\n" +
                "I can help you with:\n" +
                "🛡️ Safety tips and guidelines\n" +
                "🚨 Emergency procedures\n" +
                "📱 App features guidance\n" +
                "🏃‍♀️ Self-defense advice\n" +
                "📍 Location safety tips\n\n" +
                "How can I help you stay safe today?";

        MessageModel welcomeMsg = new MessageModel(welcomeMessage, "bot", System.currentTimeMillis());
        messageList.add(welcomeMsg);
        chatAdapter.notifyItemInserted(messageList.size() - 1);
        scrollToBottom();
    }

    private void sendMessage() {
        String userMessage = messageInput.getText().toString().trim();
        if (userMessage.isEmpty()) return;

        // Add user message to chat
        MessageModel userMsg = new MessageModel(userMessage, "user", System.currentTimeMillis());
        messageList.add(userMsg);
        chatAdapter.notifyItemInserted(messageList.size() - 1);
        scrollToBottom();

        // Clear input and show typing indicator
        messageInput.setText("");
        showTypingIndicator(true);

        // Generate response after delay (simulating AI processing)
        generateResponse(userMessage);
    }

    private void generateResponse(String userMessage) {
        chatRecyclerView.postDelayed(() -> {
            showTypingIndicator(false);

            String response = getSafetyResponse(userMessage);
            MessageModel botMsg = new MessageModel(response, "bot", System.currentTimeMillis());
            messageList.add(botMsg);
            chatAdapter.notifyItemInserted(messageList.size() - 1);
            scrollToBottom();
        }, 2000);
    }

    private String getSafetyResponse(String userMessage) {
        String message = userMessage.toLowerCase();

        if (message.contains("emergency") || message.contains("help") || message.contains("danger")) {
            return "🚨 **EMERGENCY RESPONSE** 🚨\n\n" +
                    "1. Stay calm and breathe\n" +
                    "2. Use the red emergency button in our app\n" +
                    "3. Call local emergency services immediately\n" +
                    "4. Share your location with trusted contacts\n" +
                    "5. Move to a safe, well-lit area if possible\n\n" +
                    "Remember: Your safety comes first. Trust your instincts!";
        }
        else if (message.contains("safety") || message.contains("tips") || message.contains("advice")) {
            return "🛡️ **Essential Safety Tips** 🛡️\n\n" +
                    "• Always inform someone about your whereabouts\n" +
                    "• Stay in well-lit, populated areas\n" +
                    "• Trust your instincts - they're usually right\n" +
                    "• Keep your phone charged and accessible\n" +
                    "• Use our app's location sharing feature\n" +
                    "• Carry a personal alarm or whistle\n\n" +
                    "Would you like specific advice for any situation?";
        }
        else if (message.contains("location") || message.contains("share") || message.contains("gps")) {
            return "📍 **Location Sharing Guide** 📍\n\n" +
                    "To share your location:\n" +
                    "1. Go to 'Trusted Contacts' in the app\n" +
                    "2. Select the contact you want to share with\n" +
                    "3. Tap the location sharing icon\n" +
                    "4. Your real-time location will be sent\n\n" +
                    "This helps your trusted contacts know where you are for safety purposes.";
        }
        else if (message.contains("self defense") || message.contains("defend")) {
            return "🥋 **Self-Defense Basics** 🥋\n\n" +
                    "• Stay alert and aware of your surroundings\n" +
                    "• Walk confidently with purpose\n" +
                    "• Keep your hands free and phone accessible\n" +
                    "• Learn basic moves: palm strikes, knee kicks\n" +
                    "• Aim for vulnerable spots: eyes, nose, groin\n" +
                    "• Make noise - yell 'FIRE!' to attract attention\n\n" +
                    "Consider taking a self-defense class for proper training!";
        }
        else if (message.contains("app") || message.contains("feature") || message.contains("how")) {
            return "📱 **App Features Guide** 📱\n\n" +
                    "Our safety app includes:\n" +
                    "• Emergency Alert Button - instant help\n" +
                    "• Trusted Contacts - manage your safety network\n" +
                    "• Police Station Locator - find nearby help\n" +
                    "• AI Assistant (me!) - 24/7 safety guidance\n" +
                    "• Location Sharing - real-time GPS sharing\n\n" +
                    "Which feature would you like to learn more about?";
        }
        else {
            return "I'm here to help with safety-related questions! 😊\n\n" +
                    "You can ask me about:\n" +
                    "• Emergency procedures\n" +
                    "• Personal safety tips\n" +
                    "• Self-defense advice\n" +
                    "• App features and how to use them\n" +
                    "• Location safety guidelines\n\n" +
                    "What specific safety topic interests you?";
        }
    }

    private void showTypingIndicator(boolean show) {
        if (typingIndicator != null) {
            typingIndicator.setVisibility(show ? View.VISIBLE : View.GONE);
            if (show) scrollToBottom();
        }
    }

    private void scrollToBottom() {
        if (messageList.size() > 0) {
            chatRecyclerView.smoothScrollToPosition(messageList.size() - 1);
        }
    }
}
