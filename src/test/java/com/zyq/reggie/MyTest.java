package com.zyq.reggie;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyq.reggie.common.R;
import com.zyq.reggie.controller.DishController;
import com.zyq.reggie.dto.DishDto;
import com.zyq.reggie.entity.Employee;
import com.zyq.reggie.mapper.EmployeeMapper;
import com.zyq.reggie.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.util.Map;

@SpringBootTest
public class MyTest {
    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private DishController dishController;

    @Test
    public void test01(){
        System.out.println(111);
        Employee employee = employeeMapper.selectById(1);
        System.out.println(employee);
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, "admin");
        Employee one = employeeService.getOne(queryWrapper);
        queryWrapper.select(Employee::getUsername, Employee::getPassword);
//        Map<String, Object> map = employeeService.getO(queryWrapper);
//        employeeService.list(queryWrapper).forEach(System.out::println);
//        System.out.println(map);
    }

    @Test
    public void test02(){
        File file = new File("E:\\backProject\\reggie\\src\\test\\testfile");
        if (!file.exists()){
            file.mkdirs();
        }
//        File file1 = new File(file.get);
        File file1 = new File(file, "abc.txt");
        File file2 = new File(file, "def.txt");
        FileInputStream inputStream = null;
        FileOutputStream outputStream=null;
        try {
            inputStream = new FileInputStream(file1);
            outputStream = new FileOutputStream(file2);
            byte[] bytes = new byte[1024];
            int len=0;
            while ((len=inputStream.read(bytes))!=-1){
                outputStream.write(bytes, 0, len);
            }
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void test03(){
        R<Page<DishDto>> pageR = dishController.get1(1, 10, null);
        pageR.getData().getRecords().forEach(System.out::println);
    }

}
