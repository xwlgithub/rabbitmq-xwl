package com.xwl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xwl.entity.OrderInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface OrderMapper extends BaseMapper<OrderInfo> {


    @Select("select *  from order_info where order_code=#{orderCode}")
    public OrderInfo findOrderInfoByOrderCode(@Param("orderCode")String orderCode);

    @Select("delete from order_info where order_code=#{id}")
    void deleteByOrderCode(@Param("id") String id);
}
