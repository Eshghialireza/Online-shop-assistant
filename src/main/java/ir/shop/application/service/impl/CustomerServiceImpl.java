package ir.shop.application.service.impl;

import ir.shop.application.entity.Customer;
import ir.shop.application.repository.CustomerRepo;
import ir.shop.application.service.CustomerService;
import ir.shop.application.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl extends BaseServiceImpl<Customer, Long, CustomerRepo> implements CustomerService {
    public CustomerServiceImpl(CustomerRepo repo) {
        super(repo);
    }
}
