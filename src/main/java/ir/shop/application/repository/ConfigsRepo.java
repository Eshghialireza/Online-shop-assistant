package ir.shop.application.repository;

import ir.shop.application.entity.Config;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigsRepo extends BaseRepo<Config, Long> {
    Config findByName(String name);
}
