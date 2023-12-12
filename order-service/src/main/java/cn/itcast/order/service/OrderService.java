package cn.itcast.order.service;


import cn.itcast.order.mapper.OrderMapper;
import cn.itcast.order.pojo.Order;

import com.itcast.feign.clients.UserClient;
import com.itcast.feign.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Resource
    private UserClient userClient;

    public Order queryOrderById(Long orderId) {
        // 1.查询订单
        Order order = orderMapper.findById(orderId);
        //2.用Feign远程调用
        User user = userClient.findById(order.getUserId());
        //封装
        order.setUser(user);
        // 4.返回
        return order;
    }


    /*
    @Autowired
    private RestTemplate restTemplate;
    public Order queryOrderById(Long orderId) {
        // 1.查询订单
        Order order = orderMapper.findById(orderId);
        //2.利用RestTemplate发起http请求
        String url="http://UserServer/user/"+ order.getUserId();
        //发送请求
        User user = restTemplate.getForObject(url, User.class);
        //封装
        order.setUser(user);
        // 4.返回
        return order;
    }
    */

}
