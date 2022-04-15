package com.hs.blog.utils;

import java.util.Random;
import java.util.UUID;

public class ImageUtils {

    public String genImageName(){
        // current time(in millis) + first eight digits of a random UUID

        long millis = System.currentTimeMillis();

        // eight digits of a random UUID
        String str = UUID.randomUUID().toString().substring(0, 8);
        // combine together
        String finalName = millis + str;

        return finalName;

    }
}
