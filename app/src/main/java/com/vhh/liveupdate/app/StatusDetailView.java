package com.vhh.liveupdate.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class StatusDetailView extends Activity {

    String status;
    protected TextView mStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_detail_view);

        mStatus = (TextView) findViewById(R.id.statusDetailView);

        //get intent that status detail
        Intent intent = getIntent();
        status = intent.getStringExtra("objectID");

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Status");

        query.getInBackground(status, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                if(e == null) {
                    //success
                    String userStatus = parseObject.getString("newStatus");
                    mStatus.setText(userStatus);
                } else {
                    //there was a problem, advice user

                }

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.statusdetail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {

            case R.id.updateStatus:
                //take user to update status
                Intent intent = new Intent(this, UpdateStatusActivity.class);
                startActivity(intent);

                break;

            case R.id.logoutUser:
                //logout the user
                ParseUser.logOut();

                //Take the user back to login screen
                Intent takeUserLogin = new Intent(this, LoginActivity.class);
                startActivity(takeUserLogin);

                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
