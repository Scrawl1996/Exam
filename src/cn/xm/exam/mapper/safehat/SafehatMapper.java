package cn.xm.exam.mapper.safehat;

import cn.xm.exam.bean.safehat.Safehat;
import cn.xm.exam.bean.safehat.SafehatExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SafehatMapper {
    int countByExample(SafehatExample example);

    int deleteByExample(SafehatExample example);

    int deleteByPrimaryKey(String id);

    int insert(Safehat record);

    int insertSelective(Safehat record);

    List<Safehat> selectByExampleWithBLOBs(SafehatExample example);

    List<Safehat> selectByExample(SafehatExample example);

    Safehat selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Safehat record, @Param("example") SafehatExample example);

    int updateByExampleWithBLOBs(@Param("record") Safehat record, @Param("example") SafehatExample example);

    int updateByExample(@Param("record") Safehat record, @Param("example") SafehatExample example);

    int updateByPrimaryKeySelective(Safehat record);

    int updateByPrimaryKeyWithBLOBs(Safehat record);

    int updateByPrimaryKey(Safehat record);
}