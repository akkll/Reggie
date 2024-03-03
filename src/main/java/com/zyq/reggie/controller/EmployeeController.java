package com.zyq.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyq.reggie.common.R;
import com.zyq.reggie.entity.Employee;
import com.zyq.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    public R<Employee> get1(HttpServletRequest httpServletRequest, @RequestBody Employee employee){
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);
        if (emp==null){
            return R.error("登录失败");
        }
        if (!emp.getPassword().equals(password)){
            return R.error("登录失败");
        }
        if(emp.getStatus()==0){
            return R.error("账号已禁用");
        }
        httpServletRequest.getSession().setAttribute("employee", emp.getId());
        return R.success(emp);
    }
    @PostMapping("/logout")
    public R<Employee> get2(HttpServletRequest httpServletRequest){
        httpServletRequest.getSession().removeAttribute("employee");
        return R.success(null);
    }

    @PostMapping()
    public R<String> save(HttpServletRequest request,@RequestBody Employee employee){
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());
        Long emp = (Long) request.getSession().getAttribute("employee");
//        employee.setCreateUser(emp);
//        employee.setUpdateUser(emp);
        employeeService.save(employee);
        return R.success("新增员工成功");
    }

    @GetMapping("/page")
    public R<Page<Employee>> get4(int page, int pageSize, String name){
        log.info("{}-{}-{}", page, pageSize, name);
        //分页构造器,Page(第几页, 查几条)
        Page pageInfo = new Page(page, pageSize);
        //查询构造器
        LambdaQueryWrapper<Employee> lambdaQueryWrapper = new LambdaQueryWrapper();
        //过滤条件.like(什么条件下启用模糊查询，模糊查询字段，被模糊插叙的名称)
        lambdaQueryWrapper.like(!StringUtils.isEmpty(name), Employee::getName, name);
        //添加排序
        lambdaQueryWrapper.orderByDesc(Employee::getCreateTime);
        //查询分页、自动更新
        employeeService.page(pageInfo, lambdaQueryWrapper);
        //返回查询结果
        return R.success(pageInfo);
    }

    @PutMapping
    public R<String> put1(HttpServletRequest httpServletRequest, @RequestBody Employee employee){
        Long id = (Long) httpServletRequest.getSession().getAttribute("employee");
//        employee.setUpdateTime(LocalDateTime.now());
//        employee.setUpdateUser(id);
        employeeService.updateById(employee);
        return R.success("更新成功!");
    }

    @GetMapping("/{id}")
    public R<Employee> get5(@PathVariable Long id){
        Employee employee = employeeService.getById(id);
        return R.success(employee);
    }

}
