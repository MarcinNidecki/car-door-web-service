package com.mnidecki.cardoor.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
public class Mail {
    private final String mailTo;
    private final String subject;
    private final String message;
    private String toCc;


}

