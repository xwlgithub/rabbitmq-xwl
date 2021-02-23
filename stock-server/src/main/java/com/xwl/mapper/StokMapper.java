package com.xwl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xwl.entity.StokOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @Auther: 薛
 * @Date: 2021/2/19 17:31
 * @Description:
 */
@Mapper
public interface StokMapper extends BaseMapper<StokOrder> {

    @Select("select com_modity  from stok_order where com_modity=#{orderCode}")
    String getOrderCodeByCode(@Param("orderCode")String orderCode);
}
