package com.zhangbin.convention.fastjson;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;

/**
 * @author zhangbin
 * @Type ExtendFastJsonConfig
 * @Desc
 * @date 2019-04-10
 * @Version V1.0
 */
public class ExtendFastJsonConfig extends FastJsonConfig {

    public ExtendFastJsonConfig() {
        super();
        setSerializerFeatures(
                // 消除对同一对象重复引用的优化
                SerializerFeature.DisableCircularReferenceDetect,
                // TODO: 序列化的时候会导致OOM。
                SerializerFeature.BrowserCompatible,
                // 不隐藏为空的字段
                SerializerFeature.IgnoreNonFieldGetter,
                // map为Null，置为{}
                SerializerFeature.WriteMapNullValue,
                // Long、Integer、Short等number类型为Null，置为0
                SerializerFeature.WriteNullNumberAsZero,
                // Boolean为Null，置为false
                SerializerFeature.WriteNullBooleanAsFalse,
                // List为Null，置为[]
                SerializerFeature.WriteNullListAsEmpty,
                // String为Null，置为""
                SerializerFeature.WriteNullStringAsEmpty
        );

        setSerializeFilters(new MaskFastJsonValueFilter());
    }
}
