package com.travely.travely.dto.store;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreJoinUsersDto {
    private long storeIdx;
    private long ownerIdx;
    private String storeName;
    private long region
}
