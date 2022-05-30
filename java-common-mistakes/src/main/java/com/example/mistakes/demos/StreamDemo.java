package com.example.mistakes.demos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author pano
 * @date 2022/5/25
 **/
public class StreamDemo {

    public static void main(String[] args) {
        StreamItem item1 = new StreamItem(1, "张三");
        StreamItem item2 = new StreamItem(2, "李四");
        StreamItem item3 = new StreamItem(3, "王五");
        StreamItem item4 = new StreamItem(3, "孙六");
        List<StreamItem> list = List.of(item1, item2, item3, item4);

        int size = list.stream().collect(Collectors.groupingBy(StreamItem::getType)).size();
        System.out.println(size);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class StreamItem {
        int type;
        String name;
    }
}
