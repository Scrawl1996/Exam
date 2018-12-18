package cn.xm.exam.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DynamicOperateExcelUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(DynamicOperateExcelUtils.class);

	private Workbook workBook;
	private Sheet sheet;

	public DynamicOperateExcelUtils(String fileFullPath, String sheetName) {
		// 解决版本问题，HSSFWorkbook是97-03版本的xls版本，XSSFWorkbook是07版本的xlsx
		try {
			workBook = new XSSFWorkbook(new FileInputStream(fileFullPath));
		} catch (Exception e) {
			try {
				workBook = new HSSFWorkbook(new FileInputStream(fileFullPath));
			} catch (Exception e1) {
				LOGGER.error("Excel格式不正确", e1);
			}
		}
		// 进行模板的克隆(接下来的操作都是针对克隆后的sheet)
		sheet = workBook.cloneSheet(0);
		// 移除workbook中的模板sheet
		workBook.removeSheetAt(0);
		// 重命名克隆后的sheet
		workBook.setSheetName(0, sheetName != null ? sheetName : "sheet1");
	}

	/**
	 * 读取cell的值
	 * 
	 * @param cell
	 *            需要读取的cell
	 * @param defaultValue
	 *            默认值
	 * @return
	 */
	public String getCellStringValue(Cell cell, String defaultValue) {
		if (cell != null) {
			cell.setCellType(cell.CELL_TYPE_STRING);
			return cell.getStringCellValue();
		}
		return defaultValue;
	}

	/**
	 * 替换单元格的内容，单元格的获取位置是合并单元格之前的位置，也就是下标都是合并之前的下表
	 * 
	 * @param cell
	 *            单元格
	 * @param value
	 *            需要设置的值
	 */
	public static void replaceCellValue(Cell cell, Object value) {
		String val = value != null ? String.valueOf(value) : "";
		cell.setCellValue(val);
	}

	/**
	 * 向sheet中添加行，后面的行会向后自动移动
	 * 
	 * @param startRowIndex
	 *            起始行
	 * @param datas
	 *            数据
	 * @param keys
	 *            数据中Map对应的key
	 */
	public void appendRows(int startRowIndex, List<Map<String, Object>> datas, String[] keys) {
		// 插入行
		sheet.shiftRows(startRowIndex, startRowIndex + datas.size(), datas.size(), true, false);// 第1个参数是指要开始插入的行，第2个参数是结尾行数,第三个参数表示动态添加的行数
		// 向插入的行中动态的填充数据
		for (int i = 0; i < datas.size(); i++) {
			Map<String, Object> data = datas.get(i);
			// 创建行
			Row row = sheet.createRow(startRowIndex + i);
			// 添加单元格
			Cell cell = null;
			for (int j = 0, length_2 = keys.length; j < length_2; j++) {
				String key = keys[j];
				String value = MapUtils.getString(data, key, "");
				cell = row.createCell(j);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cell.setCellValue(value);
			}
		}
	}

	/**
	 * 将数据写出到excel中
	 * 
	 * @param outputStream
	 */
	public void exportExcel(String fileFullPath) {
		// 导出之前先自动设置列宽
		OutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(fileFullPath);
			workBook.write(outputStream);
		} catch (IOException e) {
			LOGGER.error(" exportExcel error", e);
		} finally {
			IOUtils.closeQuietly(outputStream);
		}
	}

	public Sheet getSheet() {
		return sheet;
	}

	public static void main(String[] args) throws IOException {
		DynamicOperateExcelUtils dynamicOperateExcelUtils = new DynamicOperateExcelUtils("G:/test.xls", null);
		// 读取指定cell的内容
		Cell nameCell = dynamicOperateExcelUtils.getSheet().getRow(1).getCell(0);
		System.out.println(nameCell.getStringCellValue());

		// 替换单元格内容(注意获取的cell的下标是合并之前的下标)
		dynamicOperateExcelUtils.replaceCellValue(dynamicOperateExcelUtils.getSheet().getRow(1).getCell(2), "xxxxx时间");
		dynamicOperateExcelUtils.replaceCellValue(dynamicOperateExcelUtils.getSheet().getRow(2).getCell(2), "xxxxx人");

		// 动态插入数据-增加行
		List<Map<String, Object>> datas = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			Map data = new HashMap<>();
			data.put("name", "name" + i);
			data.put("age", "age" + i);
			data.put("sex", "sex" + i);
			datas.add(data);
		}
		dynamicOperateExcelUtils.appendRows(4, datas, new String[] { "name", "age", "sex" });
		dynamicOperateExcelUtils.exportExcel("G:/ttt.xls");
	}
}
