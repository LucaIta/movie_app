package com.epicodus.movieapp.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.epicodus.movieapp.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.findMovieInfoButton) Button mFindMovieInfoButton;
    @Bind(R.id.userInputEditText) EditText mUserInputEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mFindMovieInfoButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == mFindMovieInfoButton) {
            String userInput = mUserInputEditText.getText().toString();
            Intent intent = new Intent(MainActivity.this, MovieInfoActivity.class);
            intent.putExtra("userInput", userInput);
            startActivity(intent);
        }
    }
}
