package com.example.bobyk.notif;

/**
 * Created by bobyk on 26/07/15.
 */
public class NotificationItem {
    public String message;
    public String title;
    public String subtitle;
    public String tickerText;
    public int vibrate;
    public int sound;


    public String getMessage(){
        return message;
    }

    public String getTitle(){
        return title;
    }

    public String getSubtitle(){
        return subtitle;
    }

    public String getTickerText(){
        return tickerText;
    }

    public int getVibrate(){
        return vibrate;
    }

    public int getSound(){
        return sound;
    }

    public void setMessage(String _message){
        message = _message;
    }

    public void setSubtitle(String _subtitle){
        subtitle = _subtitle;
    }

    public void setTitle(String _title){
        title = _title;
    }

    public void setSound(int _sound){
        sound = _sound;
    }

    public void setVibrate(int _vibrate){
        vibrate = _vibrate;
    }

    public void setTickerText(String _tickerText){
        tickerText = _tickerText;
    }
}
