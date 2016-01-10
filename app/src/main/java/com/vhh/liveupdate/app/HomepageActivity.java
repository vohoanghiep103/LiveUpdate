package com.vhh.liveupdate.app;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class HomepageActivity extends ListActivity {

    protected List<ParseObject> mStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        //initialize

        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            // do stuff with the user
            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Status");
            query.orderByAscending("createdAt");

            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> status, ParseException e) {
                    if(e == null) {
                        //Success
                        /*mStatus = status;
                        StatusAdapter adapter = new StatusAdapter(getListView().getContext(), mStatus);
                        setListAdapter(adapter);*/

                        /*for(int i=0; i<status.size() - 1; i++) {
                            for(int j= 1; j<status.size(); j++) {
                                if(status.get(i).equals(status.get(j))) {
                                    status.remove(j);
                                    LoadStatus(status);
                                }
                            }
                        }*/
                        LoadStatus(status);


                    } else {
                        //there was a problem. Alert user

                    }
                }
            });

        } else {
            // show the login screen
            Intent takeUserToLogin = new Intent(this, LoginActivity.class);
            startActivity(takeUserToLogin);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.homepage_menu, menu);
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

            case R.id.reloadStatus:
                //Reload status
                ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Status");
                query.orderByAscending("createdAt");

                query.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> status, ParseException e) {
                        if (e == null) {
                            //Success
                        /*mStatus = status;
                        StatusAdapter adapter = new StatusAdapter(getListView().getContext(), mStatus);
                        setListAdapter(adapter);*/

                            LoadStatus(status);

                        } else {
                            //there was a problem. Alert user

                        }
                    }
                });
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        ParseObject statusObject = mStatus.get(position);
        String objectId = statusObject.getObjectId();

        Intent goToDetailView = new Intent(HomepageActivity.this, StatusDetailView.class);
        goToDetailView.putExtra("objectID", objectId);
        startActivity(goToDetailView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Status");
        query.orderByAscending("createdAt");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> status, ParseException e) {
                if (e == null) {
                    //Success
                        /*mStatus = status;
                        StatusAdapter adapter = new StatusAdapter(getListView().getContext(), mStatus);
                        setListAdapter(adapter);*/

                    LoadStatus(status);

                } else {
                    //there was a problem. Alert user

                }
            }
        });
    }

    public void LoadStatus(List<ParseObject> status) {
        mStatus = status;
        StatusAdapter adapter = new StatusAdapter(getListView().getContext(), mStatus);
        setListAdapter(adapter);
    }
}
