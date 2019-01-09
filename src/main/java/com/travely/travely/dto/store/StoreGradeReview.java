package com.travely.travely.dto.store;

import com.travely.travely.domain.Store;
import lombok.Getter;

@Getter
public class StoreGradeReview {
    private double grade;
    private int count;

    public StoreGradeReview(Store store) {
        this.grade = Double.parseDouble(String.format("%.1f", store.getGrade()));
        ;
        this.count = store.getReviews().size();
    }
}
