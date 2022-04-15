package com.hs.blog.service;

import com.hs.blog.vo.CategoryVo;
import com.hs.blog.vo.Result;

public interface CategoryService {

    CategoryVo findCategoryById(Long categoryId);

    Result findAll();

    Result findAllDetail();

    Result categoryDetailById(Long id);
}
