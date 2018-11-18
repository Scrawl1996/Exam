package cn.xm.exam.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HSSFWorkExcel {

	private static final Logger LOGGER = LoggerFactory.getLogger(HSSFWorkExcel.class);

	private String[] headerNames;
	private HSSFWorkbook workBook;
	private HSSFSheet sheet;

	public HSSFWorkExcel(String[] headerNames, String sheetName) {
		this.headerNames = headerNames;
		// 创建一个工作簿
		workBook = new HSSFWorkbook();
		// 创建一个工作表sheet
		sheet = workBook.createSheet(sheetName);
		initHeader();
	}

	/**
	 * 初始化表头信息
	 */
	private void initHeader() {
		// 创建第一行
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = null;
		// 创建表头
		for (int i = 0; i < headerNames.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(headerNames[i]);
			setCellStyle(cell);
		}
	}

	/**
	 * 设置单元格样式
	 * 
	 * @param cell
	 *            单元格
	 */
	public void setCellStyle(HSSFCell cell) {
		// 设置样式
		HSSFCellStyle cellStyle = workBook.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 设置字体居中
		// 设置字体
		HSSFFont font = workBook.createFont();
		font.setFontName("宋体");
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 字体加粗
		font.setFontHeightInPoints((short) 13);

		cellStyle.setFont(font);
		cell.setCellStyle(cellStyle);
	}

	/**
	 * 创建行内容(每一行的数据装在list中)
	 * 
	 * @param datas
	 *            每一行的数据
	 * @param rowIndex
	 *            行号(从1开始)
	 */
	public void createTableRow(List<String> datas, int rowIndex) {
		// 创建第i行
		HSSFRow row = sheet.createRow(rowIndex);
		HSSFCell cell = null;
		// 写入数据
		for (int index = 0, length = datas.size(); index < length; index++) {
			// 参数代表第几列
			cell = row.createCell(index);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(datas.get(index));
		}
	}

	/**
	 * 
	 * @param datas
	 *            数据,每一个map都是一行
	 * @param keys
	 *            key[i]代表从map中获取keys[i]的值作为第i列的值,如果传的是null默认取表头
	 */
	public void createTableRows(List<Map<String, Object>> datas, String[] keys) {
		for (int i = 0, length_1 = datas.size(); i < length_1; i++) {
			if (ArrayUtils.isEmpty(keys)) {
				keys = headerNames;
			}
			// 创建行(从第二行开始)
			Map<String, Object> data = datas.get(i);
			HSSFRow row = sheet.createRow(i + 1);
			HSSFCell cell = null;
			for (int j = 0, length_2 = keys.length; j < length_2; j++) {
				// 单元格获取map中的key
				String key = keys[j];
				String value = MapUtils.getString(data, key, "");

				cell = row.createCell(j);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(value);
			}

		}
	}

	/**
	 * 根据表头自动调整列宽度
	 */
	public void autoAllSizeColumn() {
		for (int i = 0, length = headerNames.length; i < length; i++) {
			sheet.autoSizeColumn(i);
		}
	}

	/**
	 * 将数据写出到excel中
	 * 
	 * @param outputStream
	 */
	public void exportExcel(OutputStream outputStream) {
		// 导出之前先自动设置列宽
		this.autoAllSizeColumn();
		try {
			workBook.write(outputStream);
		} catch (IOException e) {
			LOGGER.error(" exportExcel error", e);
		} finally {
			IOUtils.closeQuietly(outputStream);
		}
	}

	public static void main(String[] args) {
		test2();
	}

	public static void test1() {
		HSSFWorkExcel hssfWorkExcel = new HSSFWorkExcel(new String[] { "姓名", "年龄" }, "人员基本信息");
		for (int i = 0; i < 10; i++) {
			List<String> data = new ArrayList<>();
			data.add("namesssssssssssssss水水水水水水水水水水水水水水水水水水水ssssssssssssssss" + i);
			data.add("" + (i + 20));
			hssfWorkExcel.createTableRow(data, i + 1);
		}
		try {
			hssfWorkExcel.exportExcel(new FileOutputStream(new File("e:/test.xls")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void test2() {
		HSSFWorkExcel hssfWorkExcel = new HSSFWorkExcel(new String[] { "姓名", "年龄" }, "人员基本信息");
		List<Map<String, Object>> datas = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			Map data = new HashMap<>();
			data.put("name", "tttttttttttttt" + i);
			data.put("age", "age" + i);
			datas.add(data);
		}
		hssfWorkExcel.createTableRows(datas, new String[] { "name", "age" });
		try {
			hssfWorkExcel.exportExcel(new FileOutputStream(new File("e:/test1.xls")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
