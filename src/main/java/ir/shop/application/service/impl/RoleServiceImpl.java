package ir.shop.application.service.impl;

import ir.shop.application.entity.ERoles;
import ir.shop.application.entity.Role;
import ir.shop.application.repository.RoleRepo;
import ir.shop.application.service.RoleService;
import ir.shop.application.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl extends BaseServiceImpl<Role, Long, RoleRepo> implements RoleService {
    public RoleServiceImpl(RoleRepo repo) {
        super(repo);
    }

    @Override
    public void addRolesToApplication() {
        if (countAll() == 0) {

            save(new Role(ERoles.USER));
            save(new Role(ERoles.ADMIN));
            save(new Role(ERoles.VENDOR));
        }
    }

    @Override
    public Long countAll() {
        return Long.valueOf(String.valueOf(repo.findAll().size()));
    }

    @Override
    public Role findByName(ERoles name) {
        return repo.findByName(name).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
    }
}
