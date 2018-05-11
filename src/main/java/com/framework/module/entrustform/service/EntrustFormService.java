package com.framework.module.entrustform.service;

import com.framework.module.entrustform.domain.EntrustForm;
import com.framework.module.entrustform.web.DispatchParam;
import com.framework.module.entrustform.web.EntrustFormResult;
import com.framework.module.entrustform.web.PayParam;
import com.kratos.common.CrudService;
import com.kratos.common.PageResult;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface EntrustFormService extends CrudService<EntrustForm> {
    /**
     * 派工
     * @param formId 委托单id
     * @param dispatchParam 参数 {@link DispatchParam}
     */
    void dispatch(String formId, DispatchParam dispatchParam) throws Exception;

    /**
     * 收款
     * @param formId 委托单id
     * @param payParam 参数
     */
    void pay(String formId, PayParam payParam) throws Exception;

    /**
     * 竣工
     * @param formId 委托单id
     */
    void finish(String formId) throws Exception;

    /**
     * 分页查询所有
     * @param pageRequest 分页条件
     * @param param 查询条件
     * @return 查询结果
     */
    PageResult<EntrustFormResult> findAllTranslated(PageRequest pageRequest, Map<String, String[]> param) throws Exception;

    /**
     * 分页查询所有
     * @param pageRequest 分页条件
     * @param param 查询条件
     * @return 查询结果
     */
    List<EntrustFormResult> findAllTranslated(Map<String, String[]> param) throws Exception;

    /**
     * 查询一个
     * @param id 主键
     * @return 委托单
     */
    EntrustFormResult getOneTranslated(String id) throws Exception;

    /**
     * 确认
     * @param formId 订单id
     */
    void confirm(String formId) throws Exception;

    Map<String, Integer> getOrderCounts(String creatorId);

    void payed(String outTradeNo) throws Exception;
}
