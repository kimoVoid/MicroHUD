package me.kimovoid.microhud.info;

import java.text.SimpleDateFormat;
import java.util.Date;

public class InfoCurrentTime extends InfoLine {

    public InfoCurrentTime(int order) {
        super(order);
    }

    @Override
    public String getLineString() {
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
        return date.format(new Date(System.currentTimeMillis()));
    }
}