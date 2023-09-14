package cn.arguments.demo_spring_boot_elastic_search.service;

import cn.arguments.demo_spring_boot_elastic_search.constants.ElasticSearchConstants;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.indices.CreateIndexResponse;
import co.elastic.clients.elasticsearch.indices.DeleteIndexResponse;
import co.elastic.clients.elasticsearch.indices.GetIndexResponse;
import co.elastic.clients.transport.endpoints.BooleanResponse;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * <b><code>ElasticSearchTest</code></b>
 * <p/>
 * <p>
 * <p/>
 * <b>Creation Time:</b> 2023/9/15 1:26
 *
 * @author yang xiong
 * @since DemoSpringBootElasticSearch 1.0
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ElasticSearchIndexTest {
    @Autowired
    private ElasticsearchClient client;

    @Test
    @Order(1)
    public void createIndex() throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("json/CreateIndex.json"), StandardCharsets.UTF_8))) {
            CreateIndexResponse response = client.indices().create(r -> r
                    .index(ElasticSearchConstants.DEMO_INDEX_ES_DEMO_GOODS)
                    .mappings(b -> b.withJson(br)));
            System.out.println(response);
        }
    }

    @Test
    @Order(2)
    public void existsIndex() throws IOException {
        BooleanResponse response = client.indices().exists(r -> r.index(ElasticSearchConstants.DEMO_INDEX_ES_DEMO_GOODS));
        System.out.println(response);
    }

    @Test
    @Order(3)
    public void queryIndex() throws IOException {
        GetIndexResponse response = client.indices().get(r -> r.index(ElasticSearchConstants.DEMO_INDEX_ES_DEMO_GOODS));
        System.out.println(response);
    }

    @Test
    @Order(4)
    public void deleteIndex() throws IOException {
        DeleteIndexResponse response = client.indices().delete(r -> r.index(ElasticSearchConstants.DEMO_INDEX_ES_DEMO_GOODS));
        System.out.println(response);
    }

}
