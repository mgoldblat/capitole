package com.capitole.exam.service;

import com.capitole.exam.dto.SearchResultDto;

public interface SearchService<T, R> {
  SearchResultDto<R> search(T dto);
}
