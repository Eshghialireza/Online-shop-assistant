package ir.shop.application.repository;

import ir.shop.application.entity.Vendor;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorRepo extends BaseRepo<Vendor, Long> {
}
