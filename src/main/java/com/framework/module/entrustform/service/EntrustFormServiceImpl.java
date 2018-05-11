package com.framework.module.entrustform.service;

import com.framework.module.auth.MemberThread;
import com.framework.module.dispatchform.domain.DispatchForm;
import com.framework.module.dispatchform.service.DispatchFormService;
import com.framework.module.entrustform.domain.*;
import com.framework.module.entrustform.web.DispatchParam;
import com.framework.module.entrustform.web.EntrustFormResult;
import com.framework.module.entrustform.web.PayParam;
import com.framework.module.maintenance.domain.MaintenanceItem;
import com.framework.module.maintenance.domain.MaintenanceItemParts;
import com.framework.module.member.domain.Member;
import com.framework.module.member.service.MemberService;
import com.framework.module.orderform.domain.OrderItem;
import com.framework.module.orderform.service.OrderFormService;
import com.framework.module.parts.domain.Parts;
import com.framework.module.product.domain.Product;
import com.framework.module.record.domain.OperationRecord;
import com.framework.module.record.service.OperationRecordService;
import com.framework.module.shop.domain.Shop;
import com.framework.module.shop.service.ShopService;
import com.framework.module.vehicle.domain.Vehicle;
import com.framework.module.vehicle.service.VehicleService;
import com.kratos.common.AbstractCrudService;
import com.kratos.common.PageRepository;
import com.kratos.common.PageResult;
import com.kratos.exceptions.BusinessException;
import com.kratos.module.auth.AdminThread;
import com.kratos.module.auth.UserThread;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private final ShopService shopService;
    private final OperationRecordService operationRecordService;
    @Override
    protected PageRepository<EntrustForm> getRepository() {
        return entrustFormRepository;
    }

    @Override
    public PageResult<EntrustForm> findAll(PageRequest pageRequest, Map<String, String[]> param) throws Exception {
        if(param.containsKey("all") && param.get("all")[0].equals("true")) {
            return new PageResult<>(entrustFormRepository.findAll(this.getSpecificationForAllEntities(param), pageRequest));
        } else {
            return new PageResult<>(entrustFormRepository.findAll(this.getSpecification(param), pageRequest));
        }
    }

    @Override
    public List<EntrustForm> findAll(Map<String, String[]> param) throws Exception {
        if(param.containsKey("all") && param.get("all")[0].equals("true")) {
            return entrustFormRepository.findAll(this.getSpecificationForAllEntities(param));
        } else {
            return entrustFormRepository.findAll(this.getSpecification(param));
        }
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
            entrustForm.setCreatorId(UserThread.getInstance().get().getId());
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

    @Override
    public void pay(String formId, PayParam payParam) throws Exception {
        if(StringUtils.isBlank(formId)) {
            throw new BusinessException("委托单id不能为空");
        }
        EntrustForm.PayType[] payType = EntrustForm.PayType.values();
        Boolean hasPayType = false;
        for (EntrustForm.PayType type : payType) {
            if(type == payParam.getPayType()) {
                hasPayType = true;
            }
        }
        if(!hasPayType) {
            throw new BusinessException("payType 不正确或不能为空");
        }
        EntrustForm entrustForm = entrustFormRepository.findOne(formId);
        Member member = memberService.findOne(entrustForm.getVehicle().getMemberId());
        if(member != null) {
            if(member.getBalance() == null) {
                member.setBalance(0D);
            }
            if(member.getBalance() < payParam.getBalancePay()) {
                throw new BusinessException("会员余额不足");
            }
            memberService.deductBalance(member.getId(), payParam.getBalancePay());
        }

        entrustForm.setPayType(payParam.getPayType());
        entrustForm.setCashPay(payParam.getCashPay());
        entrustForm.setBalancePay(payParam.getBalancePay());
        entrustForm.setFinalPay(payParam.getFinalPay());
        entrustForm.setPayStatus(EntrustForm.PayStatus.PAYED);
        entrustFormRepository.save(entrustForm);
    }

    @Override
    public void finish(String formId) throws Exception {
        if(StringUtils.isBlank(formId)) {
            throw new BusinessException("委托单id不能为空");
        }
        EntrustForm entrustForm = entrustFormRepository.findOne(formId);
        entrustForm.setStatus(EntrustForm.Status.FINISHED);
        entrustFormRepository.save(entrustForm);
    }

    @Override
    public PageResult<EntrustFormResult> findAllTranslated(PageRequest pageRequest, Map<String, String[]> param) throws Exception {
        PageResult<EntrustForm> page  = findAll(pageRequest, param);
        PageResult<EntrustFormResult> pageResult = new PageResult<>();
        pageResult.setSize(page.getSize());
        pageResult.setTotalElements(page.getTotalElements());
        pageResult.setContent(translateResults(page.getContent()));
        return pageResult;
    }

    @Override
    public List<EntrustFormResult> findAllTranslated(Map<String, String[]> param) throws Exception {
        return translateResults(findAll(param));
    }

    @Override
    public EntrustFormResult getOneTranslated(String id) throws Exception {
        return translateResult(findOne(id));
    }

    @Override
    public void confirm(String formId) throws Exception {
        if(StringUtils.isBlank(formId)) {
            throw new BusinessException("委托单id不能为空");
        }
        EntrustForm entrustForm = entrustFormRepository.findOne(formId);
        entrustForm.setStatus(EntrustForm.Status.CONFIRM);
        entrustFormRepository.save(entrustForm);
    }

    @Override
    public Map<String, Integer> getOrderCounts(String creatorId) {
        EntrustForm.Status[] statuses = EntrustForm.Status.values();
        Map<String, Integer> result = new HashMap<>();
        for (EntrustForm.Status status : statuses) {
            result.put(status.name(), entrustFormRepository.countByStatusAndMemberId(status, creatorId));
        }
        return result;
    }

    @Override
    public void payed(String outTradeNo) throws Exception {
        Map<String, String[]> param = new HashMap<>();
        param.put("orderNumber", new String[]{outTradeNo});
        List<EntrustForm> entrustForms = findAll(param);
        if(entrustForms != null && !entrustForms.isEmpty()) {
            EntrustForm entrustForm = entrustForms.get(0);
            entrustForm.setPayStatus(EntrustForm.PayStatus.PAYED);
            entrustFormRepository.save(entrustForm);
            consumeModifyMemberAccount(entrustForm);
        }
    }

    /**
     * 消费修改账户余额
     * @param entrustForm 订单
     */
    private void consumeModifyMemberAccount(EntrustForm entrustForm) throws Exception {
        Member member = memberService.findOne(entrustForm.getCreatorId());
        if(entrustForm.getBalancePay() != null) {
            Double balance = member.getBalance();
            if(member.getBalance() == null) {
                balance = 0d;
            }
            member.setBalance(subtractMoney(balance, entrustForm.getBalancePay()));
        }

        memberService.save(member);
        recordConsume(member, entrustForm.getCashPay(), entrustForm.getBalancePay(), 0, entrustForm.getItems());
    }

    /**
     * 记录消费记录
     * @param member 会员
     * @param items 消费项
     */
    private void recordConsume(Member member, Double cash, Double balance, Integer point, List<EntrustFormItem> items) throws Exception {
        OperationRecord rechargeRecord = new OperationRecord();
        rechargeRecord.setMemberId(member.getId());
        rechargeRecord.setBusinessType(OperationRecord.BusinessType.CONSUME.name());
        rechargeRecord.setClientId(MemberThread.getInstance().getClientId());
        rechargeRecord.setIpAddress(MemberThread.getInstance().getIpAddress());
        StringBuilder content = new StringBuilder();
        content.append(String.format("现金消费 %s 元，余额消费 %s 元，积分消费 %s 分", cash, balance, point));
        content.append("  消费项：");
        items.forEach(item -> {
            MaintenanceItem product = item.getMaintenanceItem();
            content.append(product.getName());
            content.append(" x 1");
            content.append(",");
            List<MaintenanceItemParts> parts = product.getPartses();
            parts.forEach(p->{
                content.append(p.getParts().getName());
                content.append(" x");
                content.append(p.getCount());
                content.append(",");
            });
        });
        content.deleteCharAt(content.length() - 1);
        rechargeRecord.setContent(content.toString());
        operationRecordService.save(rechargeRecord);
    }
    private Integer subtractNumber(Integer sourcePoint, Integer point) {
        if(sourcePoint == null) {
            sourcePoint = 0;
        }
        return sourcePoint - point;
    }

    private Double subtractMoney(Double sourceMoney, Double money) {
        if(sourceMoney == null) {
            sourceMoney = 0D;
        }
        BigDecimal sp = new BigDecimal(sourceMoney);
        return sp.subtract(new BigDecimal(money)).doubleValue();
    }

    private EntrustFormResult translateResult(EntrustForm entrustForm) throws Exception {
        EntrustFormResult entrustFormResult = new EntrustFormResult();
        BeanUtils.copyProperties(entrustForm, entrustFormResult);
        Map<String, String[]> param = new HashMap<>();
        param.put("clientId", new String[] {entrustForm.getClientId()});
        List<Shop> shops = shopService.findAllEveryEntities(param);
        if(shops != null && !shops.isEmpty()) {
            entrustFormResult.setShop(shops.get(0));
        }

        return entrustFormResult;
    }

    private List<EntrustFormResult> translateResults(List<EntrustForm> entrustForms) throws Exception {
        List<EntrustFormResult> entrustFormResults = new ArrayList<>();
        for (EntrustForm entrustForm : entrustForms) {
            entrustFormResults.add(this.translateResult(entrustForm));
        }
        return entrustFormResults;
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
            DispatchFormService dispatchFormService,
            ShopService shopService,
            OperationRecordService operationRecordService
    ) {
        this.entrustFormRepository = entrustFormRepository;
        this.vehicleService = vehicleService;
        this.orderFormService = orderFormService;
        this.memberService = memberService;
        this.entrustFormItemRepository = entrustFormItemRepository;
        this.entrustFormPartsRepository = entrustFormPartsRepository;
        this.dispatchFormService = dispatchFormService;
        this.shopService = shopService;
        this.operationRecordService = operationRecordService;
    }
}
