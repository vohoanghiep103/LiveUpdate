package com.vhh.liveupdate.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.parse.ParseObject;

import java.util.List;

/**
 * Created by VHH on 09-Jan-16.
 */
public class StatusAdapter extends ArrayAdapter {
    protected Context mContext;
    protected List<ParseObject> mStatus;

    public StatusAdapter(Context context, List<ParseObject> status) {
        super(context, R.layout.homepagecustomlayout, status);
        mContext = context;
        mStatus = status;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.homepagecustomlayout, null);
            holder = new ViewHolder();
            holder.usernameHomepage = (TextView) convertView
                    .findViewById(R.id.txtUsername);
            holder.statusHomepage = (TextView) convertView
                    .findViewById(R.id.txtStatus);

            convertView.setTag(holder);
        } else {

            holder = (ViewHolder) convertView.getTag();

        }

        ParseObject statusObject = mStatus.get(position);

        //image
        /*Picasso.with(getContext())
                .load(statusObject.getParseFile("image").getUrl())
                .placeholder(R.drawable.image_picture)
                .into(holder.imgView);*/

        // title
        String username = statusObject.getString("user");
        holder.usernameHomepage.setText(username);

        // content
        String status = statusObject.getString("newStatus");
        holder.statusHomepage.setText(status);

        return convertView;
    }

    public static class ViewHolder {
        //ImageView imgView;
        TextView usernameHomepage;
        TextView statusHomepage;

    }

}