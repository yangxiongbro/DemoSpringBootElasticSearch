package cn.arguments.demo_spring_boot_elastic_search.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <b><code>GoodsVO</code></b>
 * <p/>
 * 商品 VO
 * <p/>
 * <b>Creation Time:</b> 2023/9/15 1:21
 *
 * @author yang xiong
 * @since DemoSpringBootElasticSearch 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsVO {

    /**
     * 商品名
     */
    private String name;

    /**
     * 商品信息
     */
    private String info;

    /**
     * 商品价格
     */
    private BigDecimal price;

    /**
     * 商品日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createDate;
}
