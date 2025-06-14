package net.rewerk.webstore.util;

import net.rewerk.webstore.transport.dto.request.common.PaginatedRequestParamsDto;
import net.rewerk.webstore.transport.dto.request.common.SortedRequestParamsDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public abstract class RequestUtils {
    public static PageRequest getPageRequest(PaginatedRequestParamsDto requestDto) {
        Integer page = requestDto.getPage();
        Integer limit = requestDto.getLimit();
        return PageRequest.of(page, limit);
    }

    public static PageRequest getSortAndPageRequest(SortedRequestParamsDto requestDto) {
        Integer page = requestDto.getPage();
        Integer limit = requestDto.getLimit();
        String sort = requestDto.getSort();
        String order = requestDto.getOrder();
        return PageRequest.of(page, limit,
                sort == null ? Sort.unsorted()
                        : Sort.by(order == null || "asc".equalsIgnoreCase(order) ?
                        Sort.Direction.ASC : Sort.Direction.DESC, sort
                )
        );
    }
}
