package ir.shop.application.service;

import ir.shop.application.entity.Config;
import ir.shop.application.service.base.BaseService;

public interface ConfigService extends BaseService<Config, Long> {
    Config findByName(String name);

    Config createConfig(Config config);
}
