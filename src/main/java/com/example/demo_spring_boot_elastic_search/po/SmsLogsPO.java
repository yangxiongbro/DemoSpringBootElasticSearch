package com.example.demo_spring_boot_elastic_search.po;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * <b><code>SmsLogsPO</code></b>
 * <p/>
 * <p>
 * <p/>
 * <b>Creation Time:</b> 2024/8/15 21:33
 *
 * @author yang xiong
 * @since DemoSpringBootElasticSearch 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SmsLogsPO {
    /**
     * 唯一ID
     */
    private String id;
    /**
     * 创建时间
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createDate;
    /**
     * 发送时间
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime sendDate;
    /**
     * 发送的长号码，如“1069886622”
     */
    private String longCode;
    /**
     * 如：13812341234
     */
    private String mobile;
    /**
     * 发送公司名称，需要分词检索
     */
    private String corpName;
    /**
     * 发送公司名称，需要分词检索
     */
    private String smsContent;
    /**
     * 短信下发状态：0 成功 1失败
     */
    private Integer state;
    /**
     * 运营商编号：1 移动 2 联通 3 电信
     */
    private Integer operatorId;
    /**
     * 省份
     */
    private String province;
    /**
     * 下发服务器IP地址
     */
    private String ipAddr;
    /**
     * 短信状态报告返回时长（秒）
     */
    private Integer replyTotal;
    /**
     * 扣费（分）
     */
    private Integer fee;
}
