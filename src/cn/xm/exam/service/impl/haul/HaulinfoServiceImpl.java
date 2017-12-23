package cn.xm.exam.service.impl.haul;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.xm.exam.bean.exam.Exam;
import cn.xm.exam.bean.exam.ExamExample;
import cn.xm.exam.bean.haul.Haulinfo;
import cn.xm.exam.mapper.exam.ExamMapper;
import cn.xm.exam.mapper.haul.HaulinfoMapper;
import cn.xm.exam.mapper.haul.custom.HaulinfoCustomMapper;
import cn.xm.exam.service.employee.out.UnitService;
import cn.xm.exam.service.exam.exam.ExamService;
import cn.xm.exam.service.haul.HaulinfoService;
import cn.xm.exam.utils.PageBean;
import cn.xm.exam.utils.UUIDUtil;
import cn.xm.exam.utils.ValidateCheck;

@Service
public class HaulinfoServiceImpl implements HaulinfoService {

	@Autowired
	private HaulinfoMapper haulinfoMapper;// 大修mapper
	@Autowired
	private HaulinfoCustomMapper haulinfoCustomMapper;// 大修mapper
	@Resource
	private ExamMapper examMapper;// 考试mapper
	@Resource
	private ExamService examService;// 考试mapper
	@Autowired
	private UnitService unitService;

	@Override
	public boolean addHaulinfo(Haulinfo haulInfo) throws SQLException {
		if (ValidateCheck.isNull(haulInfo.getBigid())) {// 设置ID
			haulInfo.setBigid(UUIDUtil.getUUID2());
		}
		haulInfo.setBigcreatedate(new Date());// 设置创建日期
		haulInfo.setBigstatus("未开始");// 设置状态
		return haulinfoMapper.insert(haulInfo) > 0 ? true : false;
	}

	@Override
	public boolean deleteHaulinfoByHaulId(String haulId) throws Exception {
		// 1.根据大修ID查出部门ID
		List<String> unitIds = this.getHaulUnitByHaulid(haulId);
		// 2.根据大修ID与部门ID删除大修部门(循环删除)
		if (unitIds != null && unitIds.size() > 0) {
			Map<String, Object> bigidAndUnitid = null;
			for (int i = 0, length_1 = unitIds.size(); i < length_1; i++) {
				bigidAndUnitid = new HashMap<String, Object>();
				bigidAndUnitid.put("bigId", haulId);
				bigidAndUnitid.put("unitId", unitIds.get(i));
				unitService.deleteUnitByBigIdAndHaulId(bigidAndUnitid);// 删除部门
			}
		}
		// 3.根据大修ID删除考试信息
		ExamExample examExample = new ExamExample();
		ExamExample.Criteria criteria = examExample.createCriteria();
		criteria.andBigidEqualTo(haulId);
		List<Exam> exams = examMapper.selectByExample(examExample);// 查出所有的考试
		for (int i = 0; exams != null && i < exams.size(); i++) {
			examService.deleteExamById(exams.get(i).getExamid());// 循环删除考试
		}
		// 4.删除本条大修记录
		return haulinfoMapper.deleteByPrimaryKey(haulId) > 0 ? true : false;
	}

	@Override
	public Haulinfo getHaulinfoByHaulId(String haulId) throws SQLException {
		return haulinfoMapper.selectByPrimaryKey(haulId);
	}

	@Override
	public boolean updateHaulinfoById(Haulinfo haulinfo) throws SQLException {
		// 修改的影响行数大于零说明修改成功
		return haulinfoMapper.updateByPrimaryKeySelective(haulinfo) > 0 ? true : false;
	}

	@Override
	public PageBean<Haulinfo> getHaulinfoPageByCondition(int currentPage, int currentCount,
			Map<String, Object> condition) throws SQLException {
		PageBean<Haulinfo> pageBean = new PageBean<Haulinfo>();
		pageBean.setCurrentCount(currentCount);// 设置页大小
		pageBean.setCurrentPage(currentPage);// 设置当前页
		int total = 0;
		int totalCount = haulinfoCustomMapper.getHaulinfoTotalByCondition(condition);// 查询满足条件的总数
		pageBean.setTotalCount(totalCount);// 总记录数
		int totalPage = (int) Math.ceil(1.0 * totalCount / currentCount);
		pageBean.setTotalPage(totalPage);// 总页数

		/******
		 * 计算起始值 页数 起始值 页大小 1 0 8 2 8 16
		 *******/
		int index = (currentPage - 1) * currentCount;// 起始值
		condition.put("index", index);
		condition.put("currentCount", currentCount);
		List<Haulinfo> haulinfos = haulinfoCustomMapper.getHaulinfoslByCondition(condition);
		pageBean.setProductList(haulinfos);
		return pageBean;
	}

	@Override
	public Map<String, Object> getHaulinfoWithUnitInfo(String haulId) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> getDepartmentAndOverHaulTree() throws SQLException {
		List<Map<String, Object>> departInfoTree = (List<Map<String, Object>>) haulinfoCustomMapper.getDepartInfoTree();
		List<Map<String, Object>> overHaulInfoTree = (List<Map<String, Object>>) haulinfoCustomMapper
				.getOverHaulInfoTree();
		departInfoTree.addAll(overHaulInfoTree);
		return departInfoTree;
	}

	@Override
	public List<String> getHaulUnitByHaulid(String haulId) throws SQLException {
		return haulinfoCustomMapper.getHaulUnitByHaulid(haulId);
	}

	@Override
	public List<Map<String, Object>> getHaulNameAndIdsForExam() throws SQLException {
		return haulinfoCustomMapper.getHaulNameAndIdsForExam();
	}

}
