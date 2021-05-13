package com.pmcc.core.filters;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.PropertyPreFilter;
import com.alibaba.fastjson.serializer.SerialContext;

import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName: JsonFieldsFilter <br>
 * @Description: TODO返回Json字段过滤器
 * @Date: 2020/3/24 8:58 <br>
 * @Author: DarkBF <br>
 * @Version: 1.0 <br>
 */
public class JsonFieldsFilter implements PropertyPreFilter {

    private final Class<?> clazz;
    private final Set<String> includes = new HashSet<String>();
    private final Set<String> excludes = new HashSet<String>();
    private int maxLevel = 0;

    public JsonFieldsFilter(String... properties) {
        this(null, properties);
    }

    public JsonFieldsFilter(Class<?> clazz, String... properties) {
        super();
        this.clazz = clazz;
        for (String item : properties) {
            if (item != null) {
                this.includes.add(item);
            }
        }
    }

    public JsonFieldsFilter addExcludes(String... filters) {
        for (int i = 0; i < filters.length; i++) {
            this.getExcludes().add(filters[i]);
        }
        return this;
    }

    public JsonFieldsFilter addIncludes(String... filters) {
        for (int i = 0; i < filters.length; i++) {
            this.getIncludes().add(filters[i]);
        }
        return this;
    }

    public boolean apply(JSONSerializer serializer, Object source, String name) {
        if (source == null) {
            return true;
        }

        if (clazz != null && !clazz.isInstance(source)) {
            return true;
        }

        // 过滤带层级属性（store.book.price）
        SerialContext serialContext = serializer.getContext();
        String levelName = serialContext.toString();
        levelName = levelName + "." + name;
        levelName = levelName.replace("$.", "");
        levelName = levelName.replaceAll("\\[\\d+\\]", "");
        if (this.excludes.contains(levelName)) {
            return false;
        }

        if (maxLevel > 0) {
            int level = 0;
            SerialContext context = serializer.getContext();
            while (context != null) {
                level++;
                if (level > maxLevel) {
                    return false;
                }
                context = context.parent;
            }
        }

        if (includes.size() == 0 || includes.contains(name)) {
            return true;
        }

        return false;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public Set<String> getIncludes() {
        return includes;
    }

    public Set<String> getExcludes() {
        return excludes;
    }
}
