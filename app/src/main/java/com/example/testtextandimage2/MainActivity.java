package com.example.testtextandimage2;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {


    private TextView tvTest;
    private String mUrl1 = "https://img.hayuki.com/upload/headwear_webp/2025-09-23/e2af1492c6bd2f54e993b71bcbacdd42.webp";
    private String mUrl2 = "https://img.hayuki.com/upload/headwear_webp/2025-09-23/646b678b6ac0e21d79d7d200c7f382d6.webp";
    private String mUrl3 = "https://img.hayuki.com/upload/headwear_webp/2025-09-23/1a421e749f86e4d77e1553586b94c68a.webp";
    private String mUrl4 = "https://img.hayuki.com/upload/headwear_webp/2025-09-23/e2af1492c6bd2f54e993b71bcbacdd42.webp";
    private String mUrl5 = "https://img.hayuki.com/upload/headwear_webp/2025-09-23/646b678b6ac0e21d79d7d200c7f382d6.webp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        tvTest = findViewById(R.id.tv_test);
    }

    public void onTest1(View v) {

    }

    public void onTest2(View v) {

    }

}