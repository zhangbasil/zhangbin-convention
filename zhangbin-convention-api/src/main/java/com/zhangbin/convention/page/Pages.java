package com.zhangbin.convention.page;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author zhangbin
 * @Type Pages
 * @Desc
 * @date 2019-04-08
 * @Version V1.0
 */
public final class Pages {

    public static <T> Page<T> page(PageQuery pageQuery, long totalCount, List<T> results) {
        return new DefaultPage<>(pageQuery, totalCount, results);
    }

    public static <T, R> Page<R> page(PageQuery pageQuery, long totalCount, List<T> results,
                                      Function<T, R> converter) {
        List<R> data = Objects.isNull(results) ?
                Collections.emptyList() : results.stream().map(converter).collect(Collectors.toList());
        return page(pageQuery, totalCount, data);
    }

}
