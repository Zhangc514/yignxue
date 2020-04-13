package com.baizhi.zsj;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.baizhi.zsj.entity.Emps;
import com.baizhi.zsj.entity.Photo;
import com.baizhi.zsj.entity.Teacher;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.junit.Test;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*@RunWith(SpringRunner.class)
@SpringBootTest*/
public class EasyPoiTest {

    @Test
    public void testExport() {
        ArrayList<Emps> emps = new ArrayList<>();
        emps.add(new Emps("1", "嘿嘿", 18, new Date()));
        emps.add(new Emps("2", "拜拜", 19, new Date()));
        emps.add(new Emps("3", "熊熊", 20, new Date()));
        emps.add(new Emps("4", "真真", 28, new Date()));
        emps.add(new Emps("5", "佳佳", 28, new Date()));


        //导出的参数     title:标题     工作表名
        ExportParams params = new ExportParams("计算机一班学生", "学 生");
        //参数：标题，表名，实体类类对象，导出的集合
        Workbook workbook = ExcelExportUtil.exportExcel(params,Emps.class, emps);


        try {
            //导出
            workbook.write(new FileOutputStream(new File("F://186-EasyPoi.xls")));
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testExports() {
        ArrayList<Emps> emps = new ArrayList<>();
        emps.add(new Emps("1", "嘿嘿", 18, new Date()));
        emps.add(new Emps("2", "拜拜", 19, new Date()));
        emps.add(new Emps("3", "熊熊", 20, new Date()));
        emps.add(new Emps("4", "真真", 28, new Date()));
        emps.add(new Emps("5", "佳佳", 28, new Date()));


        Teacher teacher1 = new Teacher("1","梦",18,emps);
        Teacher teacher2 = new Teacher("2","想",21,emps);

        ArrayList<Teacher> teachersList = new ArrayList<>();
        teachersList.add(teacher1);
        teachersList.add(teacher2);


        //导出的参数     title:标题     工作表名
        ExportParams params = new ExportParams("计算机一班学生", "学 生");
        //参数：标题，表名，实体类类对象，导出的集合
        Workbook workbook = ExcelExportUtil.exportExcel(params,Teacher.class, teachersList);


        try {
            //导出
            workbook.write(new FileOutputStream(new File("F://186-EasyPoi.xls")));
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public  void imports(){

        //配置导入相关数据
        ImportParams params = new ImportParams();
        params.setTitleRows(1);//表格标题行数 默认是0行
        params.setHeadRows(2);  //表头所占行 表头行数 默认一行


        try {
            FileInputStream fileInputStream = new FileInputStream(new File("F://186-easyPoi.xls"));
            List<Teacher> list = ExcelImportUtil.importExcel(fileInputStream, Teacher.class, params);


            for (Teacher teacher : list) {
                System.out.println("======导入老师数据"+teacher);
                List<Emps> empsList = teacher.getEmpsList();

                for (Emps emps : empsList) {

                    System.out.println("------导入学生数据"+emps);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testExportPhoto() {
        ArrayList<Photo> photos = new ArrayList<>();
        photos.add(new Photo("1", "src/main/webapp/upload/photo/1585566001482-pic4.jpg", 18, new Date()));
        photos.add(new Photo("2", "src/main/webapp/upload/photo/1585566058690-pic1.jpg", 19, new Date()));
        photos.add(new Photo("3", "src/main/webapp/upload/photo/1585568583366-pic7.jpg", 20, new Date()));
        photos.add(new Photo("4", "src/main/webapp/upload/photo/1585569004701-pic8.jpg", 28, new Date()));
        photos.add(new Photo("5", "src/main/webapp/upload/photo/1585569083596-pic6.jpg", 28, new Date()));



        //导出的参数     title:标题     工作表名
        ExportParams params = new ExportParams("图片上传", "头像1");
        //参数：标题，表名，实体类类对象，导出的集合
        Workbook workbook = ExcelExportUtil.exportExcel(params,Photo.class, photos);


        try {
            //导出
            workbook.write(new FileOutputStream(new File("F://186-user.xls")));
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
