package ir.shop.application.repository;

import ir.shop.application.entity.Customer;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepo extends BaseRepo<Customer, Long> {
}
