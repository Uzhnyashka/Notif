package com.example.bobyk.notif;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by bobyk on 26/07/15.
 */
public class NotificationView extends RelativeLayout {

    TextView txtTitle, txtMessage;

    public static NotificationView inflate(ViewGroup parent){
        NotificationView notificationView = (NotificationView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_view, parent, false);
        return notificationView;
    }
    public NotificationView(Context context) {
        this(context,null);
    }

    public NotificationView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NotificationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.item_view_children, this, true);
        setupChildren();
    }

    public void setupChildren(){
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtMessage = (TextView) findViewById(R.id.txtMessage);
    }

    public void setItem(NotificationItem _item){
        txtTitle.setText(_item.getTitle());
        txtMessage.setText(_item.getMessage());
    }
}
