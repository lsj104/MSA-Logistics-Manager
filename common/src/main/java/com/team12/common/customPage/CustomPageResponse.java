package com.team12.common.customPage;

import com.team12.common.exception.response.CommonResponse;
import com.team12.common.exception.response.ErrorResponse;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
public class CustomPageResponse<T> {
    private List<T> data;
    private PageInfo pageInfo;

    @Getter
    @Setter
    public static class PageInfo {
        private long totalItem;
        private int pageItemSize;
        private int currentPage;
        private int totalPage;
        private boolean hasPrev;
        private boolean hasNext;
    }

    public CustomPageResponse(Page<T> page) {
        this.data = page.getContent();

        PageInfo pageInfo = new PageInfo();
        pageInfo.setTotalItem(page.getTotalElements());
        pageInfo.setPageItemSize(page.getSize());
        pageInfo.setCurrentPage(page.getNumber() + 1);
        pageInfo.setTotalPage(page.getTotalPages());
        pageInfo.setHasPrev(page.hasPrevious());
        pageInfo.setHasNext(page.hasNext());

        this.pageInfo = pageInfo;
    }
}
