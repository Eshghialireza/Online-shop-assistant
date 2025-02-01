package ir.shop.application.service.base;

import ir.shop.application.repository.BaseRepo;

import java.io.Serializable;

public class BaseServiceImpl <E,ID extends Serializable,R extends BaseRepo<E,ID>> implements BaseService<E,ID> {
   protected final R repo;

    public BaseServiceImpl(R repo) {
        this.repo = repo;
    }

    @Override
    public E findById(ID id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public E save(E entity) {
        return repo.save(entity);
    }
}
