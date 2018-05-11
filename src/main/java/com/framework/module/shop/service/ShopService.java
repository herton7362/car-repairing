package com.framework.module.shop.service;

import com.framework.module.shop.domain.Shop;
import com.kratos.common.CrudService;
import com.kratos.common.PageResult;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Map;

public interface ShopService extends CrudService<Shop> {
    /**
     * 查看所有企业信息
     * @param pageRequest 分页参数
     * @param param 查询条件
     * @return 分页后的数据
     */
    PageResult<Shop> findAllEveryEntities(PageRequest pageRequest, Map<String, String[]> param) throws Exception;

    /**
     * 查看所有企业信息
     * @param param 查询条件
     * @return 结果数据
     */
    List<Shop> findAllEveryEntities(Map<String, String[]> param) throws Exception;
}
