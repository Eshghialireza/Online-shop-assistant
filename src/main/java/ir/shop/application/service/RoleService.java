package ir.shop.application.service;

import ir.shop.application.entity.ERoles;
import ir.shop.application.entity.Role;
import ir.shop.application.service.base.BaseService;

import java.util.Optional;

public interface RoleService extends BaseService<Role, Long> {
    void addRolesToApplication();

    Long countAll();

    Role findByName(ERoles name);
}
