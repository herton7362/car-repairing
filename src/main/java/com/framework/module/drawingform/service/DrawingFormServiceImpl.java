package com.framework.module.drawingform.service;

import com.framework.module.drawingform.domain.DrawingForm;
import com.framework.module.drawingform.domain.DrawingFormRepository;
import com.framework.module.drawingform.web.OutStoreParam;
import com.framework.module.entrustform.domain.EntrustForm;
import com.framework.module.entrustform.domain.EntrustFormItem;
import com.framework.module.entrustform.domain.EntrustFormItemRepository;
import com.framework.module.entrustform.domain.EntrustFormParts;
import com.framework.module.entrustform.service.EntrustFormService;
import com.framework.module.store.domain.Goods;
import com.framework.module.store.domain.OutStoreForm;
import com.framework.module.store.domain.OutStoreFormItem;
import com.framework.module.store.service.GoodsService;
import com.framework.module.store.service.OutStoreFormItemService;
import com.framework.module.store.service.OutStoreFormService;
import com.kratos.common.AbstractCrudService;
import com.kratos.common.PageRepository;
import com.kratos.entity.BaseUser;
import com.kratos.exceptions.BusinessException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Transactional
public class DrawingFormServiceImpl extends AbstractCrudService<DrawingForm> implements DrawingFormService {
    private final DrawingFormRepository drawingFormRepository;
    private final OutStoreFormService outStoreFormService;
    private final OutStoreFormItemService outStoreFormItemService;
    private final EntrustFormService entrustFormService;
    private final GoodsService goodsService;
    @Override
    protected PageRepository<DrawingForm> getRepository() {
        return drawingFormRepository;
    }

    @Override
    public void outStore(String formId, OutStoreParam outStoreParam) throws Exception {
        if(StringUtils.isBlank(outStoreParam.getStoreId())) {
            throw new BusinessException("请指定仓库");
        }
        DrawingForm drawingForm = drawingFormRepository.findOne(formId);
        drawingForm.setStatus(DrawingForm.Status.OUT_STORE);
        drawingFormRepository.save(drawingForm);
        EntrustForm entrustForm = entrustFormService.findOne(drawingForm.getEntrustFormId());
        verifyStoreCount(outStoreParam.getStoreId(), entrustForm.getPartses());
        List<EntrustFormParts> partses = entrustForm.getPartses();
        OutStoreForm outStoreForm = new OutStoreForm();
        outStoreForm.setStoreId(outStoreParam.getStoreId());
        outStoreForm.setOutStoreDate(new Date());
        outStoreForm.setBusinessType(OutStoreForm.BusinessType.DRAWING);
        outStoreForm = outStoreFormService.save(outStoreForm);
        for (EntrustFormParts parts : partses) {
            OutStoreFormItem outStoreFormItem = new OutStoreFormItem();
            outStoreFormItem.setCount(parts.getCount());
            outStoreFormItem.setRelationId(parts.getParts().getId());
            outStoreFormItem.setOutStoreFormId(outStoreForm.getId());
            outStoreFormItemService.save(outStoreFormItem);
        }
    }

    /**
     * 检查库存余量，如果余量不足抛出异常
     * @param storeId 库存id
     * @param entrustForm 委托单
     */
    private void verifyStoreCount(String storeId, List<EntrustFormParts> partses) throws Exception {
        Boolean insufficient = false;
        StringBuilder stringBuilder = new StringBuilder();
        Map<String, String[]> params = new HashMap<>();
        List<Goods> goodsList;
        for (EntrustFormParts parts : partses) {
            params.clear();
            params.put("storeId", new String[]{storeId});
            params.put("relationId", new String[]{ parts.getParts().getId()});
            goodsList = goodsService.findAll(params);
            if(goodsList == null || goodsList.isEmpty()) {
                insufficient = true;
                stringBuilder.append(parts.getParts().getName());
                stringBuilder.append("，");
            } else {
                if(goodsList.get(0).getCount() < parts.getCount()) {
                    insufficient = true;
                    stringBuilder.append(parts.getParts().getName());
                    stringBuilder.append("，");
                }
            }
        }

        if(insufficient) {
            throw new BusinessException(stringBuilder + "库存不足");
        }
    }

    @Autowired
    @Lazy
    public DrawingFormServiceImpl(
            DrawingFormRepository drawingFormRepository,
            OutStoreFormService outStoreFormService,
            EntrustFormService entrustFormService,
            OutStoreFormItemService outStoreFormItemService,
            GoodsService goodsService
    ) {
        this.drawingFormRepository = drawingFormRepository;
        this.outStoreFormService = outStoreFormService;
        this.entrustFormService = entrustFormService;
        this.outStoreFormItemService = outStoreFormItemService;
        this.goodsService = goodsService;
    }
}
