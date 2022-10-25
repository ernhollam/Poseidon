package com.nnk.springboot.services;

import com.nnk.springboot.constants.Pagination;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class PaginationService {
    public PageImpl<?> getPaginatedList(Pageable pageable, List<?> rawList) {
        // Configure pagination parameters
        int                        pageSize    = Pagination.DEFAULT_SIZE;
        int                        currentPage = pageable.getPageNumber();
        int                        startItem   = currentPage * pageSize;
        List<?> list;

        if (rawList.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, rawList.size());
            list = rawList.subList(startItem, toIndex);
        }

        return new PageImpl<>(list,
                              PageRequest.of(currentPage, pageSize),
                              rawList.size());
    }
}
