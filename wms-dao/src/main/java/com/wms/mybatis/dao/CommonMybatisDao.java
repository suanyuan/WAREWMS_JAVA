package com.wms.mybatis.dao;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: andy.qu
 * Date: 2019/7/8
 */
public interface CommonMybatisDao {
    void getIdSequence(Map<String, Object> map);
}