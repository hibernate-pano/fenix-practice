package com.example.mistakes.demos;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 这个错误示例充分说明了在线程池中线程是复用的
 *
 * @author pano
 * @date 2022/5/7
 **/
@RestController
@RequestMapping("thread_local")
public class ThreadLocalDemo {

    private static final ThreadLocal<Integer> CURRENT_USER = ThreadLocal.withInitial(() -> null);

    @GetMapping("wrong")
    public Map<String, Object> wrong(@RequestParam("userId") Integer userId) {
        //设置用户信息之前先查询一次ThreadLocal中的用户信息
        String before = Thread.currentThread().getName() + ":" + CURRENT_USER.get();
        //设置用户信息到ThreadLocal
        CURRENT_USER.set(userId);

        try {
            //设置用户信息之后再查询一次ThreadLocal中的用户信息
            String after = Thread.currentThread().getName() + ":" + CURRENT_USER.get();
            //汇总输出两次查询结果
            Map<String, Object> result = new HashMap<>(2);
            result.put("before", before);
            result.put("after", after);
            return result;
        } finally {
            // 使用finally显式的清空ThreadLocal中的数据，避免复用。
            CURRENT_USER.remove();
        }
    }

}
