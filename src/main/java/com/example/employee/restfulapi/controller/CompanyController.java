package com.example.employee.restfulapi.controller;

import com.example.employee.restfulapi.entity.Company;
import com.example.employee.restfulapi.entity.Employee;
import com.example.employee.restfulapi.repository.CompanyRepository;
import com.example.employee.restfulapi.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("")
    public List<Company> getCompanyList() {
        return companyRepository.findAll();
    }

    @GetMapping("/{id}")
    public Company getCompanyById(@PathVariable("id") Long id) {
        return companyRepository.getById(id);
    }

    @GetMapping("/{companyId}/employees")
    public List<Employee> getEmployeesbyCompanyId(@PathVariable("companyId") Long id) {
        return employeeRepository.findAllByCompanyId(id);
    }

    @GetMapping("/page/{page}/pageSize/{size}")
    public Page<Company> paginateCompanies(@PathVariable("page") int page, @PathVariable("size") int size) {
        return companyRepository.findAll(new PageRequest(page, size));
    }

    @PostMapping("")
    public Company addCompany(Company company) {
        companyRepository.save(company);
        return company;
    }

    @PutMapping("/{id}")
    public Company updateCompany(@RequestBody Company company, @PathVariable Long id) {
        Company companyToUpdate = companyRepository.getById(id);
        companyToUpdate.setCompanyName(company.getCompanyName());
        companyToUpdate.setEmployeesNumber(company.getEmployeesNumber());
        return companyRepository.save(company);
    }

    @DeleteMapping("/{id}")
    public String deleteCompany(@PathVariable Long id) {
        try {
            companyRepository.delete(id);
        } catch (Exception e) {
            return "error:" + e.getMessage();
        }
        return "success";
    }

}
