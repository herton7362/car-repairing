package com.framework.module.entrustform.service;

import com.framework.module.dispatchform.domain.DispatchForm;
import com.framework.module.dispatchform.service.DispatchFormService;
import com.framework.module.entrustform.domain.EntrustForm;
import com.framework.module.entrustform.domain.EntrustFormItemRepository;
import com.framework.module.entrustform.domain.EntrustFormPartsRepository;
import com.framework.module.entrustform.domain.EntrustFormRepository;
import com.framework.module.entrustform.web.DispatchParam;
import com.framework.module.maintenance.domain.MaintenanceItem;
import com.framework.module.member.domain.Member;
import com.framework.module.member.service.MemberService;
import com.framework.module.orderform.service.OrderFormService;
import com.framework.module.vehicle.domain.Vehicle;
import com.framework.module.vehicle.service.VehicleService;
import com.kratos.common.AbstractCrudService;
import com.kratos.common.PageRepository;
import com.kratos.exceptions.BusinessException;
import com.kratos.module.auth.AdminThread;
import com.kratos.module.auth.UserThread;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Pattern;

@Component
@Transactional
public class EntrustFormServiceImpl extends AbstractCrudService<EntrustForm> implements EntrustFormService {
    private final EntrustFormRepository entrustFormRepository;
    private final VehicleService vehicleService;
    private final OrderFormService orderFormService;
    private final MemberService memberService;
    private final EntrustFormItemRepository entrustFormItemRepository;
    private final EntrustFormPartsRepository entrustFormPartsRepository;
    private final DispatchFormService dispatchFormService;
    @Override
    protected PageRepository<EntrustForm> getRepository() {
        return entrustFormRepository;
    }

    @Override
    public EntrustForm save(EntrustForm entrustForm) throws Exception {
        Vehicle vehicle = entrustForm.getVehicle();
        // 如果没有传入车主，则从联系人电话获取会员
        // 如果会员没有注册则直接注册会员
        if(!Pattern.matches("^1[0-9]{10}$",
                entrustForm.getContactTel())) {
            throw new BusinessException("手机号码格式不正确");
        }
        if(entrustForm.getItems() == null || entrustForm.getItems().isEmpty()) {
            throw new BusinessException("维修项目不能为空");
        }
        if(entrustForm.getPartses() == null || entrustForm.getPartses().isEmpty()) {
            throw new BusinessException("配件项目不能为空");
        }
        if(StringUtils.isNotBlank(entrustForm.getId())) {
            EntrustForm old = findOne(entrustForm.getId());
            old.getItems().forEach((item -> {
                entrustFormItemRepository.delete(item);
            }));
            old.getPartses().forEach(parts -> {
                entrustFormPartsRepository.delete(parts);
            });
        } else {
            entrustForm.setOrderNumber(orderFormService.getOutTradeNo());
            entrustForm.setCreator(AdminThread.getInstance().get());
        }
        Member member = memberService.findOneByLoginName(entrustForm.getContactTel());
        if(member == null) {
            member = new Member();
            member.setLoginName(entrustForm.getContactTel());
            member.setMobile(entrustForm.getContactTel());
            member.setName(entrustForm.getContactTel());
            member = memberService.save(member);
        }
        if(StringUtils.isBlank(vehicle.getMemberId())) {
            vehicle.setMemberId(member.getId());
        }
        validate(vehicle);

        entrustForm.setVehicle(vehicleService.save(vehicle));
        EntrustForm result = super.save(entrustForm);
        entrustForm.getItems().forEach((item -> {
            item.setEntrustForm(result);
            entrustFormItemRepository.save(item);
        }));
        entrustForm.getPartses().forEach(parts -> {
            parts.setEntrustForm(result);
            entrustFormPartsRepository.save(parts);
        });
        return result;
    }

    private void validate(Vehicle vehicle) throws Exception {
        if(StringUtils.isBlank(vehicle.getMemberId())) {
            throw new BusinessException("车主不能为空");
        }
        if(StringUtils.isBlank(vehicle.getPlateNumber())) {
            throw new BusinessException("车牌号不能为空");
        }
        if(StringUtils.isBlank(vehicle.getVehicleCategoryId())) {
            throw new BusinessException("车型不能为空");
        }
    }

    @Override
    public void dispatch(String formId, DispatchParam dispatchParam) throws Exception {
        if(StringUtils.isBlank(formId)) {
            throw new BusinessException("委托单id不能为空");
        }
        if(StringUtils.isBlank(dispatchParam.getWorkerId()) && StringUtils.isBlank(dispatchParam.getWorkingTeamId())) {
            throw new BusinessException("请至少选择维修工人、班组的其中一项");
        }
        EntrustForm entrustForm = entrustFormRepository.findOne(formId);
        if(entrustForm == null) {
            throw new BusinessException("委托单未找到");
        }
        entrustForm.setStatus(EntrustForm.Status.DISPATCHING);
        entrustFormRepository.save(entrustForm);
        DispatchForm dispatchForm = new DispatchForm();
        dispatchForm.setEntrustFormId(formId);
        dispatchForm.setEntrustFormOrderNumber(entrustForm.getOrderNumber());
        dispatchForm.setOrderNumber(orderFormService.getOutTradeNo());
        dispatchForm.setWorkerId(dispatchParam.getWorkerId());
        dispatchForm.setWorkingTeamId(dispatchParam.getWorkingTeamId());
        dispatchForm.setStatus(DispatchForm.Status.NEW);
        dispatchFormService.save(dispatchForm);
    }

    @Autowired
    @Lazy
    public EntrustFormServiceImpl(
            EntrustFormRepository entrustFormRepository,
            VehicleService vehicleService,
            OrderFormService orderFormService,
            MemberService memberService,
            EntrustFormItemRepository entrustFormItemRepository,
            EntrustFormPartsRepository entrustFormPartsRepository,
            DispatchFormService dispatchFormService
    ) {
        this.entrustFormRepository = entrustFormRepository;
        this.vehicleService = vehicleService;
        this.orderFormService = orderFormService;
        this.memberService = memberService;
        this.entrustFormItemRepository = entrustFormItemRepository;
        this.entrustFormPartsRepository = entrustFormPartsRepository;
        this.dispatchFormService = dispatchFormService;
    }
}
