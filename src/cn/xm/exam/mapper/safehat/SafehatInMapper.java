package cn.xm.exam.mapper.safehat;

import cn.xm.exam.bean.safehat.SafehatIn;
import cn.xm.exam.bean.safehat.SafehatInExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SafehatInMapper {
    int countByExample(SafehatInExample example);

    int deleteByExample(SafehatInExample example);

    int deleteByPrimaryKey(String id);

    int insert(SafehatIn record);

    int insertSelective(SafehatIn record);

    List<SafehatIn> selectByExampleWithBLOBs(SafehatInExample example);

    List<SafehatIn> selectByExample(SafehatInExample example);

    SafehatIn selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") SafehatIn record, @Param("example") SafehatInExample example);

    int updateByExampleWithBLOBs(@Param("record") SafehatIn record, @Param("example") SafehatInExample example);

    int updateByExample(@Param("record") SafehatIn record, @Param("example") SafehatInExample example);

    int updateByPrimaryKeySelective(SafehatIn record);

    int updateByPrimaryKeyWithBLOBs(SafehatIn record);

    int updateByPrimaryKey(SafehatIn record);
}