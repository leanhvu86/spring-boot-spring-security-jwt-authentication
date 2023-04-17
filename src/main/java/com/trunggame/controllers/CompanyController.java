package com.trunggame.controllers;

import com.trunggame.dto.BaseResponseDTO;
import com.trunggame.models.Company;
import com.trunggame.security.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping
    public BaseResponseDTO<?> getAllCompanies() {
        return new BaseResponseDTO<>("Success", 200,200,companyService.getAllCompanies());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable Long id) {
        Company company = companyService.getCompanyById(id);
        if (company != null) {
            return new ResponseEntity<>(company, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public  BaseResponseDTO<?> createCompany(@RequestBody Company company) {
        Company createdCompany = companyService.createCompany(company);
        return new BaseResponseDTO<>("Success", 200,200,createdCompany);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public BaseResponseDTO<?> updateCompany(@PathVariable("id") Long companyId, @RequestBody Company companyDetails) {
        Company updatedCompany = companyService.updateCompany(companyId, companyDetails);
        return updatedCompany != null ?  new BaseResponseDTO<>("Success", 200,200,updatedCompany)
                :  new BaseResponseDTO<>("No content", 403,403,null);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public BaseResponseDTO<?> deleteCompany(@PathVariable("id") Long companyId) {
        companyService.deleteCompany(companyId);
        return new BaseResponseDTO<>("Success", 200,200,null);
    }
}

