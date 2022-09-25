package com.example.companyemployeespring.controller.employee;

import com.example.companyemployeespring.entity.Employee;
import com.example.companyemployeespring.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class EmployeeController {
    @Autowired
    EmployeeRepository employeeRepository;

    @GetMapping("/employee")
    public String listPage(ModelMap modelMap) {
        List<Employee> allEmployees = employeeRepository.findAll();
        modelMap.addAttribute("employees", allEmployees);
        return "employee/list";
    }

    @GetMapping("/employee/add")
    public String addPage() {
        return "employee/add";
    }

    @PostMapping("/employee/add")
    public String add(@RequestParam Employee employee) {
        employeeRepository.save(employee);
        return "redirect:/employee/list";
    }

    @GetMapping("/employee/view")
    public String viewPage(@RequestAttribute int id, ModelMap modelMap) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()){
            modelMap.addAttribute("employee", employee);
            return "employee/view";
        }
        return "redirect:/employee";
    }
}
