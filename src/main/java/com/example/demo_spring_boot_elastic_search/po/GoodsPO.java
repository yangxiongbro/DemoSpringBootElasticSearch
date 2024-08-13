package com.example.demo_spring_boot_elastic_search.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <b><code>GoodsPO</code></b>
 * <p/>
 * <p>
 * <p/>
 * <b>Creation Time:</b> 2024/8/12 23:02
 *
 * @author yang xiong
 * @since DemoSpringBootElasticSearch 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "es_demo_goods")
public class GoodsPO {
    @Id
    private String id;

    /**
     * 商品名
     */
    @Field(type = FieldType.Text)
    private String name;

    /**
     * 商品信息
     */
    @Field(type = FieldType.Text)
    private String info;

    /**
     * 商品价格
     */
    @Field(type = FieldType.Scaled_Float)
    private BigDecimal price;

    /**
     * 商品日期
     */
    @Field(name = "create_date", type = FieldType.Date, format = {DateFormat.date_hour_minute_second})
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createDate;
}
