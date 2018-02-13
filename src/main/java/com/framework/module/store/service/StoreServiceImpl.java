package com.framework.module.store.service;

import com.framework.module.store.domain.Goods;
import com.framework.module.store.domain.Store;
import com.framework.module.store.domain.StoreRepository;
import com.kratos.common.AbstractCrudService;
import com.kratos.common.PageRepository;
import com.kratos.common.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Transactional
public class StoreServiceImpl extends AbstractCrudService<Store> implements StoreService {
    private final StoreRepository storeRepository;
    private final GoodsService goodsService;
    @Override
    protected PageRepository<Store> getRepository() {
        return storeRepository;
    }

    @Override
    public List<Goods> getGoods(String storeId) throws Exception {
        Map<String, String[]> params = new HashMap<>();
        params.put("storeId", new String[]{storeId});
        return goodsService.findAll(params);
    }

    @Override
    public PageResult<Goods> getGoods(PageRequest pageRequest, String storeId) throws Exception {
        Map<String, String[]> params = new HashMap<>();
        params.put("storeId", new String[]{storeId});
        return goodsService.findAll(pageRequest, params);
    }

    @Autowired
    public StoreServiceImpl(
            StoreRepository storeRepository,
            GoodsService goodsService
    ) {
        this.storeRepository = storeRepository;
        this.goodsService = goodsService;
    }
}
