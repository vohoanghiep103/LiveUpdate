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

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class UpdateStatusActivity extends Activity {


    protected EditText mStatusUpdate;
    protected Button mStatusUpdateBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_status);

        //initialize
        mStatusUpdate = (EditText) findViewById(R.id.updateStatusTextBox);
        mStatusUpdateBtn = (Button) findViewById(R.id.btnStatusUpdate);

        //Listen update button clicked
        mStatusUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get the current user
                ParseUser currentUser = ParseUser.getCurrentUser();
                String currentUserName = currentUser.getUsername();

                //Get the status user has enter then convert into string
                String newStatus = mStatusUpdate.getText().toString();

                if(newStatus.isEmpty()) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(UpdateStatusActivity.this);
                    builder.setMessage("Status should not be empty!");
                    builder.setTitle("Oops!");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //clone the dialog
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {

                    //save the status in backed or cloud or parse
                    ParseObject statusObject = new ParseObject("Status");
                    statusObject.put("user", currentUserName);
                    statusObject.put("newStatus", newStatus);
                    statusObject.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                //Successfully stored the new status in parse
                                Toast.makeText(UpdateStatusActivity.this, "Success!", Toast.LENGTH_LONG).show();

                                //take user to the homepage
                                Intent takeUserToHome = new Intent(UpdateStatusActivity.this,
                                        HomepageActivity.class);
                                startActivity(takeUserToHome);

                            } else {
                                //There was a problem stored new status, advice user
                                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateStatusActivity.this);
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
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.update_status_menu, menu);
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
