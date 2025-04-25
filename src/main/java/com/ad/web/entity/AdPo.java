package com.ad.web.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * 广告位信息表
 * @TableName ad_po_info
 */
@Data
public class AdPo {
    /**
     * 广告位id
     */
    private Long id;

    /**
     * 广告位名称
     */
    private String name;

    /**
     * 广告位介绍
     */
    private String content;

    /**
     * 广告位价格
     */
    private Long price;

    /**
     * 广告位点击次数
     */
    private Long clickNum;

    /**
     * 广告位平台
     */
    private String platform;
    /**
     * 广告位大类
     */
    private String type;
    /**
     * 创建时间
     */
    @JsonIgnore
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonIgnore
    private Date updateTime;

    /**
     * 是否删除
     */
    @JsonIgnore
    private Integer isDeleted;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        AdPo other = (AdPo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()))
            && (this.getPrice() == null ? other.getPrice() == null : this.getPrice().equals(other.getPrice()))
            && (this.getClickNum() == null ? other.getClickNum() == null : this.getClickNum().equals(other.getClickNum()))
            && (this.getPlatform() == null ? other.getPlatform() == null : this.getPlatform().equals(other.getPlatform()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
        result = prime * result + ((getPrice() == null) ? 0 : getPrice().hashCode());
        result = prime * result + ((getClickNum() == null) ? 0 : getClickNum().hashCode());
        result = prime * result + ((getPlatform() == null) ? 0 : getPlatform().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", content=").append(content);
        sb.append(", price=").append(price);
        sb.append(", clickNum=").append(clickNum);
        sb.append(", platform=").append(platform);
        sb.append(", type=").append(type);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append("]");
        return sb.toString();
    }
}