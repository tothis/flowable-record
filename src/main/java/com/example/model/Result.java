package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.http.HttpStatus;

/**
 * @author admin
 * @version V1.0
 * @time 2019/10/29 12:59
 * @description
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Result<T> {

    private int code;

    private String message;

    private T data;

    // 成功
    public static Result success() {
        return new Result() {{
            setCode(HttpStatus.OK.value());
            setMessage(HttpStatus.OK.getReasonPhrase());
        }};
    }

    // 成功 返回'success'信息 返回自定义数据
    public static <T> Result<T> success(T data) {
        return new Result() {{
            setCode(HttpStatus.OK.value());
            setMessage(HttpStatus.OK.getReasonPhrase());
            setData(data);
        }};
    }

    // 失败
    public static Result fail() {
        return new Result() {{
            setCode(HttpStatus.BAD_REQUEST.value());
            setMessage(HttpStatus.BAD_REQUEST.getReasonPhrase());
        }};
    }

    // 失败 返回自定义错误信息
    public static Result fail(String message) {
        return new Result() {{
            setCode(HttpStatus.BAD_REQUEST.value());
            setMessage(message);
        }};
    }

    // 权限不足失败 返回'权限不足'
    public static Result unauthorized() {
        return new Result() {{
            setCode(HttpStatus.UNAUTHORIZED.value());
            setMessage(HttpStatus.UNAUTHORIZED.getReasonPhrase());
        }};
    }
}