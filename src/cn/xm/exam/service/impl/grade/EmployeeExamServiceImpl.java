package cn.xm.exam.service.impl.grade;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.xm.exam.bean.exam.Exam;
import cn.xm.exam.bean.grade.Employeeexam;
import cn.xm.exam.bean.grade.EmployeeexamExample;
import cn.xm.exam.mapper.exam.ExamMapper;
import cn.xm.exam.mapper.grade.EmployeeexamMapper;
import cn.xm.exam.mapper.grade.custom.EmployeeexamCustomMapper;
import cn.xm.exam.service.grade.EmployeeExamService;
import cn.xm.exam.utils.PageBean;
import cn.xm.exam.vo.grade.EmployeeExamGrade;
import cn.xm.exam.vo.grade.ExamEmployeeexamExampaper;
import cn.xm.exam.vo.grade.OnlineExamEmployeeInfo;

@Service
public class EmployeeExamServiceImpl implements EmployeeExamService {
	@Autowired
	private EmployeeexamCustomMapper employeeexamCustomMapper;
	@Resource
	private ExamMapper examMapper;
	@Resource
	private EmployeeexamMapper employeeexamMapper;
	@Resource
	private EmployeeexamMapper employeeExamMapper;

	@Override
	public boolean addEmployeGrade(Employeeexam employeeOutGrades) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 批量加入外部员工的成绩,并批量设置线下考试的培训状态
	 * 
	 * @param mployeeOutGrades
	 *            外部员工的基本信息
	 * @return 影响行数
	 * @throws Exception
	 */
	public int addEmployeeOutGradeBatch(List<Employeeexam> employeeOutGrades) throws Exception {
		// 批量导入员工的成绩
		int count = employeeexamCustomMapper.insertEmployeeGradeBantch(employeeOutGrades);
		// 根据考试ID查询通过这次考试的员工身份证号集合和大修ID
		String examId = employeeOutGrades.get(0).getExamid();
		List<EmployeeExamGrade> employeeOutExamInfoList = employeeexamCustomMapper
				.getEmployeeIdCardsAndBigIdByExamId(examId);
		// 判断通过考试的集合中是否有值
		if (employeeOutExamInfoList != null && employeeOutExamInfoList.size() > 0) {
			String bigId = employeeOutExamInfoList.get(0).getBigid();
			String examLevel = employeeOutExamInfoList.get(0).getLevel();
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("examLevel", examLevel);
			condition.put("bigId", bigId);
			condition.put("employeeOutExamInfoList", employeeOutExamInfoList);
			employeeexamCustomMapper.updateEmployeeOutTrainStatus(condition);
		}
		return count;
	}

	@Override
	public boolean deleteEmployeeInByExamIdBatch(String examId, List<String> employeeInId) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean addEmployeeInGradeOnlineBatch(List<Map<String, Object>> employeeInGrades) throws Exception {

		return false;
	}

	@Override
	public boolean deleteEmployeeGradeById(Integer employeeExamId) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 批量删除外部员工考试成绩（根据考试ID）
	 * 
	 * @param ExamId
	 *            需要删除的考试id
	 * @return
	 * @throws Exception
	 */
	public boolean deleteEmployeeOutGradeBatch(String examId) throws Exception {
		int count = employeeexamCustomMapper.deleteEmployeeOutGradeBantch(examId);
		return count > 0 ? true : false;
	}

