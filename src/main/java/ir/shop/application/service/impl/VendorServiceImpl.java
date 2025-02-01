package ir.shop.application.service.impl;

import ir.shop.application.entity.Vendor;
import ir.shop.application.repository.VendorRepo;
import ir.shop.application.service.VendorService;
import ir.shop.application.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class VendorServiceImpl extends BaseServiceImpl<Vendor, Long, VendorRepo> implements VendorService {
    public VendorServiceImpl(VendorRepo repo) {
        super(repo);
    }
}
