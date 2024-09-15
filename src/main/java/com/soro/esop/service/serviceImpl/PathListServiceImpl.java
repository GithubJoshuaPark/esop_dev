package com.soro.esop.service.serviceImpl;

import com.soro.esop.entity.PathList;
import com.soro.esop.repository.PathListRepository;
import com.soro.esop.service.PathListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PathListServiceImpl implements PathListService {
    private final PathListRepository pathListRepository;

    @Override
    public PathList findById(Long id) {
        return pathListRepository.findById(id).orElse(null);
    }

    @Override
    public List<PathList> findAll() {
        List<PathList> pathLists = pathListRepository.findAll();
        return pathLists.isEmpty() ? null : pathLists;
    }

    @Override
    public PathList save(PathList PathList) {
        return pathListRepository.save(PathList);
    }

    @Override
    public PathList update(PathList PathList) {
        return pathListRepository.save(PathList);
    }

    @Override
    public void delete(Long id) {
        pathListRepository.deleteById(id);
    }
    
}
