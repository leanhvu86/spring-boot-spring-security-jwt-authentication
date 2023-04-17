package com.trunggame.security.services;

import java.util.List;

import com.trunggame.models.Company;
import com.trunggame.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public Company getCompanyById(Long id) {
        return companyRepository.findById(id).orElse(null);
    }

    public Company createCompany(Company company) {
        return companyRepository.save(company);
    }

    public Company updateCompany(Long id, Company company) {
        Company existingCompany = companyRepository.findById(id).orElse(null);
        if (existingCompany != null) {
            existingCompany.setName(company.getName());
            existingCompany.setAddress(company.getAddress());
            existingCompany.setPhoneNumber(company.getPhoneNumber());
            existingCompany.setEmailAddress(company.getEmailAddress());
            existingCompany.setWebsite(company.getWebsite());
            existingCompany.setDescription(company.getDescription());
            return companyRepository.save(existingCompany);
        } else {
            return null;
        }
    }

    public void deleteCompany(Long id) {
        companyRepository.deleteById(id);
    }

}
