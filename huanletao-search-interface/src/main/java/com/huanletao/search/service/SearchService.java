package com.huanletao.search.service;

import com.huanletao.pojo.PageResult;

import java.util.Map;

public interface SearchService {

    PageResult search(Map<String, String> map);

}
