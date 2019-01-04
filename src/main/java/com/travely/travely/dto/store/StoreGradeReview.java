package com.travely.travely.dto.store;

import com.travely.travely.domain.Store;
import lombok.Getter;

@Getter
public class StoreGradeReview {
    private double grade;
    private int count;

    public StoreGradeReview(Store store) {
        this.grade = store.getGrade();
        this.count = store.getRestWeeks().size();
    }
}
