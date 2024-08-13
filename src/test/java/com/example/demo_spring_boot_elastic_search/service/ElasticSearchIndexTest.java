package com.example.demo_spring_boot_elastic_search.service;

import com.example.demo_spring_boot_elastic_search.po.GoodsPO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.IndexOperations;

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
public class ElasticSearchIndexTest {
    @Autowired
    private ElasticsearchRestTemplate template;

    @Test
    public void existsIndex() {
        IndexOperations idxOpt = template.indexOps(GoodsPO.class);
        boolean idxExist = idxOpt.exists();             // 索引是否存在
        System.out.println("exists: " + idxExist);
    }

    @Test
    public void createIndex() {
        IndexOperations idxOpt = template.indexOps(GoodsPO.class);
        boolean createSuccess = idxOpt.create();        // 创建索引
        System.out.println("create: " + createSuccess);
    }

    @Test
    public void deleteIndex() {
        IndexOperations idxOpt = template.indexOps(GoodsPO.class);
        boolean deleted = idxOpt.delete();              // 删除索引
        System.out.println("delete: " + deleted);
    }

}
