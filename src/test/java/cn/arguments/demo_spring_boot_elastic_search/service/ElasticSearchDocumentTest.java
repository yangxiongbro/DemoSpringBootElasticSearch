package cn.arguments.demo_spring_boot_elastic_search.service;

import cn.arguments.demo_spring_boot_elastic_search.constants.ElasticSearchConstants;
import cn.arguments.demo_spring_boot_elastic_search.vo.GoodsVO;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.bulk.BulkOperation;
import co.elastic.clients.transport.endpoints.BooleanResponse;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
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
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ElasticSearchDocumentTest {
    @Autowired
    private ElasticsearchClient client;

    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private String name = "小米%d Pro";
    private String info = "小米%d Pro是小米于2022年12月11日发布的手机产品；于2022年12月14日正式开售。";
    private BigDecimal price = new BigDecimal(3999.99);
    private LocalDateTime createDate = LocalDateTime.parse("2022-12-11 00:00:00", dtf);

    private GoodsVO goodsVO = new GoodsVO(String.format(name, 13), String.format(info, 13), price, createDate);

    @Test
    @Order(1)
    public void createDocument() throws IOException {
        IndexResponse response = client.index(request -> request
                .index(ElasticSearchConstants.DEMO_INDEX_ES_DEMO_GOODS)
                .id("1")
                .document(goodsVO));
        System.out.println(response);
    }

    @Test
    @Order(2)
    public void batchCreateDocument() throws IOException {
        List<BulkOperation> bulkOperationList = new ArrayList<>(5);
        for(int i = 12;i > 7; i--){
            int finalI = i;
            bulkOperationList.add(BulkOperation.of(o -> o.index(index -> index.document(
                    new GoodsVO(String.format(name, finalI), String.format(info, finalI), price, createDate)
            ))));
        }
        BulkResponse response = client.bulk(request -> request
                .index(ElasticSearchConstants.DEMO_INDEX_ES_DEMO_GOODS)
                .operations(bulkOperationList));
        System.out.println(response);
    }

    @Test
    @Order(3)
    public void existsDocument() throws IOException {
        BooleanResponse response = client.exists(request -> request
                .index(ElasticSearchConstants.DEMO_INDEX_ES_DEMO_GOODS)
                .id("1"));
        System.out.println(response);
    }

    @Test
    @Order(4)
    public void queryDocument() throws IOException {
        GetResponse<GoodsVO> response = client.get(request -> request
                .index(ElasticSearchConstants.DEMO_INDEX_ES_DEMO_GOODS)
                .id("1"),
                GoodsVO.class);
        System.out.println(response);
    }

    @Test
    @Order(5)
    public void updateDocument() throws IOException {
        goodsVO.setCreateDate(LocalDateTime.parse("2022-12-14 00:00:00", dtf));
        UpdateResponse<GoodsVO> response = client.update(request -> request
                .index(ElasticSearchConstants.DEMO_INDEX_ES_DEMO_GOODS)
                .id("1")
                .doc(goodsVO),
                GoodsVO.class);
        System.out.println(response);
    }

    @Test
    @Order(6)
    public void deleteDocument() throws IOException {
        DeleteResponse response = client.delete(request -> request
                        .index(ElasticSearchConstants.DEMO_INDEX_ES_DEMO_GOODS)
                        .id("1"));
        System.out.println(response);
    }
}
