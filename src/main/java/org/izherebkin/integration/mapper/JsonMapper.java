package org.izherebkin.integration.mapper;

import com.alibaba.fastjson.JSON;

public class JsonMapper {

    public static String toJson(Object o) {
        return JSON.toJSONString(o);
    }

    public static <T> T fromJson(String json, Class<T> valueType) {
        return JSON.parseObject(json, valueType);
    }

}
