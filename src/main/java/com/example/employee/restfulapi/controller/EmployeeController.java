package com.example.employee.restfulapi.controller;

import com.example.employee.restfulapi.entity.Employee;
import com.example.employee.restfulapi.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("")
    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    @GetMapping("/{param}")
    public Object getEmployee(@PathVariable("param") String param) {
        if (Pattern.matches("^[0-9]+$", param)) {
            return employeeRepository.getById(Long.parseLong(param));
        }
        return employeeRepository.findByGender(param);
    }

    @GetMapping("/page/{page}/pageSize/{pageSize}")
    public Page<Employee> getEmployeeByPage(@PathVariable("page") int page, @PathVariable("pageSize") int pageSize) {
        return employeeRepository.findAll(new PageRequest(page, pageSize));
    }

    @PostMapping("")
    public Employee addEmployee(@RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }

    @PutMapping("/{id}")
    public Employee updateEmployee(@RequestBody Employee employee, @PathVariable("id") Long id) {
        Employee employeeToUpdate = employeeRepository.getById(id);
        employeeToUpdate.setName(employee.getName());
        employeeToUpdate.setAge(employee.getAge());
        employeeToUpdate.setGender(employee.getGender());
        employeeToUpdate.setCompanyId(employee.getCompanyId());
        employeeToUpdate.setSalary(employee.getSalary());

        return employeeRepository.save(employeeToUpdate);
    }

    @DeleteMapping("/{id}")
    public String deleteEmployee(@PathVariable("id") Long id) {
        try {
            employeeRepository.delete(id);
            return "delete success id:" + id;
        } catch (Exception e) {
            e.printStackTrace();
            return "error: " + e.getMessage();
        }
    }

}
