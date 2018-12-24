package cn.xm.exam.action.safeHat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

import cn.xm.exam.service.safehat.EmpInSafehatService;
import cn.xm.exam.service.safehat.SafehatService;
import cn.xm.exam.utils.ExamSystemUtils;
import cn.xm.exam.utils.HSSFWorkExcel;
import cn.xm.exam.utils.ValidateCheck;

/**
 * 导出安全帽台账
 * 
 * @author Administrator
 *
 */
@Controller
public class ExtSafeHatInTaizhang extends ActionSupport {
	private static final Logger log = LoggerFactory.getLogger(ExtSafeHatInTaizhang.class);
	private String userName;
	private String idCard;
	private String safeHatNum;
	private String fileName;
	private String departName;
	@Autowired
	private EmpInSafehatService empInSafehatService;

	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}

	public InputStream getInputStream() {
		// 获取一个临时文件
		File file = ExamSystemUtils.getTmpFile();

		// 只用返回一个输入流
		try {
			// 查数据
			List<Map<String, Object>> safehatTaizhang = getSafehatTaizhang();

			// 写入文件中
			String[] headerNames = new String[] { "序号", "姓名", "性别", "年龄", "部门", "学历", "身体状况", "职务", "身份证号", "安全帽编号",
					"备注" };
			HSSFWorkExcel hssfWorkExcel = new HSSFWorkExcel(headerNames, "安全帽台账信息");
			String[] keys = new String[] { "index", "name", "sex", "age", "departmentName", "empeducate",
					"empphysicalstatus", "duty", "idCode", "safeHatNum", "description" };
			hssfWorkExcel.createTableRows(safehatTaizhang, keys);
			hssfWorkExcel.exportExcel(new FileOutputStream(file));

			// 返回流
			return FileUtils.openInputStream(file);

		} catch (IOException e) {
			log.error("getInputStream error", e);
		}
		return null;
	}

	public List<Map<String, Object>> getSafehatTaizhang() {
		Map condition = new HashMap<>();
		if (ValidateCheck.isNotNull(userName)) {
			condition.put("userName", userName);
		}
		if (ValidateCheck.isNotNull(idCard)) {
			condition.put("idCard", idCard);
		}
		if (ValidateCheck.isNotNull(safeHatNum)) {
			condition.put("safeHatNum", safeHatNum);
		}
		if (ValidateCheck.isNotNull(departName)) {
			condition.put("departName", departName);
		}
		// 增加排序列
		condition.put("orderColumn", "safehat_in.safeHatNum");

		List<Map<String, Object>> results = null;
		try {
			results = empInSafehatService.getSafehatTaizhang(condition);
		} catch (Exception e) {
			log.error("getSafehatTaizhang error", e);
			results = new ArrayList<>();
			return results;
		}
		if (CollectionUtils.isNotEmpty(results)) {
			disPoseResults(results);
		}

		return results;
	}

	private void disPoseResults(List<Map<String, Object>> results) {
		int index = 1;
		for (Map map : results) {
			// 装一个序号进去
			map.put("index", index++);

			// 处理性别
			if (StringUtils.isNotBlank(MapUtils.getString(map, "sex"))) {
				if ("1".equals(map.get("sex").toString())) {
					map.put("sex", "男");
				}
				if ("2".equals(map.get("sex").toString())) {
					map.put("sex", "女");
				}
			}

			// 处理年龄
			String birthday = MapUtils.getString(map, "birthday");
			if (StringUtils.isNotBlank(birthday)) {
				Date date = null;
				try {
					date = DateUtils.parseDateStrictly(birthday, "yyyy-MM-dd");
				} catch (ParseException e) {
					date = new Date();
				}
				int birhYear = date.getYear();
				int nowYear = (new Date()).getYear();
				map.put("age", (nowYear - birhYear) + 1);
			}
		}
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getSafeHatNum() {
		return safeHatNum;
	}

	public void setSafeHatNum(String safeHatNum) {
		this.safeHatNum = safeHatNum;
	}

	public String getFileName() {
		String name = "安全帽台账(内部)" + DateFormatUtils.format(new Date(), "yyyy-MM-dd") + ".xls";
		try {
			name = new String(name.getBytes("UTF-8"), "ISO8859-1");
		} catch (UnsupportedEncodingException e) {
			log.error("getFileName error", e);
			return DateFormatUtils.format(new Date(), "yyyy-MM-dd") + ".xls";
		}
		return name;
	}

	public String getDepartName() {
		return departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
	}
}
