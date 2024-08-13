package com.example.demo_spring_boot_elastic_search.service;

import com.example.demo_spring_boot_elastic_search.po.GoodsPO;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * <b><code>ElasticSearchDocumentTest</code></b>
 * <p/>
 * <p>
 * <p/>
 * <b>Creation Time:</b> 2023/9/15 1:29
 *
 * @author yang xiong
 * @since DemoSpringBootElasticSearch 1.0
 */
@SpringBootTest
public class ElasticSearchDocumentTest {
    @Autowired
    private ElasticsearchRestTemplate template;

    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private String name = "小米%d Pro";
    private String info = "小米%d Pro是小米于2022年12月11日发布的手机产品；于2022年12月14日正式开售。";
    private BigDecimal price = new BigDecimal(3999.99);
    private LocalDateTime createDate = LocalDateTime.parse("2022-12-11 00:00:00", dtf);

    private GoodsPO goods = new GoodsPO(null, String.format(name, 13), String.format(info, 13), price, createDate);

    @Test
    public void createDocument() {
        goods.setId("1");
        System.out.println(goods);
        GoodsPO result = template.save(goods);
        System.out.println(result);
    }

    @Test
    public void batchCreateDocument() {
        List<GoodsPO> goodList = new ArrayList<>(5);
        for(int i = 12;i > 7; i--){
            int finalI = i;
            goodList.add(new GoodsPO(String.valueOf(finalI), String.format(name, finalI), String.format(info, finalI), price, createDate));
        }
        Iterable<GoodsPO> resultList = template.save(goodList);
        for(GoodsPO goods:resultList){
            System.out.println(goods);
        }
    }

    @Test
    public void existsDocument() {
        boolean exist = template.exists("1", GoodsPO.class);
        System.out.println(exist);
    }

    @Test
    public void queryDocument() {
        // 根据 id 查询
        GoodsPO goodsPO = template.get("1", GoodsPO.class);
        System.out.println(goodsPO);
    }

    @Test
    public void searchDocument() {
        // https://blog.csdn.net/xiao_gu_yu/article/details/137009722
        Criteria criteria = new Criteria();
        criteria.and(new Criteria("name").is("小米"));
        criteria.and(new Criteria("price").is(3999.99));

        Query query = new CriteriaQuery(criteria)
                .addSort(Sort.by(new Order(Sort.Direction.ASC, "price")))
                .addSort(Sort.by(new Order(Sort.Direction.DESC, "create_date")))
                .setPageable(PageRequest.of(0, 20));
        query.addSourceFilter(new FetchSourceFilterBuilder().withExcludes("info").build()); // 不需要查询的字段
        SearchHits<GoodsPO> searchHits = template.search(query, GoodsPO.class);
        System.out.println(searchHits.getTotalHits()); //数量
        for(SearchHit<GoodsPO> searchHit:searchHits){
            GoodsPO goods = searchHit.getContent();
            System.out.println(goods);
        }
    }

    @Test
    public void updateDocument() {
        Document document = Document.create();
        document.put("create_date", LocalDateTime.parse("2022-12-14 00:00:00", dtf));
        template.update(UpdateQuery.builder("1").withDocument(document).build(), IndexCoordinates.of("es_demo_goods"));
    }

    @Test
    public void deleteDocument() {
        goods.setId("1");
        String delete = template.delete(goods);
        System.out.println(delete);
    }
}
