package ir.shop.application.service.base;

import java.io.Serializable;

public interface BaseService <E,ID extends Serializable>{
    E findById(ID id);
    E save(E entity);
}
