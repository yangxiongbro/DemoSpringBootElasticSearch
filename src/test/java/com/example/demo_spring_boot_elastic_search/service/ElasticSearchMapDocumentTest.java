package com.example.demo_spring_boot_elastic_search.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.GeoLocation;
import co.elastic.clients.elasticsearch._types.LatLonGeoLocation;
import co.elastic.clients.elasticsearch._types.query_dsl.GeoPolygonPoints;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.example.demo_spring_boot_elastic_search.po.SmsLogsPO;
import com.example.demo_spring_boot_elastic_search.vo.MapVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Map;

/**
 * <b><code>ElasticSearchMapDocumentTest</code></b>
 * <p/>
 * <p>
 * <p/>
 * <b>Creation Time:</b> 2024/8/26 22:09
 *
 * @author yang xiong
 * @since DemoSpringBootElasticSearch 1.0
 */
@SpringBootTest
public class ElasticSearchMapDocumentTest {
    @Autowired
    private ElasticsearchClient client;

    @Test
    public void searchDocument() throws IOException {
        SearchResponse<Map> response = client.search(request -> request
                .index(ElasticSearchIndexTest.MAP_INDEX_NAME)
                .query(q -> q
                        .geoPolygon(gpq -> gpq
                                .field("location")
                                .polygon(
                                        GeoPolygonPoints.of(gpp->gpp
                                                .points(
                                                        GeoLocation.of(gl->gl.latlon(LatLonGeoLocation.of(f->f.lon(116.298916).lat(39.99878)))),
                                                        GeoLocation.of(gl->gl.latlon(LatLonGeoLocation.of(f->f.lon(116.29561).lat(39.972576)))),
                                                        GeoLocation.of(gl->gl.latlon(LatLonGeoLocation.of(f->f.lon(116.327661).lat(39.984739))))
                                                ))))),
                Map.class);
        System.out.println(response.hits().total().value()); //数量
        response.hits().hits().forEach(h -> {
            Map log = h.source();
            System.out.println(log);
        });
    }
}
