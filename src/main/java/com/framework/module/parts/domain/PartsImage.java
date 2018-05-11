package com.framework.module.parts.domain;

import com.kratos.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@ApiModel("零件图片")
public class PartsImage extends BaseEntity {
    @ApiModelProperty(value = "是否封面")
    private Boolean isCover;
    @ApiModelProperty(value = "商品id")
    @Column(length = 36)
    private String partsId;
    @ApiModelProperty(value = "附件id")
    @Column(length = 36)
    private String attachmentId;

    public Boolean getCover() {
        return isCover;
    }

    public void setCover(Boolean cover) {
        isCover = cover;
    }

    public String getPartsId() {
        return partsId;
    }

    public void setPartsId(String partsId) {
        this.partsId = partsId;
    }

    public String getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(String attachmentId) {
        this.attachmentId = attachmentId;
    }
}
