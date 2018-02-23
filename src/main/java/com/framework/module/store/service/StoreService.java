package com.framework.module.store.service;

import com.framework.module.store.domain.Goods;
import com.framework.module.store.domain.Store;
import com.kratos.common.CrudService;
import com.kratos.common.PageResult;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface StoreService extends CrudService<Store> {
    /**
     * 获取货物
     * @param storeId 仓库id
     * @return 货物列表
     */
    List<Goods> getGoods(String storeId) throws Exception;
    /**
     * 分页获取货物
     * @param pageRequest 分页参数
     * @param storeId 仓库id
     * @return 货物分页列表
     */
    PageResult<Goods> getGoods(PageRequest pageRequest, String storeId) throws Exception;

    /**
     * 增加库存
     * @param storeId 仓库id
     * @param relationId 商品id
     * @param count 数量
     */
    void inStore(String storeId, String relationId, Double count) throws Exception;

    /**
     * 减少库存
     * @param storeId 仓库id
     * @param relationId 商品id
     * @param count 数量
     */
    void outStore(String storeId, String relationId, Double count) throws Exception;
}
