package com.vhh.liveupdate.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;


public class LoginActivity extends Activity {

    protected EditText mUsername;
    protected EditText mPassword;
    protected Button btnLogin;
    protected Button btnCreateAccount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //initialize
        mUsername = (EditText) findViewById(R.id.usernameLoginTextBox);
        mPassword = (EditText) findViewById(R.id.passwordLoginTextBox);
        btnLogin = (Button) findViewById(R.id.loginButton);
        btnCreateAccount = (Button) findViewById(R.id.createAccountButton);

        //Listen create account button clicked
        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //take user to register
                Intent takeUserToRegister = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(takeUserToRegister);
            }
        });

        //Listen to when the btnLogin button is clicked
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get the user inputs from text box and convert to string
                String username = mUsername.getText().toString().trim();
                String password = mPassword.getText().toString().trim();


                    //Login the user using parse sdk
                    ParseUser.logInInBackground(username, password, new LogInCallback() {
                        @Override
                        public void done(ParseUser parseuser, ParseException e) {
                            if (e == null) {
                                //success user logged in
                                Toast.makeText(LoginActivity.this, "Welcome back!", Toast.LENGTH_LONG).show();

                                //take user to the homepage
                                Intent takeUserHome = new Intent(LoginActivity.this, HomepageActivity.class);
                                startActivity(takeUserHome);

                            } else {
                                //there was a problem, advice user
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage(e.getMessage());
                                builder.setTitle("Sorry!");
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //clone the dialog
                                        dialog.dismiss();
                                    }
                                });
                                AlertDialog dialog = builder.create();
                                dialog.show();

                            }
                        }
                    });


            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.login_menu, menu);
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
