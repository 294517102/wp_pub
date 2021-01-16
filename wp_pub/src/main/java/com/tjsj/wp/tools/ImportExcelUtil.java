/**
 * 
 */
package com.tjsj.wp.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import jxl.Sheet;

/**
 *
 * @author 郑彬彬
 * @date 2018年6月28日 下午4:44:54
 * @version V1.0
 *
 */
public class ImportExcelUtil {
	public static List<Map<String,Object>> importExcel(File file) throws IOException{    
        String fileName = file.getName();    
        String extension = fileName.lastIndexOf(".")==-1?"":fileName.substring(fileName.lastIndexOf(".")+1);    
        if("xls".equals(extension)){    
         return read2003Excel(file);    
        }else if("xlsx".equals(extension)){    
         return read2007Excel(file);    
        }else{
         throw new IOException("不支持的文件类型");    
        }    
     }  



     /**  
     * 读取 office 2003 excel  
     * @throws IOException   
     * @throws FileNotFoundException */    
     private static List<Map<String,Object>> read2003Excel(File file) throws IOException{    
//        List<List<Object>> list = new LinkedList<List<Object>>();   
    	 List<Map<String,Object>> list = new ArrayList();    
        HSSFWorkbook hwb = new HSSFWorkbook(new FileInputStream(file));    
        HSSFSheet sheet = hwb.getSheetAt(0);    
        Object value = null;    
        HSSFRow row = null;    
        HSSFCell cell = null;     
//        for(int i = sheet.getFirstRowNum();i<= sheet.getPhysicalNumberOfRows();i++){    
        for(int i = 2;i<= sheet.getPhysicalNumberOfRows();i++){    
//        for(int i = 2;i<= 5;i++){    
         row = sheet.getRow(i);    
         HSSFRow firstrow=sheet.getRow(0);
         if (row == null) {    
          continue;    
         }    
//         List<Object> linked = new LinkedList<Object>();   
         Map<String,Object> linked = new HashMap<String, Object>();  
         for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {    
          cell = row.getCell(j);    
          if (cell == null) {    
           continue;    
          }    
          DecimalFormat df = new DecimalFormat("0");// 格式化 number String 字符    
          SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化日期字符串    
          DecimalFormat nf = new DecimalFormat("0.##");// 格式化数字    
          DecimalFormat tf = new DecimalFormat("#");// 格式化数字    
          DecimalFormat tf2 = new DecimalFormat("#.##");// 格式化数字    
          
//          cellStyle.setDataFormat(format.getFormat("#,##0"));
          
//          System.out.println("单元格是否合并：》"+sheet.getNumMergedRegions());
          System.out.println("==="+cell.getCellStyle().getDataFormatString());
          switch (cell.getCellType()) {  
          case XSSFCell.CELL_TYPE_STRING:    
         //  System.out.println(i+"行"+j+" 列 is String type");    
           value = cell.getStringCellValue();    
           break;    
          case XSSFCell.CELL_TYPE_NUMERIC:  
//           System.out.println(i+"行"+j+" 列 is Number type ; DateFormt:"+cell.getCellStyle().getDataFormatString());   
           if(DateUtil.isCellDateFormatted(cell)){
        	   value = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue())); 
           }else    if("@".equals(cell.getCellStyle().getDataFormatString())){    
              value = df.format(cell.getNumericCellValue());    
           } else if("General".equals(cell.getCellStyle().getDataFormatString())){    
              value = nf.format(cell.getNumericCellValue());  
           } else if("#,##0;[Red]#,##0".equals(cell.getCellStyle().getDataFormatString())){
        	   value = tf.format(cell.getNumericCellValue());   
           }else if("0.00_ ".equals(cell.getCellStyle().getDataFormatString())){
//        	   System.out.println("jiaoch:"+cell.getNumericCellValue());
        	   value = tf.format(cell.getNumericCellValue());   
           }else{   
        	   value = tf2.format(cell.getNumericCellValue());      
           }    
           break;    
          case XSSFCell.CELL_TYPE_BOOLEAN:    
        //   System.out.println(i+"行"+j+" 列 is Boolean type");    
           value = cell.getBooleanCellValue();    
           break;    
          case XSSFCell.CELL_TYPE_BLANK:    
        //   System.out.println(i+"行"+j+" 列 is Blank type");    
           value = "";    
           break;    
          default:    
        //   System.out.println(i+"行"+j+" 列 is default type");    
           value = cell.toString();    
          }    
          if (value == null || "".equals(value)) {    
           continue;    
          }    
//          linked.add(value);      
          linked.put(firstrow.getCell(j).toString(), value);      
        }   
         if(linked.size()!=0){
        	 list.add(linked);    
         }
        }    
        return list;    
     }


     /**  
     * 读取Office 2007 excel  
     * */    
     private static List<Map<String,Object>> read2007Excel(File file) throws IOException {    
//        List<List<Object>> list = new LinkedList<List<Object>>();    
        List<Map<String,Object>> list = new ArrayList();    
        // 构造 XSSFWorkbook 对象，strPath 传入文件路径    
        XSSFWorkbook xwb = new XSSFWorkbook(new FileInputStream(file));    
        // 读取第一章表格内容    
        XSSFSheet sheet = xwb.getSheetAt(0);    
        Object value = null;    
        XSSFRow row = null;    
        XSSFCell cell = null;    
//        for (int i = sheet.getFirstRowNum(); i <= sheet.getPhysicalNumberOfRows(); i++) {    
        for (int i = 2; i <= sheet.getPhysicalNumberOfRows(); i++) {    
         row = sheet.getRow(i);  
         XSSFRow firstrow=sheet.getRow(1);
         if (row == null) {    
          continue;    
         }    
//         Map<String,Object> linked = new LinkedList<Object>();    
         Map<String,Object> linked = new HashMap<String, Object>();    
         for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {    
          cell = row.getCell(j);    
          if (cell == null) {    
           continue;    
          }    
          SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化日期字符串    
          DecimalFormat nf = new DecimalFormat("#.##");// 格式化数字    
          
          switch (cell.getCellType()) {
//          1
          case XSSFCell.CELL_TYPE_STRING:    
           value = cell.getStringCellValue();    
           break;
           
       /*   case HSSFCell.CELL_TYPE_NUMERIC:  
              value = cell.getStringCellValue();    
              break;*/
          case XSSFCell.CELL_TYPE_NUMERIC:
           if(DateUtil.isCellDateFormatted(cell)){
        	   value = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue())); 
           }else	if("@".equals(cell.getCellStyle().getDataFormatString())){    
             value = nf.format(cell.getNumericCellValue());    
           } else if("General".equals(cell.getCellStyle().getDataFormatString())){    
             value = nf.format(cell.getNumericCellValue());
           }else {  
            value = nf.format(cell.getNumericCellValue());    
           }  
//           System.out.println("得到的value"+value);
           if(StringUtils.isNumeric(value.toString())) {
        	   DecimalFormat df = new DecimalFormat("#.##");
        	   DecimalFormat g2=new DecimalFormat("0.00");
//        	   System.out.println(g2.format(Double.valueOf(value.toString())));//
           }
           break;    
          case XSSFCell.CELL_TYPE_BOOLEAN:    
//           System.out.println(i+"行"+j+" 列 is Boolean type");    
           value = cell.getBooleanCellValue();    
           break;    
          case XSSFCell.CELL_TYPE_BLANK:    
//         System.out.println(i+"行"+j+" 列 is Blank type");    
           value = "";    
           break;    
          default:    
//           System.out.println(i+"行"+j+" 列 is default type");    
           value = cell.toString();    
          }    
          if (value == null || "".equals(value)) {    
           continue;    
          }  
          linked.put(firstrow.getCell(j).toString(), value);
         } 
         if(linked.size()!=0){
        	 list.add(linked);    
         }
        }    
        return list;    
     }    

  
}
