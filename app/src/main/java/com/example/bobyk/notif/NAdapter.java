package com.example.bobyk.notif;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by bobyk on 26/07/15.
 */
public class NAdapter extends BaseAdapter {

    Context context;
    ArrayList<NotificationItem> NotificationList = new ArrayList<>();
    public NAdapter(Context _context, ArrayList<NotificationItem> _ni){
        context = _context;
        NotificationList = _ni;
    }

    @Override
    public View getView(int _position, View _convertView, ViewGroup _parent) {
        NotificationView nv = (NotificationView) _convertView;
        if (nv == null){
            nv = NotificationView.inflate(_parent);
        }
        nv.setItem(NotificationList.get(_position));
        return nv;
    }

    @Override
    public long getItemId(int _position) {
        return _position;
    }

    @Override
    public Object getItem(int _position) {
        return NotificationList.get(_position);
    }

    @Override
    public int getCount() {
        return NotificationList.size();
    }
}
