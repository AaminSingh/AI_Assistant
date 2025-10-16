package com.example.aiassistant;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.activity.ComponentActivity;
import androidx.activity.EdgeToEdge;

public class MainActivity extends ComponentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Button aiAssistantButton = findViewById(R.id.aiAssistantButton);
        aiAssistantButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AIAssistantActivity.class);
            startActivity(intent);
        });
    }
}
