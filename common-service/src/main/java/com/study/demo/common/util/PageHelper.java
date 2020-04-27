package com.study.demo.common.util;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * @Author: Lon
 * @Date: 2020/4/26 16:32
 * @Description: 分页工具类
 */
@Getter
@Setter
@NoArgsConstructor
public class PageHelper {

    @NonNull
    private Integer page = 1;//当前页

    @NonNull
    private Integer size = 10;//每页总数

    private Integer totalPageNum;//查询总页数

    private Long totalCount;//查询总数

    private List<?> data;//当前查询数据

    /**
     * 构造分页查询参数pageable
     *
     * @return
     */
    public Pageable pageable() {
        return PageRequest.of(page - 1, size);
    }

    /**
     * 构造分页查询参数pageable
     * @param sort 排序规则
     * @return
     */
    public Pageable pageable(Sort sort) {
        return PageRequest.of(page - 1, size, sort);
    }

    /**
     * 设置分页查询返回参数
     *
     * @param page
     */
    public void queryResult(Page page) {
        this.setData(page.getContent());
        this.setTotalPageNum(page.getTotalPages());
        this.setTotalCount(page.getTotalElements());
    }
}
