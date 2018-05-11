package com.framework.module.shop.service;

import com.framework.module.shop.domain.Shop;
import com.framework.module.shop.domain.ShopRepository;
import com.kratos.common.AbstractCrudService;
import com.kratos.common.PageRepository;
import com.kratos.common.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Component
@Transactional
public class ShopServiceImpl extends AbstractCrudService<Shop> implements ShopService {
    private final ShopRepository shopRepository;
    @Override
    protected PageRepository<Shop> getRepository() {
        return shopRepository;
    }

    @Override
    public PageResult<Shop> findAllEveryEntities(PageRequest pageRequest, Map<String, String[]> param) throws Exception {
        Page<Shop> page = shopRepository.findAll(this.getSpecificationForAllEntities(param), pageRequest);
        return new PageResult<>(page);
    }

    @Override
    public List<Shop> findAllEveryEntities(Map<String, String[]> param) throws Exception {
        return shopRepository.findAll(this.getSpecificationForAllEntities(param));
    }

    @Autowired
    public ShopServiceImpl(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }
}
