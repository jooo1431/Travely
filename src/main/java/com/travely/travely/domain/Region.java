package com.travely.travely.domain;

import com.travely.travely.config.CommonConfig;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Region {
    private Long regionIdx;
    private String regionName;

    private List<Store> stores;

    @Builder
    public Region(Long regionIdx, String regionName) {
        this.regionIdx = regionIdx;
        this.regionName = regionName;
    }

    public List<Store> getStores() {
        return CommonConfig.getCheckedList(stores);
    }
}
