package com.ezybooks.collegeonyourterms.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ezybooks.collegeonyourterms.R;

/**This class sets up the main activity, or home screen for the app*/
public class MainActivity extends AppCompatActivity {
public static int numAlert;

    /**This overridden method creates the home screen UI.
     * @param savedInstanceState*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.enterButton);
        button.setOnClickListener(new View.OnClickListener() {
            /**This overridden method makes a new intent to send the user to the term list screen.
             * @param v */
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TermList.class);
                intent.putExtra("test", "Information sent");
                startActivity(intent);
            }
        });
    }
}