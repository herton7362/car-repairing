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

import java.math.BigDecimal;
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

    @Override
    public synchronized void inStore(String storeId, String relationId, Double count) throws Exception {
        Map<String, String[]> params = new HashMap<>();
        params.put("storeId", new String[]{storeId});
        params.put("relationId", new String[]{relationId});
        List<Goods> goodsList = goodsService.findAll(params);
        Goods goods = null;
        if(goodsList != null && !goodsList.isEmpty()) {
            goods = goodsList.get(0);
            goods.setCount(new BigDecimal(goods.getCount()).add(new BigDecimal(count)).doubleValue());
            goodsService.save(goods);
        } else {
            goods = new Goods();
            goods.setCount(count);
            goods.setRelationId(relationId);
            goods.setStoreId(storeId);
            goodsService.save(goods);
        }
    }

    @Override
    public synchronized void outStore(String storeId, String relationId, Double count) throws Exception {
        Map<String, String[]> params = new HashMap<>();
        params.put("storeId", new String[]{storeId});
        params.put("relationId", new String[]{relationId});
        List<Goods> goodsList = goodsService.findAll(params);
        Goods goods = null;
        if(goodsList != null && !goodsList.isEmpty()) {
            goods = goodsList.get(0);
            goods.setCount(new BigDecimal(goods.getCount()).subtract(new BigDecimal(count)).doubleValue());
            goodsService.save(goods);
        } else {
            goods = new Goods();
            goods.setCount(-count);
            goods.setRelationId(relationId);
            goods.setStoreId(storeId);
            goodsService.save(goods);
        }
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
