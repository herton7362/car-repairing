package com.framework.module.purchase.service;

import com.framework.module.entrustform.domain.EntrustForm;
import com.framework.module.entrustform.domain.EntrustFormParts;
import com.framework.module.orderform.service.OrderFormService;
import com.framework.module.purchase.domain.PurchaseForm;
import com.framework.module.purchase.domain.PurchaseFormParts;
import com.framework.module.purchase.domain.PurchaseFormPartsRepository;
import com.framework.module.purchase.domain.PurchaseFormRepository;
import com.framework.module.purchase.web.InStoreParam;
import com.framework.module.store.domain.InStoreForm;
import com.framework.module.store.domain.InStoreFormItem;
import com.framework.module.store.domain.OutStoreForm;
import com.framework.module.store.domain.OutStoreFormItem;
import com.framework.module.store.service.InStoreFormItemService;
import com.framework.module.store.service.InStoreFormService;
import com.kratos.common.AbstractCrudService;
import com.kratos.common.PageRepository;
import com.kratos.exceptions.BusinessException;
import com.kratos.module.auth.AdminThread;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Component
@Transactional
public class PurchaseFormServiceImpl extends AbstractCrudService<PurchaseForm> implements PurchaseFormService {
    private final PurchaseFormRepository purchaseFormRepository;
    private final PurchaseFormPartsRepository purchaseFormPartsRepository;
    private final OrderFormService orderFormService;
    private final InStoreFormService inStoreFormService;
    private final InStoreFormItemService inStoreFormItemService;
    @Override
    protected PageRepository<PurchaseForm> getRepository() {
        return purchaseFormRepository;
    }

    @Override
    public PurchaseForm save(PurchaseForm purchaseForm) throws Exception {
        if(purchaseForm.getPartses() == null || purchaseForm.getPartses().isEmpty()) {
            throw new BusinessException("配件项目不能为空");
        }
        if(StringUtils.isNotBlank(purchaseForm.getId())) {
            PurchaseForm old = findOne(purchaseForm.getId());
            old.getPartses().forEach(parts -> {
                purchaseFormPartsRepository.delete(parts);
            });
        } else {
            purchaseForm.setOrderNumber(orderFormService.getOutTradeNo());
            purchaseForm.setCreatorId(AdminThread.getInstance().get().getId());
        }
        PurchaseForm result = super.save(purchaseForm);
        purchaseForm.getPartses().forEach(parts -> {
            parts.setPurchaseFormId(result.getId());
            purchaseFormPartsRepository.save(parts);
        });
        return result;
    }

    @Override
    public void inStore(String formId, InStoreParam inStoreParam) throws Exception {
        if(StringUtils.isBlank(inStoreParam.getStoreId())) {
            throw new BusinessException("请指定仓库");
        }
        PurchaseForm purchaseForm = purchaseFormRepository.findOne(formId);
        purchaseForm.setStatus(PurchaseForm.Status.IN_STORE);
        purchaseFormRepository.save(purchaseForm);
        List<PurchaseFormParts> partses = purchaseForm.getPartses();
        InStoreForm inStoreForm = new InStoreForm();
        inStoreForm.setStoreId(inStoreParam.getStoreId());
        inStoreForm.setInStoreDate(new Date());
        inStoreForm.setBusinessType(InStoreForm.BusinessType.PURCHASE);
        inStoreForm = inStoreFormService.save(inStoreForm);
        for (PurchaseFormParts parts : partses) {
            InStoreFormItem inStoreFormItem = new InStoreFormItem();
            inStoreFormItem.setCount(parts.getCount());
            inStoreFormItem.setRelationId(parts.getParts().getId());
            inStoreFormItem.setInStoreFormId(inStoreForm.getId());
            inStoreFormItemService.save(inStoreFormItem);
        }
    }

    @Autowired
    @Lazy
    public PurchaseFormServiceImpl(
            PurchaseFormRepository purchaseFormRepository,
            PurchaseFormPartsRepository purchaseFormPartsRepository,
            OrderFormService orderFormService,
            InStoreFormService inStoreFormService,
            InStoreFormItemService inStoreFormItemService
    ) {
        this.purchaseFormRepository = purchaseFormRepository;
        this.purchaseFormPartsRepository = purchaseFormPartsRepository;
        this.orderFormService = orderFormService;
        this.inStoreFormService = inStoreFormService;
        this.inStoreFormItemService = inStoreFormItemService;
    }
}
