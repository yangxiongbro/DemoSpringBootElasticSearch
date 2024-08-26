package com.example.demo_spring_boot_elastic_search.vo;

import co.elastic.clients.elasticsearch._types.mapping.GeoPointProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <b><code>MapVO</code></b>
 * <p/>
 * <p>
 * <p/>
 * <b>Creation Time:</b> 2024/8/26 22:14
 *
 * @author yang xiong
 * @since DemoSpringBootElasticSearch 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MapVO {
    private String name;

    private GeoPointProperty location;
}
