package com.example.companyemployeespring.controller.employee;

import com.example.companyemployeespring.entity.Company;
import com.example.companyemployeespring.entity.Employee;
import com.example.companyemployeespring.repository.CompanyRepository;
import com.example.companyemployeespring.repository.EmployeeRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Controller
public class EmployeeController {
    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Value("${example.companyemployeespring.images.folder}")
    private String folderPath;

    @GetMapping("/employee/list")
    public String listPage(ModelMap modelMap) {
        List<Employee> allEmployees = employeeRepository.findAll();
        modelMap.addAttribute("employees", allEmployees);
        return "employee/list";
    }

    @GetMapping("/employee/add")
    public String addPage(ModelMap modelMap) {
        List<Company> allCompanies = companyRepository.findAll();
        modelMap.addAttribute("companies", allCompanies);
        return "employee/add";
    }

    @PostMapping("/employee/add")
    public String add(@ModelAttribute Employee employee,
                      @RequestParam("userImage") MultipartFile file) throws IOException {

        if (!file.isEmpty() && file.getSize() > 0) {
            String fileName = System.currentTimeMillis() + "_" +file.getOriginalFilename();
            File newFile = new File(folderPath + File.separator + fileName);
            file.transferTo(newFile);
            employee.setProfilePic(fileName);
        }

        employeeRepository.save(employee);

        if (employee.getCompany() != null) {
            Company company = employee.getCompany();
            company.setSize(company.getSize() + 1);
            companyRepository.save(company);
        }
        return "redirect:/employee/list";
    }

    @GetMapping("/employee/delete")
    public String remove(@RequestParam int id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()) {
            Company company = employee.get().getCompany();
            if (company != null) {
                company = companyRepository.findById(company.getId()).get();
                company.setSize(company.getSize() - 1);
                companyRepository.save(company);
            }
        }
        employeeRepository.deleteById(id);

        return "redirect:/employee/list";
    }

    @GetMapping(value = "/employee/getImage", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImage(@RequestParam("fileName") String fileName) throws IOException {
        InputStream inputStream = new FileInputStream(folderPath + File.separator + fileName);
        return IOUtils.toByteArray(inputStream);
    }
}
