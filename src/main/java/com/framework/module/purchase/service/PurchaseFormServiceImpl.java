package com.framework.module.purchase.service;

import com.framework.module.entrustform.domain.EntrustForm;
import com.framework.module.orderform.service.OrderFormService;
import com.framework.module.purchase.domain.PurchaseForm;
import com.framework.module.purchase.domain.PurchaseFormPartsRepository;
import com.framework.module.purchase.domain.PurchaseFormRepository;
import com.kratos.common.AbstractCrudService;
import com.kratos.common.PageRepository;
import com.kratos.exceptions.BusinessException;
import com.kratos.module.auth.AdminThread;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class PurchaseFormServiceImpl extends AbstractCrudService<PurchaseForm> implements PurchaseFormService {
    private final PurchaseFormRepository purchaseFormRepository;
    private final PurchaseFormPartsRepository purchaseFormPartsRepository;
    private final OrderFormService orderFormService;
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

    @Autowired
    public PurchaseFormServiceImpl(
            PurchaseFormRepository purchaseFormRepository,
            PurchaseFormPartsRepository purchaseFormPartsRepository,
            OrderFormService orderFormService
    ) {
        this.purchaseFormRepository = purchaseFormRepository;
        this.purchaseFormPartsRepository = purchaseFormPartsRepository;
        this.orderFormService = orderFormService;
    }
}
