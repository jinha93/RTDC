package com.rtdc.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class HelloResponseDto {

    // @Getter : 선언된 모든 필드의 getter 메소드를 생성합니다.
    // @RequiredArgsConstructor : 선언된 모든 final이 붙은 필드가 포함된 생성자를 생성합니다.

    private final String name;
    private final String nickname;
}