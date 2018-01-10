package cn.xm.exam.service.impl.employee.out;

import java.sql.SQLException;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.xm.exam.bean.employee.out.BlackUnit;
import cn.xm.exam.mapper.employee.out.BlackUnitMapper;
import cn.xm.exam.service.employee.out.BlackUnitService;
import cn.xm.exam.utils.PageBean;
/**
 * 黑名单单位服务
 * @author QiaoLiQiang
 * @time 2018年1月10日下午2:20:52
 */
@Service
public class BlacklistServiceImpl implements BlackUnitService {
	@Resource
	private BlackUnitMapper blackUnitMapper;//导出的mapper
	@Override
	public boolean addBlackUnit(BlackUnit blackUnit) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteBlackUnitByBlackId(int id) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateBlackUnitByBlackId(BlackUnit blackUnit) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public PageBean<BlackUnit> getBlackUnitPage(int currentPage, int currentCount) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