	/**
	 * 组合条件查询 按照考试名称，考试等级，考试时间，员工的相关信息查询员工成绩的相关信息
	 * 
	 * @param currentPage
	 * @param currentTotal
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public PageBean<EmployeeExamGrade> findExamGradesWithCondition(int currentPage, int currentTotal,
			Map<String, Object> condition) throws Exception {
		PageBean<EmployeeExamGrade> pageBean = new PageBean<EmployeeExamGrade>();
		pageBean.setCurrentPage(currentPage);
		pageBean.setCurrentCount(currentTotal);
		int totalCount = 0;
		totalCount = employeeexamCustomMapper.getEmployeeGradesInfoCountWithCondition(condition);
		pageBean.setTotalCount(totalCount);
		// 总页数
		int totalPage = (int) Math.ceil(1.0 * totalCount / currentTotal);
		pageBean.setTotalPage(totalPage);
		// 每页显示的数据
		// 设置索引，当前页-1乘上当前页显示的条数
		int index = (currentPage - 1) * currentTotal;
		condition.put("index", index);
		condition.put("currentCount", currentTotal);
		List<EmployeeExamGrade> list = employeeexamCustomMapper.getEmployeeGradesInfoWithCondition(condition);
		pageBean.setProductList(list);
		return pageBean;
	}

	/**
	 * 根据考试编号与员工Id查询在线考试员工的成绩信息
	 * 
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public OnlineExamEmployeeInfo getExamGardeByEmployeeIdAndExamId(Map<String, Object> condition) throws Exception {
		return employeeexamCustomMapper.getExamGardeByEmployeeIdAndExamId(condition);
	}

	/**
	 * 组合条件查询 根据考试名称，考试级别，考试时间查询考试的相关信息
	 * 
	 * @param currentPage
	 * @param currentTotal
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public PageBean<ExamEmployeeexamExampaper> getExamGradesInfoWithCondition(int currentPage, int currentTotal,
			Map<String, Object> condition) throws Exception {
		PageBean<ExamEmployeeexamExampaper> pageBean = new PageBean<ExamEmployeeexamExampaper>();
		pageBean.setCurrentPage(currentPage);
		pageBean.setCurrentCount(currentTotal);
		int totalCount = 0;
		totalCount = employeeexamCustomMapper.getExamGradesInfoCountWithCondition(condition);
		pageBean.setTotalCount(totalCount);
		// 总页数
		int totalPage = (int) Math.ceil(1.0 * totalCount / currentTotal);
		pageBean.setTotalPage(totalPage);
		// 每页显示的数据
		// 设置索引，当前页-1乘上当前页显示的条数
		int index = (currentPage - 1) * currentTotal;
		condition.put("index", index);
		condition.put("currentCount", currentTotal);
		List<ExamEmployeeexamExampaper> list = employeeexamCustomMapper.getExamGradesInfoWithCondition(condition);
		pageBean.setProductList(list);
		return pageBean;
	}

	/**
	 * 根据条件对考试成绩进行分析，查询优良差的人数
	 * 
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getExamGradeAnalyseInfoByCondition(Map<String, Object> condition) throws Exception {
		return employeeexamCustomMapper.getExamAnalyseInfoByCondition(condition);
	}

	/**
	 * 根据考试ID查询员工的考试成绩信息，用于导出成绩单
	 * 
	 * @param examId
	 * @return
	 * @throws Exception
	 */
	public List<EmployeeExamGrade> getEmployeeGradeByExamId(String examId) throws Exception {

		return employeeexamCustomMapper.getEmployeeGradeByExamId(examId);
	}

	/**
	 * 根据组合条件查询员工的成绩信息，用于导出员工成绩
	 * 
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public List<EmployeeExamGrade> findEmployeeGradeByCondition(Map<String, Object> condition) throws Exception {

		return employeeexamCustomMapper.findEmployeeGradesInfoByCondition(condition);
	}

	/**
	 * 根据考试ID查询线下考试的员工信息,获取员工的考号和姓名用于对比
	 * 
	 * @param examId
	 * @return
	 * @throws Exception
	 */
	public List<Employeeexam> getEmployeeOutInfoByExamId(String examId) throws Exception {
		EmployeeexamExample employeeExamGradeExample = new EmployeeexamExample();
		EmployeeexamExample.Criteria criteria = employeeExamGradeExample.createCriteria();
		criteria.andExamidEqualTo(examId);
		criteria.andExammethodEqualTo("线下");
		employeeExamGradeExample.setOrderByClause("employeeid asc");
		List<Employeeexam> employeeOutInfo = employeeExamMapper.selectByExample(employeeExamGradeExample);
		return employeeOutInfo;
	}

	/**
	 * 根据考试ID，试卷ID和员工身份证号修改内部员工在线考试的成绩信息
	 * 
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public boolean updateEmployeeInScoreByIdCard(Map<String, Object> condition) throws Exception {
		int isUpdate = employeeexamCustomMapper.updateEmployeeInScoreByIdCard(condition);
		return isUpdate > 0 ? true : false;
	}

	/********* S qlq *************/
	@Override
	public int addEmployeeExam(List<Employeeexam> employeeexam) throws SQLException {
		// TODO Auto-generated method stub
		return employeeexamCustomMapper.addEmployeeExam(employeeexam);
	}

	@Override
	public List<Map<String, Object>> getEmployeeexamsByExamId(String examId) throws SQLException {
		// 1.获取考试
		Exam exam = examMapper.selectByPrimaryKey(examId);
		// 2.根据考试类型查询对应的考试员工(内部查询内部员工，外部查询外部员工)
		String examType = exam.getExamtype();
		// 查询内部参考人员
		if (examType != null && "内部考试".equals(examType)) {
			return employeeexamCustomMapper.getEmployeeexamsInByExamId(examId);
		}
		// 2.查询外部参考人员
		if (examType != null && "外部考试".equals(examType)) {
			return employeeexamCustomMapper.getEmployeeexamsOutByExamId(examId);
		}
		return null;
	}
	/********* E qlq *************/

}
