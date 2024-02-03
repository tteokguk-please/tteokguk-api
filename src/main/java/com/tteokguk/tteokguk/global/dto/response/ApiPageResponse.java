package com.tteokguk.tteokguk.global.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Slf4j
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiPageResponse<T> {

    private List<T> data;
    private PageInfo pageInfo;

    @Builder
    private ApiPageResponse(Page<T> data) {
        this.data = data.getContent();
        if (data.isEmpty()) {
            this.pageInfo = PageInfo.of(0, 0);
        } else {
            this.pageInfo = PageInfo.of(data.getPageable(), data.getTotalPages());
        }
    }

    public static <T> ApiPageResponse<T> of(Page<T> data) {
        return ApiPageResponse.<T>builder()
                .data(data)
                .build();
    }

    @Getter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class PageInfo {
        private Integer page;
        private Integer size;

        @Builder
        private PageInfo(
                Pageable pageable,
                Integer page
        ) {
            this.page = page;
            this.size = pageable.getPageSize();
        }

        private PageInfo(Integer page, Integer size) {
            this.page = page;
            this.size = size;
        }

        public static PageInfo of(
                Pageable pageable,
                Integer page
        ) {
            return PageInfo.builder()
                    .pageable(pageable)
                    .page(page)
                    .build();
        }

        public static PageInfo of(Integer page, Integer size) {
            return new PageInfo(page, size);
        }
    }
}
