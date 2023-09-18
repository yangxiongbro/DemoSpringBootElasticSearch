package com.example.demo_spring_boot_elastic_search.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <b><code>ElasticSearchConfig</code></b>
 * <p/>
 * ES配置类
 * <p/>
 * <b>Creation Time:</b> 2023/9/15 1:17
 *
 * @author yang xiong
 * @since DemoSpringBootElasticSearch 1.0
 */
@Configuration
public class ElasticSearchConfig {
    @Bean
    public ElasticsearchClient elasticsearchClient(){
        RestClient client = RestClient
                .builder(new HttpHost("192.168.31.101", 9200, "http"))
                .build();
        ElasticsearchTransport transport = new RestClientTransport(client, new JacksonJsonpMapper());
        return new ElasticsearchClient(transport);
    }
}
