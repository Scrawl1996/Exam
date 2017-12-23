package cn.xm.exam.action.employee.in;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

import cn.xm.exam.bean.employee.in.Department;
import cn.xm.exam.bean.employee.in.EmployeeIn;
import cn.xm.exam.service.employee.in.DepartmentService;
import cn.xm.exam.service.employee.in.EmployeeInService;
import cn.xm.exam.service.exam.exam.ExamService;
import cn.xm.exam.utils.ResourcesUtil;

@Controller
@Scope("prototype")
public class ExportExcelPaperAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		//查数据的身份证编号
		private String idcode;
		
		public String getIdcode() {
			return idcode;
		}

		public void setIdcode(String idcode) {
			this.idcode = idcode;
		}
		@Autowired
		private ExamService examService;
	    public ExamService getExamService() {
			return examService;
		}

		public void setExamService(ExamService examService) {
			this.examService = examService;
		}
		// 导出的Excel名称
		private String fileName;
		public String getFileName() {
			return fileName;
		}

		public void setFileName(String fileName) {
			this.fileName = fileName;
		}
		
		//得到员工信息
		@Autowired
		private EmployeeInService employeeInService;
		public EmployeeInService getEmployeeInService() {
			return employeeInService;
		}

		public void setEmployeeInService(EmployeeInService employeeInService) {
			this.employeeInService = employeeInService;
		}
		@Autowired
		private DepartmentService departmentService;
		public DepartmentService getDepartmentService() {
			return departmentService;
		}

		public void setDepartmentService(DepartmentService departmentService) {
			this.departmentService = departmentService;
		}

		//获取数据
		public List<Map<String, Object>> findExamEmployeeByExamId() throws Exception {
	        
	        
	        List<Map<String, Object>> myexam = examService.getExamInfoByEmployeeId(idcode);
	      
	        return myexam;
	    }

	    // 2.写入Excel
	    public boolean writeExamEmployees2Excel(List<Map<String, Object>> exams, String fileName) throws Exception {
	    
	    	EmployeeIn employeeIn = employeeInService.getEmployeeInByIdcode(idcode);
	    	System.out.println("employeeIn"+employeeIn);
	    	System.out.println("employeeIn.getSex()"+employeeIn.getSex());
	    	String sex = null;
        	if("1".equals(employeeIn.getSex())){
        		sex="男";
        	}else if("2".equals(employeeIn.getSex())){
        		sex="女";
        	}
        	System.out.println("sex"+sex);
	    	/*
	    	 *员工信息
	    	 */
	    	//标题
        	
        	
	    	String[] title1 = {"姓名","性别"};
	    	String[] title2 = {"联系方式","所属单位"};
	    	
	    	// 创建一个工作簿
			HSSFWorkbook workbook = new HSSFWorkbook();
			// 创建一个工作表sheet
			HSSFSheet sheet = workbook.createSheet();
			// 设置列宽
			setColumnWidth(sheet, 8);
			
			//创建第一行
			HSSFRow row0 = sheet.createRow(0);
			// 创建一个单元格
			HSSFCell cell0 = row0.createCell(2);;
			if(cell0!=null){
				
			
			 // 设置样式
            HSSFCellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 设置字体居中
            // 设置字体
            HSSFFont font = workbook.createFont();
            font.setFontName("宋体");
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 字体加粗
            // font.setFontHeight((short)12);
            font.setFontHeightInPoints((short) 20);
            cellStyle.setFont(font);
            cell0.setCellStyle(cellStyle);
				
            	//写入数据
	            cell0.setCellValue("员工详细信息");
	            
			}
			// 创建第二行
			HSSFRow row1 = sheet.createRow(1);
			// 创建一个单元格
			HSSFCell cell1 = null;

			
			
			
			for(int j=0,i=0;j<4;j=j+2,i++){
				cell1 = row1.createCell(j);
	            // 设置样式
	            HSSFCellStyle cellStyle = workbook.createCellStyle();
	            cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 设置字体居中
	            // 设置字体
	            HSSFFont font = workbook.createFont();
	            font.setFontName("宋体");
	            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 字体加粗
	            // font.setFontHeight((short)12);
	            font.setFontHeightInPoints((short) 13);
	            cellStyle.setFont(font);
	            cell1.setCellStyle(cellStyle);
					
	            	//写入数据
		            cell1.setCellValue(title1[i]);
		            System.out.println("cell1"+cell1);
				
	        }
			
			
			for(int j=1;j<4;j=j+2){
				cell1 = row1.createCell(j);
	            //设置数据
	            if(j==1){
	            	cell1.setCellValue(employeeIn.getName());
		           
	            }
	            if(j==3){
	            	
	    	        cell1.setCellValue(sex);
		            
	            }
				
				
	        }
			
			// 创建第三行
			HSSFRow row2 = sheet.createRow(2);
			// 创建一个单元格
			HSSFCell cell3 = null;
			for(int j=0,i=0;j<4;j=j+2,i++){
				cell3 = row2.createCell(j);
	            // 设置样式
	            HSSFCellStyle cellStyle = workbook.createCellStyle();
	            cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 设置字体居中
	            // 设置字体
	            HSSFFont font = workbook.createFont();
	            font.setFontName("宋体");
	            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 字体加粗
	            // font.setFontHeight((short)12);
	            font.setFontHeightInPoints((short) 13);
	            cellStyle.setFont(font);
	            cell3.setCellStyle(cellStyle);
				//写入数据
	            cell3.setCellValue(title2[i]);
	            
	            
	        }
			for(int j=1;j<4;j=j+2){
				cell3 = row2.createCell(j);
	           
	            if(j==1){
	            	cell3.setCellValue(employeeIn.getPhone());
		            
	            }
	            if(j==3){
	            	Department department =departmentService.getDepartmentById(employeeIn.getDepartmentid());
	            	cell3.setCellValue(department.getDepartmentname());
		            
	            }
	        }
			
			
			
	    	
	    	
	    	
	    	
			//创建第五行
			HSSFRow row5 = sheet.createRow(5);
			// 创建一个单元格
			HSSFCell cell5 = row5.createCell(2);;
			if(cell5!=null){
				
			
			 // 设置样式
            HSSFCellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 设置字体居中
            // 设置字体
            HSSFFont font = workbook.createFont();
            font.setFontName("宋体");
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 字体加粗
            // font.setFontHeight((short)12);
            font.setFontHeightInPoints((short) 20);
            cellStyle.setFont(font);
            cell5.setCellStyle(cellStyle);
				
            	//写入数据
	            cell5.setCellValue("考试培训详细信息");
	            System.out.println("cell5"+cell5);
			}
	    	/*
	    	 * 培训信息
	    	 */
			// 标题
			
			String[] title = { "考试名称", "考试级别", "考试时间", "考试总分数", "获得成绩"};
			// 创建第六行
			HSSFRow row = sheet.createRow(6);
			// 创建一个单元格
			HSSFCell cell = null;
			// 创建表头
	        for (int i = 0; i < title.length; i++) {
	            cell = row.createCell(i);
	            // 设置样式
	            HSSFCellStyle cellStyle = workbook.createCellStyle();
	            cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 设置字体居中
	            // 设置字体
	            HSSFFont font = workbook.createFont();
	            font.setFontName("宋体");
	            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 字体加粗
	       
	            font.setFontHeightInPoints((short) 13);
	            cellStyle.setFont(font);
	            cell.setCellStyle(cellStyle);
	            cell.setCellValue(title[i]);
	          
	        }
	       

	        // 写入数据
	        // 从第六行开始追加数据
	        for (int i = 7; i < (exams.size() + 7); i++) {
	            // 创建第i行
	            HSSFRow nextRow = sheet.createRow(i);
	            // 获取数据
	            Map<String, Object> exam = exams.get(i - 7);
	           
	            for (int j = 0; j < 5; j++) {
					
					HSSFCell cell2 = nextRow.createCell(j);
					if (j == 0) {
						cell2.setCellValue( exam.get("examName").toString());
					}
					if (j == 1) {
						String level="";
						
						if(exam.get("examLevel").toString()==null){
							level="未开始";
						}
						if("0".equals(exam.get("examLevel").toString())){
							level="未通过";
						}
						if("1".equals(exam.get("examLevel").toString())){
							level="通过一级考试";
						}
						if("2".equals(exam.get("examLevel").toString())){
							level="通过二级考试";
						}
						if("3".equals(exam.get("examLevel").toString())){
							level="通过三级考试";
						}
						
						cell2.setCellValue(level);
					}
					if (j == 2) {
						cell2.setCellValue(exam.get("startTime").toString()+"到"+exam.get("endTime").toString());
					}
					if (j == 3) {
						cell2.setCellValue(exam.get("paperScore").toString());
					}
					if (j == 4) {
						cell2.setCellValue(exam.get("grade").toString());
					}
				
				}
	        }

	        // 创建一个文件
	        File file = new File(fileName);
	        // 如果存在就删除
	        if (file.exists()) {
	            file.delete();
	        }
	        try {
	            file.createNewFile();
	            // 打开文件流
	            FileOutputStream outputStream = FileUtils.openOutputStream(file);
	          
	            workbook.write(outputStream);
	            outputStream.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return true;
	    }

	    // 设置列宽()
	    private static void setColumnWidth(HSSFSheet sheet, int colNum) {
	        for (int i = 0; i < colNum; i++) {
	            int v = 0;
	            v = Math.round(Float.parseFloat("15.0") * 37F);
	            v = Math.round(Float.parseFloat("20.0") * 267.5F);
	            sheet.setColumnWidth(i, v);
	        }
	    }

	    // 3.打开文件的流提供下载
	    public InputStream getInputStream() throws Exception {
	        this.create();// 创建文件到指定目录下
	        //String path = ServletActionContext.getServletContext().getRealPath("/files/examEmployeesExcel");
	        String filepath = ResourcesUtil.getValue("path", "excel")  + fileName + ".xls";
	    
	        File file = new File(filepath);
	        // 只用返回一个输入流
	   
	        return FileUtils.openInputStream(file);// 打开文件
	    }

	    // 产生Excel到文件夹下面
	    public void create() throws Exception {
	        // 获取工程下的路径
	        //String path = ServletActionContext.getServletContext().getRealPath("/files/examEmployeesExcel");
	        String filepath = ResourcesUtil.getValue("path", "excel")  + fileName + ".xls";
	        // 获取文件
	        List<Map<String, Object>> examEmployees = this.findExamEmployeeByExamId();
	        this.writeExamEmployees2Excel(examEmployees, filepath);
	    }

	    // 文件下载名
	    public String getDownloadFileName() {
	        String downloadFileName = "";
	        //String filename = fileName + ".xls";
	        String filename = idcode + ".xls";
	       
	        try {
	            downloadFileName = URLEncoder.encode(filename, "UTF-8");
	        } catch (UnsupportedEncodingException e) {
	            e.printStackTrace();
	        }
	        return downloadFileName;
	    }

	    @Override
	    public String execute() throws Exception {
	        // 先将名字设为秒数产生唯一的名字
	        //this.setFileName(String.valueOf(System.currentTimeMillis()));
	        this.setFileName(idcode);
	        return super.execute();
	    }
	}