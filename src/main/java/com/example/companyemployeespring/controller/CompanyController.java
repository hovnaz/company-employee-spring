package com.example.companyemployeespring.controller;

import com.example.companyemployeespring.entity.Company;
import com.example.companyemployeespring.repository.CompanyRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class CompanyController {

    @Autowired
    CompanyRepository companyRepository;


    @GetMapping("/company/list")
    public String listPage(ModelMap modelMap) {
        List<Company> all = companyRepository.findAll();
        modelMap.addAttribute("companies", all);
        return "company/list";
    }

    @GetMapping("/company/add")
    public String addPage() {
        return "company/add";
    }

    @PostMapping("/company/add")
    public String add(@RequestParam String name) {
        Company company = Company.builder().name(name).build();
        companyRepository.save(company);
        return "redirect:/company/list";
    }
    @GetMapping("/company/delete")
    public String delete(@RequestParam int id) {
        if(companyRepository.findById(id).isPresent()){
            companyRepository.deleteById(id);
        }
        return "redirect:/company/list";
    }
}
