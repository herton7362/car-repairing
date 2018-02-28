package com.framework.module.entrustform.web;

import com.framework.module.entrustform.domain.EntrustForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("收款参数")
public class PayParam {
    @ApiModelProperty(value = "应付款")
    private Double finalPay;
    @ApiModelProperty(value = "现金")
    private Double cashPay;
    @ApiModelProperty(value = "余额")
    private Double balancePay;
    @ApiModelProperty(value = "支付类型")
    private EntrustForm.PayType payType;

    public Double getFinalPay() {
        return finalPay;
    }

    public void setFinalPay(Double finalPay) {
        this.finalPay = finalPay;
    }

    public Double getCashPay() {
        return cashPay;
    }

    public void setCashPay(Double cashPay) {
        this.cashPay = cashPay;
    }

    public Double getBalancePay() {
        return balancePay;
    }

    public void setBalancePay(Double balancePay) {
        this.balancePay = balancePay;
    }

    public EntrustForm.PayType getPayType() {
        return payType;
    }

    public void setPayType(EntrustForm.PayType payType) {
        this.payType = payType;
    }
}
