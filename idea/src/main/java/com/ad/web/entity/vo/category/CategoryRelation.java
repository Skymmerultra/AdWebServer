package com.ad.web.entity.vo.category;

import com.ad.web.entity.Category;
import lombok.Data;

@Data
public class CategoryRelation {
    private Long adPoId;
    private Category category;
}
