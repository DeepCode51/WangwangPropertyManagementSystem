package com.kum.mapper;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kum.domain.entity.SysUserPlayRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @version V1.0
 * @Package com.kum.mapper
 * @auhter 枯木Kum
 * @date 2021/3/21-7:55 PM
 */

@Mapper
public interface SysUserPlayRecordMapper  extends BaseMapper<SysUserPlayRecord> {


    @Select("SELECT * FROM sys_user_play_record WHERE user_id = #{userId} AND charge_type_id = #{chargeTypeId} AND MONTH(create_time) = #{currentMonth}")
    public List<JSONObject> findByChargeTypeIdAndNowMonth(@Param("userId") String userId,@Param("chargeTypeId") Integer chargeTypeId, @Param("currentMonth") Integer currentMonth);

}
