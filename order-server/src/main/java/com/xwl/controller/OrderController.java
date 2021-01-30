package com.xwl.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Auther: 薛
 * @Date: 2021/1/30 16:18
 * @Description:
 */
@RestController
@RequestMapping("xwl")
@AllArgsConstructor
public class OrderController {
//    class User{
//        private String name;
//        private String value;
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        public String getValue() {
//            return value;
//        }
//
//        public void setValue(String value) {
//            this.value = value;
//        }
//    }
//    public static void main(String[] args) {
//        List<User> userList=new LinkedList<>();
//        List<Map<String,Object>> objects = new LinkedList<>();
//        Map<String, Object> maps = userList.stream().collect(Collectors.toMap(user -> user.getName(), userTwo -> userTwo.getValue()));
//        objects.add(maps);
//    }
}
