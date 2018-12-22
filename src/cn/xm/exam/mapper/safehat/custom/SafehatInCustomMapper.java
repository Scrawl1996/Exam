package cn.xm.exam.mapper.safehat.custom;

import java.util.List;
import java.util.Map;

public interface SafehatInCustomMapper {

	List<Map<String, Object>> getSafehatTaizhang(Map condition);
}