package com.framework.module.entrustform.web;

import com.framework.module.entrustform.domain.EntrustForm;
import com.framework.module.shop.domain.Shop;

public class EntrustFormResult extends EntrustForm {
    private Shop shop;

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }
}
