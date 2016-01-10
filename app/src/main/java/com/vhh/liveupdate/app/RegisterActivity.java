package com.vhh.liveupdate.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class RegisterActivity extends Activity {

    protected EditText mUsername;
    protected EditText mUserEmail;
    protected EditText mUserPassword;
    protected Button mRegisterButton;
    protected Button mResetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //initialize
        mUsername = (EditText) findViewById(R.id.usernameRegisterEditText);
        mUserEmail = (EditText) findViewById(R.id.emailRegisterEditText);
        mUserPassword = (EditText) findViewById(R.id.passwordRegisterEditText);

        mRegisterButton = (Button) findViewById(R.id.registerButton);

        //listen to the register button click
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //get the user name, password and email and convert them to string
                String username = mUsername.getText().toString().trim();
                String password = mUserPassword.getText().toString().trim();
                String email = mUserEmail.getText().toString().trim();

                ParseUser user = new ParseUser();
                user.setUsername(username);
                user.setPassword(password);
                user.setEmail(email);

                try {
                    user.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                //user signed up successfully
                                //toast
                                Toast.makeText(RegisterActivity.this, "Success register!", Toast.LENGTH_LONG).show();

                                //take user homepage
                                Intent takeUserHome = new Intent(RegisterActivity.this, HomepageActivity.class);
                                startActivity(takeUserHome);

                            } else {
                                //there was a error signed up user. advice user
                                //toast
                                //Toast.makeText(RegisterActivity.this, "Some wrong when register!", Toast.LENGTH_LONG).show();

                            }
                        }
                    });
                } catch (Exception e) {
                    Log.e("Lỗi user", "Lỗi không tạo user mới được!");
                }
            }
        });

        mResetButton = (Button) findViewById(R.id.resetButton);
        mResetButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mUsername.setText(null);
                mUserEmail.setText(null);
                mUserPassword.setText(null);
                mUsername.requestFocus();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.register_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
