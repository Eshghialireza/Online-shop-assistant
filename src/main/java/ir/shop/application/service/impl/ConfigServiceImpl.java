package ir.shop.application.service.impl;

import ir.shop.application.entity.Config;
import ir.shop.application.repository.ConfigsRepo;
import ir.shop.application.service.ConfigService;
import ir.shop.application.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ConfigServiceImpl extends BaseServiceImpl<Config, Long, ConfigsRepo> implements ConfigService {
    public ConfigServiceImpl(ConfigsRepo repo) {
        super(repo);
    }

    @Override
    public Config findByName(String name) {
        return repo.findByName(name);
    }

    @Override
    public Config createConfig(Config config) {
        Config realConfig = repo.findByName("config");
        if (realConfig == null) {
            return repo.save(config);
        }
        return realConfig;
    }

}
