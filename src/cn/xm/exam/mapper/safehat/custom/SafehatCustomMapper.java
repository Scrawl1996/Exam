package cn.xm.exam.mapper.safehat.custom;

import java.util.List;
import java.util.Map;

public interface SafehatCustomMapper {

	List<Map<String, Object>> getSafehatTaizhang(Map condition);
}