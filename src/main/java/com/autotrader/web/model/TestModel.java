package com.autotrader.web.model;

import lombok.Data;

import java.util.List;

@Data
public class TestModel {
    private List<TestResult> results;
    private String overallResults;
}
