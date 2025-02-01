package ir.shop.application.repository;

import ir.shop.application.entity.ERoles;
import ir.shop.application.entity.Role;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepo extends BaseRepo<Role, Long> {
    Optional<Role> findByName(ERoles name);

}
