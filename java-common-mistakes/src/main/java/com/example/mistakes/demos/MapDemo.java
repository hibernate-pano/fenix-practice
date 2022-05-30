package com.example.mistakes.demos;

import java.util.HashMap;
import java.util.Map;

/**
 * @author pano
 * @date 2022/5/25
 **/
public class MapDemo {

    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> sub = new HashMap<>();
        sub.put("111", 111);
        map.put("111", sub);
        sub.put("111", 222);
        map.put("222", sub);
        sub.put("111", 333);
        System.out.println(map);
    }

}
