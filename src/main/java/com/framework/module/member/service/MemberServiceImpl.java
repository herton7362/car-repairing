package com.framework.module.member.service;

import com.framework.module.auth.MemberThread;
import com.framework.module.member.domain.Member;
import com.framework.module.member.domain.MemberCoupon;
import com.framework.module.member.domain.MemberLevel;
import com.framework.module.member.domain.MemberRepository;
import com.framework.module.orderform.base.BaseOrderForm;
import com.framework.module.orderform.base.BaseOrderItem;
import com.framework.module.record.domain.OperationRecord;
import com.framework.module.record.service.OperationRecordService;
import com.kratos.common.AbstractCrudService;
import com.kratos.common.PageRepository;
import com.kratos.exceptions.BusinessException;
import com.kratos.module.auth.service.OauthClientDetailsService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
public class MemberServiceImpl extends AbstractCrudService<Member> implements MemberService {
    private MemberRepository repository;
    private OauthClientDetailsService oauthClientDetailsService;
    private OperationRecordService operationRecordService;

    @Override
    public Member save(Member member) throws Exception {
        if(StringUtils.isNotBlank(member.getId())) {
            Member old = repository.findOne(member.getId());
            member.setPassword(old.getPassword());
        }
        return super.save(member);
    }

    @Override
    protected PageRepository<Member> getRepository() {
        return repository;
    }

    @Override
    public Member findOneByLoginName(String loginName) {
        return repository.findOneByLoginName(loginName);
    }

    @Override
    public Member findOneByCardNo(String cardNo) {
        return repository.findOneByCardNo(cardNo);
    }

    @Override
    public void fastIncreasePoint(String id, Integer point) throws Exception {
        Member member = findOne(id);
        if(member == null) {
            throw new BusinessException(String.format("会员id:[%s]不存在" , id));
        }
        if(point == null) {
            return;
        }
        member.setPoint(increaseNumber(member.getPoint(), point));
        member.setSalePoint(increaseNumber(member.getSalePoint(), point));
        save(member);

        record(member, String.format("充值积分 %s 分", point), OperationRecord.BusinessType.FAST_INCREASE_POINT);
    }

    /**
     * 记录充值记录
     * @param member 会员
     * @param content 记录内容
     */
    private void record(Member member, String content, OperationRecord.BusinessType businessType) throws Exception {
        OperationRecord rechargeRecord = new OperationRecord();
        rechargeRecord.setMemberId(member.getId());
        rechargeRecord.setBusinessType(businessType.name());
        rechargeRecord.setClientId(MemberThread.getInstance().getClientId());
        rechargeRecord.setIpAddress(MemberThread.getInstance().getIpAddress());
        rechargeRecord.setContent(content);
        operationRecordService.save(rechargeRecord);
    }

    @Override
    public void increaseBalance(String id, Double balance) throws Exception {
        Member member = findOne(id);
        if(member == null) {
            throw new BusinessException(String.format("会员id:[%s]不存在" , id));
        }
        if(balance == null) {
            return;
        }
        member.setBalance(increaseMoney(member.getBalance(), balance));
        save(member);
    }

    @Override
    public void deductBalance(String memberId, Double amount) throws Exception {
        Member member = findOne(memberId);
        if(member == null) {
            throw new BusinessException(String.format("会员id:[%s]不存在" , memberId));
        }
        if(amount == null) {
            return;
        }
        member.setBalance(subtractMoney(member.getBalance(), amount));
        save(member);

        record(member, String.format("储值消费 %s 元", amount), OperationRecord.BusinessType.DEDUCT_BALANCE);
    }

    @Override
    public Long count() {
        return repository.count(
            (Root<Member> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder)-> {
                List<Predicate> predicate = new ArrayList<>();
                predicate.add(criteriaBuilder.equal(root.get("logicallyDeleted"), false));
                return criteriaBuilder.and(predicate.toArray(new Predicate[]{}));
            }
        );
    }

    private Integer increaseNumber(Integer sourcePoint, Integer point) {
        if(sourcePoint == null) {
            sourcePoint = 0;
        }
        return sourcePoint + point;
    }

    private Double increaseMoney(Double sourceMoney, Double money) {
        if(sourceMoney == null) {
            sourceMoney = 0D;
        }
        return new BigDecimal(sourceMoney).add(new BigDecimal(money)).doubleValue();
    }

    private Double subtractMoney(Double sourceMoney, Double money) {
        if(sourceMoney == null) {
            sourceMoney = 0D;
        }
        return new BigDecimal(sourceMoney).subtract(new BigDecimal(money)).doubleValue();
    }

    @SuppressWarnings("unchecked")
    public Member calculateMemberAccountAfterConsume(BaseOrderForm orderForm) {
        Member member = orderForm.getMember();
        Integer productPoints = 0;
        List<BaseOrderItem> items = orderForm.getItems();
        for (BaseOrderItem orderItem : items) {
            productPoints += orderItem.getPoint() * orderItem.getCount().intValue();
        }
        member.setSalePoint(subtractNumber(member.getSalePoint(), orderForm.getPoint()));
        member.setPoint(increaseNumber(member.getPoint(), productPoints));
        member.setSalePoint(increaseNumber(member.getSalePoint(), productPoints));
        member.setBalance(subtractMoney(member.getBalance(), orderForm.getBalance()));
        return member;
    }


    public void consumeModifyMemberAccount(BaseOrderForm orderForm) throws Exception {
        Member member =calculateMemberAccountAfterConsume(orderForm);
        repository.save(member);
    }

    @Override
    public Integer getAvailableCouponCount(String memberId) throws Exception {
        Member member = findOne(memberId);
        Integer count = 0;
        for (MemberCoupon memberCoupon : member.getCoupons()) {
            if(!memberCoupon.getUsed()) {
                count ++;
            }
        }
        return count;
    }

    @Override
    public MemberLevel getMemberLevel(String memberId) throws Exception {
        return null;
    }

    private Integer subtractNumber(Integer sourcePoint, Integer point) {
        if(sourcePoint == null) {
            sourcePoint = 0;
        }
        return sourcePoint - point;
    }

    @Autowired
    public MemberServiceImpl(
            MemberRepository repository,
            OauthClientDetailsService oauthClientDetailsService,
            OperationRecordService operationRecordService
    ) {
        this.repository = repository;
        this.oauthClientDetailsService = oauthClientDetailsService;
        this.operationRecordService = operationRecordService;
    }
}
