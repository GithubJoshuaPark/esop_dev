package com.soro.esop.service;

import com.soro.esop.entity.PathList;

import java.util.List;

public interface PathListService {
    PathList findById(Long id);
    List<PathList> findAll();
    PathList save(PathList PathList);
    PathList update(PathList PathList);
    void delete(Long id);
}
