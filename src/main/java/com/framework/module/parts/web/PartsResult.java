package com.framework.module.parts.web;

import com.framework.module.parts.domain.Parts;
import com.framework.module.parts.domain.PartsImage;
import io.swagger.annotations.ApiModelProperty;

public class PartsResult extends Parts {
    @ApiModelProperty(value = "零件封面图片")
    private PartsImage partsCoverImage;
    @ApiModelProperty(value = "零件附图1")
    private PartsImage partsAttached1Image;
    @ApiModelProperty(value = "零件附图2")
    private PartsImage partsAttached2Image;
    @ApiModelProperty(value = "零件附图3")
    private PartsImage partsAttached3Image;
    @ApiModelProperty(value = "零件附图4")
    private PartsImage partsAttached4Image;

    public PartsImage getPartsCoverImage() {
        return partsCoverImage;
    }

    public void setPartsCoverImage(PartsImage partsCoverImage) {
        this.partsCoverImage = partsCoverImage;
    }

    public PartsImage getPartsAttached1Image() {
        return partsAttached1Image;
    }

    public void setPartsAttached1Image(PartsImage partsAttached1Image) {
        this.partsAttached1Image = partsAttached1Image;
    }

    public PartsImage getPartsAttached2Image() {
        return partsAttached2Image;
    }

    public void setPartsAttached2Image(PartsImage partsAttached2Image) {
        this.partsAttached2Image = partsAttached2Image;
    }

    public PartsImage getPartsAttached3Image() {
        return partsAttached3Image;
    }

    public void setPartsAttached3Image(PartsImage partsAttached3Image) {
        this.partsAttached3Image = partsAttached3Image;
    }

    public PartsImage getPartsAttached4Image() {
        return partsAttached4Image;
    }

    public void setPartsAttached4Image(PartsImage partsAttached4Image) {
        this.partsAttached4Image = partsAttached4Image;
    }
}
