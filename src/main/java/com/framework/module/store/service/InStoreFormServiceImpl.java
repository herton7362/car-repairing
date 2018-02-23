package com.framework.module.store.service;

import com.framework.module.store.domain.*;
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
public class InStoreFormServiceImpl extends AbstractCrudService<InStoreForm> implements InStoreFormService {
    private final InStoreFormRepository inStoreFormRepository;
    private final StoreService storeService;
    private final InStoreFormItemService inStoreFormItemService;
    @Override
    protected PageRepository<InStoreForm> getRepository() {
        return inStoreFormRepository;
    }

    @Override
    public InStoreForm save(InStoreForm inStoreForm) throws Exception {
        InStoreForm result = super.save(inStoreForm);
        if(StringUtils.isBlank(inStoreForm.getId())) {
            inStoreForm.setInStoreDate(new Date());
            Map<String, String[]> param = new HashMap<>();
            param.put("inStoreFormId", new String[]{result.getId()});
            List<InStoreFormItem> inStoreFormItems = inStoreFormItemService.findAll(param);
            for (InStoreFormItem inStoreFormItem : inStoreFormItems) {
                storeService.inStore(inStoreForm.getStoreId(), inStoreFormItem.getRelationId(), inStoreFormItem.getCount());
            }
        }
        return result;
    }

    @Autowired
    public InStoreFormServiceImpl(
            InStoreFormRepository inStoreFormRepository,
            StoreService storeService,
            InStoreFormItemService inStoreFormItemService
    ) {
        this.inStoreFormRepository = inStoreFormRepository;
        this.storeService = storeService;
        this.inStoreFormItemService = inStoreFormItemService;
    }
}
