package com.example.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author admin
 * @version V1.0
 * @time 2019/10/30 16:00
 * @description
 */
@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_EMPTY) // 此注解可被继承 即继承此类的类此注解依然生效
@JsonIgnoreProperties(ignoreUnknown = true, value = "delFlag")
public class BasePOJO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 数据主键
     */
    private Long id;

    /**
     * 数据创建者
     */
    private Long createBy;

    /**
     * 数据创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 数据更新者
     */
    private Long updateBy;

    /**
     * 数据更新时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    /**
     * 是否删除 0未删除(默认) 1已删除
     */
    private Boolean delFlag;

    /**
     * 是否启用 0不启用 1启用(默认)
     */
    private Byte state;

    /**
     * 起始页
     */
    private Long startRow;

    /**
     * 每页显示的数据数量
     */
    private Long pageSize;
}