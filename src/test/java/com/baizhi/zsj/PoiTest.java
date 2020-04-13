package com.baizhi.zsj;


import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.PutObjectRequest;
import com.baizhi.zsj.dao.AdminDao;
import com.baizhi.zsj.dao.UserMapper;
import com.baizhi.zsj.entity.Emps;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.junit.Test;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

/*@RunWith(SpringRunner.class)
@SpringBootTest*/
public class PoiTest {

    @Test
    public void testPoi(){
        //创建一个Excel文档
        HSSFWorkbook workbook = new HSSFWorkbook();

        //创建一个工作表
        HSSFSheet sheet = workbook.createSheet("学生信息1");

        //创建一行 row  下标从0开始
        HSSFRow row = sheet.createRow(0);

        //创建一个单元格 下标从0开始
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("山川异域 风月同天");

        //导出单元格
        try {
            workbook.write(new FileOutputStream(new File("F://186poi-学习测试.xls")));
            //释放资源
            workbook.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testPois(){
        ArrayList<Emps> emps = new ArrayList<>();
        emps.add(new Emps("1","嘿嘿",18,new Date()));
        emps.add(new Emps("2","拜拜",19,new Date()));
        emps.add(new Emps("3","熊熊",20,new Date()));
        emps.add(new Emps("4","真真",28,new Date()));
        emps.add(new Emps("5","佳佳",28,new Date()));
        //创建一个Excel文档
        Workbook workbook = new HSSFWorkbook();

        //创建一个工作表
        Sheet sheet = workbook.createSheet("学生信息1");

        //设置列宽   参数：哪一列  列的值
        sheet.setColumnWidth(3,20*256);

        //设置字体大小 样式
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 15);//设置字体大小
        font.setFontName("微软雅黑");
        font.setBold(true); //是否加粗
        font.setColor(Font.COLOR_RED); //字体颜色
        font.setItalic(true); //斜体
        font.setUnderline(FontFormatting.U_SINGLE); //下划线

        //创建标题样式
        CellStyle cellStyle1 = workbook.createCellStyle();
        cellStyle1.setAlignment(HorizontalAlignment.CENTER);//居中
        cellStyle1.setFont(font); //字体


        //创建一个标题
        Row titleRow = sheet.createRow(0);
        //设置行高
        titleRow.setHeight((short)(20*20));
        //创建标题单元格 并赋值
        Cell cell1 = titleRow.createCell(0);
        cell1.setCellStyle(cellStyle1);
        cell1.setCellValue("186学生信息表");

        //合并单元格                                    参数分别是: 行开始 行结束 列开始 列结束
        CellRangeAddress cellAddresses = new CellRangeAddress(0,0,0,3);
        //将设置好的格式放进sheet中
        sheet.addMergedRegion(cellAddresses);


        //目录行
        String[] titles={"ID","姓名","芳龄","生日"};

        //创建目录行
        Row row = sheet.createRow(1);

        //设置行高
        row.setHeight((short)(20*20));
        
        //遍历数组
        for (int i = 0; i < titles.length; i++) {
            //创建一个单元格
            Cell cell = row.createCell(i);
            //给单元格赋值
            cell.setCellValue(titles[i]);
        }


        //处理日期格式
        DataFormat format = workbook.createDataFormat();

        //设置日期格式
        short formats = format.getFormat("yyyy年MM月dd日");

        //创建样式格式
        CellStyle cellStyle = workbook.createCellStyle();

        //将设置好的日期格式给样式对象
        cellStyle.setDataFormat(formats);

        //处理数据行
        for (int i = 0; i < emps.size(); i++) {

            //每遍历一条数据 创建一行
            Row rows = sheet.createRow(i+2);
            //创建单元格 给单元格赋值
            rows.createCell(0).setCellValue(emps.get(i).getId());
            rows.createCell(1).setCellValue(emps.get(i).getName());
            rows.createCell(2).setCellValue(emps.get(i).getAge());
            //创建单元格
            Cell cell = rows.createCell(3);
            //给单元格设置指定样式
            cell.setCellStyle(cellStyle);
            //给单元格赋值
            cell.setCellValue(emps.get(i).getDate());


        }

        //导出单元格
        try {
            workbook.write(new FileOutputStream(new File("F://186poi-学习测试.xls")));
            //释放资源
            workbook.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testImport(){
        //创建一个Excel文档
        try {
            //获取要导入的文件
            Workbook workbook = new HSSFWorkbook(new FileInputStream(new File("F://186poi-学习测试.xls")));
            //根据文档获取工作表
            Sheet sheet = workbook.getSheet("学生信息1");

            for (int i = 2; i <= sheet.getLastRowNum(); i++) {
                 //获取行
                Row row = sheet.getRow(i);
                //获取行数据
                String id = row.getCell(0).getStringCellValue();
                String name = row.getCell(1).getStringCellValue();
                double ages = row.getCell(2).getNumericCellValue();
                int age = (int) ages;
                Date date = row.getCell(3).getDateCellValue();

                Emps emps = new Emps(id, name, age, date);
                System.out.println("向数据库插入:"+emps);


            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
