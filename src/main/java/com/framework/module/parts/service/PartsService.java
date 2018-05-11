package com.framework.module.parts.service;

import com.framework.module.parts.domain.Parts;
import com.framework.module.parts.web.PartsResult;
import com.framework.module.parts.web.PartsSaveParam;
import com.kratos.common.CrudService;
import com.kratos.common.PageResult;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Map;

public interface PartsService extends CrudService<Parts> {
    /**
     * 保存
     * @param partsSaveParam 参数
     */
    void save(PartsSaveParam partsSaveParam) throws Exception;

    /**
     * 分页查询，并且转译结果
     * @param pageRequest 分页条件
     * @param param 查询条件
     * @return {@link GoodsResult} spring boot 分页类
     */
    PageResult<PartsResult> findAllTranslated(PageRequest pageRequest, Map<String, String[]> param) throws Exception;

    /**
     * 查询，并且转译结果
     * @param param 查询条件
     * @return {@link GoodsResult} spring boot 分页类
     */
    List<PartsResult> findAllTranslated(Map<String, String[]> param) throws Exception;

    /**
     * 根据id查询一个
     * @param id 主键
     * @return 实体
     */
    PartsResult findOneTranslated(String id) throws Exception;
}
