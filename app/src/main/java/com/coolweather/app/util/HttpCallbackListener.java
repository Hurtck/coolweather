package com.coolweather.app.util;

/**
 * Created by 47321 on 2016/9/27 0027.
 */

public interface HttpCallbackListener {
    void Finsh(String response);

    void Error(Exception e);
}
