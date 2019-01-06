package com.travely.travely.dto.iAmPort;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class IAmPortRequest {
    private String imp_uid;
    private String merchant_uid;

    @Builder
    public IAmPortRequest(String imp_uid, String merchant_uid) {
        this.imp_uid = imp_uid;
        this.merchant_uid = merchant_uid;
    }
}
