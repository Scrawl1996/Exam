package cn.xm.exam.action.safeHat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.sun.org.apache.bcel.internal.generic.NEW;

import cn.xm.exam.bean.system.User;
import cn.xm.exam.service.safehat.EmpInSafehatService;

@Controller
@Scope("prototype")
public class ImportSafeHatAction {
	// 文件上传的相关属性
	private File file;
	private Map<String, Object> response = new HashMap<>();
	private static final Logger log = LoggerFactory.getLogger(ImportSafeHatAction.class);
	private List safehatNums = new ArrayList<>();
	private List idCards = new ArrayList<>();

	@Autowired
	private EmpInSafehatService empInSafehatService;

	public String execute() {
		String message = "";
		try {
			extDataFromExcel(file);
			User user = (User) ServletActionContext.getRequest().getSession().getAttribute("userinfo");
			if (CollectionUtils.isNotEmpty(idCards) && CollectionUtils.isNotEmpty(safehatNums)) {
				empInSafehatService.saveSafeHatNums(safehatNums, idCards, user);
			}
			message = "导入成功";
		} catch (Exception exception) {
			log.error("导入失败", exception);
			message = "导入失败";
		}

		response.put("message", message);
		return "json";
	}

	private void extDataFromExcel(File file2) {
		// 1.创建WorkBook
		Workbook workbook = null;
		try {
			workbook = new XSSFWorkbook(file2);
		} catch (Exception e) {
			log.error("", e);
			try {
				workbook = new HSSFWorkbook(new FileInputStream(file2));
			} catch (IOException e1) {
				log.error("HSSFWorkbook error", e);
				throw new RuntimeException("文件格式不正确");
			}
		}

		// 标记行号
		int i = 0;
		// 获取工作表的第二种方式
		Sheet sheet = workbook.getSheetAt(0);

		for (Iterator<Row> iterator = sheet.rowIterator(); iterator.hasNext();) {
			Row row = iterator.next();
			if (row == null) {
				continue;
			}

			i++;
			if (i <= 1) {
				log.debug("跳过第一行,说明行!");
				continue;
			}

			String idCard = getCellStringValue(row.getCell(0));
			idCards.add(idCard);
			String safehatNum = getCellStringValue(row.getCell(1));
			safehatNums.add(safehatNum);
			log.debug("idCards ->{},safehatNum->{}", idCard, safehatNum);
		}
	}

	private String getCellStringValue(Cell cell) {
		if (cell == null) {
			return "";
		} else {
			cell.setCellType(Cell.CELL_TYPE_STRING);
			return cell.getStringCellValue();
		}
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public Map<String, Object> getResponse() {
		return response;
	}

	public void setResponse(Map<String, Object> response) {
		this.response = response;
	}
}
