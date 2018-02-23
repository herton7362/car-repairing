package com.framework.module.store.service;

import com.framework.module.store.domain.OutStoreForm;
import com.framework.module.store.domain.OutStoreFormItem;
import com.framework.module.store.domain.OutStoreFormRepository;
import com.kratos.common.AbstractCrudService;
import com.kratos.common.PageRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Transactional
public class OutStoreFormServiceImpl extends AbstractCrudService<OutStoreForm> implements OutStoreFormService {
    private final OutStoreFormRepository outStoreFormRepository;
    private final OutStoreFormItemService outStoreFormItemService;
    private final StoreService storeService;
    @Override
    protected PageRepository<OutStoreForm> getRepository() {
        return outStoreFormRepository;
    }

    @Override
    public OutStoreForm save(OutStoreForm outStoreForm) throws Exception {
        OutStoreForm result = super.save(outStoreForm);
        if(StringUtils.isBlank(outStoreForm.getId())) {
            outStoreForm.setOutStoreDate(new Date());
            Map<String, String[]> param = new HashMap<>();
            param.put("outStoreFormId", new String[]{result.getId()});
            List<OutStoreFormItem> outStoreFormItems = outStoreFormItemService.findAll(param);
            for (OutStoreFormItem outStoreFormItem : outStoreFormItems) {
                storeService.outStore(outStoreForm.getStoreId(), outStoreFormItem.getRelationId(), outStoreFormItem.getCount());
            }
        }

        return result;
    }

    @Autowired
    public OutStoreFormServiceImpl(
            OutStoreFormRepository outStoreFormRepository,
            StoreService storeService,
            OutStoreFormItemService outStoreFormItemService
    ) {
        this.outStoreFormRepository = outStoreFormRepository;
        this.storeService = storeService;
        this.outStoreFormItemService = outStoreFormItemService;
    }
}
