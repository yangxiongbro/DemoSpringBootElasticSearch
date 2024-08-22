package com.example.demo_spring_boot_elastic_search.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.Time;
import co.elastic.clients.elasticsearch._types.aggregations.*;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.bulk.BulkOperation;
import co.elastic.clients.json.JsonData;
import co.elastic.clients.transport.endpoints.BooleanResponse;
import com.example.demo_spring_boot_elastic_search.po.SmsLogsPO;
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

    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    private String name = "小米%d Pro";
    private String info = "小米%d Pro是小米于2022年12月11日发布的手机产品；于2022年12月14日正式开售。";
    private BigDecimal price = new BigDecimal(3999.99);
    private LocalDateTime createDate = LocalDateTime.parse("2022-12-11 00:00:00.000", dtf);

    private SmsLogsPO SmsLogs01 = new SmsLogsPO(null, LocalDateTime.parse("2020-04-17 23:36:33.030", dtf), LocalDateTime.parse("2020-04-17 23:36:33.030", dtf), "10690000988", "13700000001", "途虎养车", "【途虎养车】亲爱的刘红先生/女士，您在途虎购买的货品(单号TH1234526)已到指定安装店多日，现需与您确认订单的安装情况，请点击链接按实际情况选择（此链接有效期为72小时）。您也可以登录途虎APP进入“我的-待安装订单”进行预约安装。若您在服务过程中有任何疑问，请致电400-111-8886向途虎咨询。", 0, 1, "上海", "10.126.2.9", 10, 3);
    private SmsLogsPO SmsLogs02 = new SmsLogsPO(null, LocalDateTime.parse("2020-04-17 23:36:33.073", dtf), LocalDateTime.parse("2020-04-17 23:36:33.073", dtf), "10690000988", "18600000001", "盒马鲜生", "【盒马】您尾号7775678的订单已开始配送，请在您指定的时间收货不要走开哦~配送员：王五，电话：13800000001", 0, 2, "上海", "10.126.2.9", 15, 5);
    private SmsLogsPO SmsLogs03 = new SmsLogsPO(null, LocalDateTime.parse("2020-04-17 23:36:33.073", dtf), LocalDateTime.parse("2020-04-17 23:36:33.073", dtf), "10690000988", "15300000000", "滴滴打车", "【滴滴单车平台】专属限时福利!青桔/小蓝月卡立享5折，特惠畅骑30天。戳 https://xxxxxx退订TD", 1, 3, "上海", "10.126.2.8", 50, 7);
    private SmsLogsPO SmsLogs04 = new SmsLogsPO(null, LocalDateTime.parse("2020-04-17 23:36:33.073", dtf), LocalDateTime.parse("2020-04-17 23:36:33.073", dtf), "10690000988", "18000000001", "滴滴打车", "【滴滴单车平台】专属限时福利!青桔/小蓝月卡立享5折，特惠畅骑30天。戳 https://xxxxxx退订TD", 1, 3, "武汉", "10.126.2.8", 50, 7);
    private SmsLogsPO SmsLogs05 = new SmsLogsPO(null, LocalDateTime.parse("2020-04-17 23:36:33.073", dtf), LocalDateTime.parse("2020-04-17 23:36:33.073", dtf), "10690000988", "13900000000", "招商银行", "【招商银行】尊贵的李四先生，恭喜您获得华为P30 Pro抽奖资格，还可领100元打车红包，仅限1天", 0, 1, "上海", "10.126.2.8", 50, 8);
    private SmsLogsPO SmsLogs06 = new SmsLogsPO(null, LocalDateTime.parse("2020-04-17 23:36:33.073", dtf), LocalDateTime.parse("2020-04-17 23:36:33.073", dtf), "10690000988", "13600000000", "中国移动", "【北京移动】尊敬的客户137****0000，5月话费账单已送达您的139邮箱，点击查看账单详情 http://y.10086.cn/；回Q关闭通知，关注“中国移动139邮箱”微信随时查账单【中国移动139邮箱】", 0, 1, "武汉", "10.126.2.8", 60, 4);
    private SmsLogsPO SmsLogs07 = new SmsLogsPO(null, LocalDateTime.parse("2020-04-17 23:36:33.073", dtf), LocalDateTime.parse("2020-04-17 23:36:33.073", dtf), "10690000988", "13990000001", "招商银行", "【招商银行】尊贵的李四先生，恭喜您获得华为P30 Pro抽奖资格，还可领100元打车红包，仅限1天", 0, 1, "武汉", "10.126.2.8", 50, 8);
    private SmsLogsPO SmsLogs08 = new SmsLogsPO(null, LocalDateTime.parse("2020-04-17 23:36:33.073", dtf), LocalDateTime.parse("2020-04-17 23:36:33.073", dtf), "10690000988", "13990001234", "中国银行", "【北京移动】尊敬的客户137****1234，8月话费账单已送达您的126邮箱，点击查看账单详情 http://y.10086.cn/；回Q关闭通知，关注“中国移动126邮箱”微信随时查账单【中国移动126邮箱】", 0, 1, "山西", "10.126.2.8", 60, 4);
    private SmsLogsPO SmsLogs09 = new SmsLogsPO(null, LocalDateTime.parse("2020-04-17 23:36:33.073", dtf), LocalDateTime.parse("2020-04-17 23:36:33.073", dtf), "10690000988", "13800000000", "途虎养车", "【途虎养车】亲爱的张三先生/女士，您在途虎购买的货品(单号TH1234526)已到指定安装店多日，现需与您确认订单的安装情况，请点击链接按实际情况选择（此链接有效期为72小时）。您也可以登录途虎APP进入“我的-待安装订单”进行预约安装。若您在服务过程中有任何疑问，请致电400-111-8886向途虎咨询。", 0, 1, "北京", "10.126.2.9", 10, 3);
    private SmsLogsPO SmsLogs10 = new SmsLogsPO(null, LocalDateTime.parse("2020-04-17 23:36:33.073", dtf), LocalDateTime.parse("2020-04-17 23:36:33.073", dtf), "10690000988", "13100000000", "盒马鲜生", "【盒马】您尾号12345678的订单已开始配送，请在您指定的时间收货不要走开哦~配送员：刘三，电话：13800000000", 0, 2, "北京", "10.126.2.9", 15, 5);
    private SmsLogsPO SmsLogs11 = new SmsLogsPO(null, LocalDateTime.parse("2020-04-17 23:36:33.073", dtf), LocalDateTime.parse("2020-04-17 23:36:33.073", dtf), "10690000988", "13700000000", "中国平安保险有限公司", "【中国平安】奋斗时代，更需要健康的身体。中国平安为您提供多重健康保障，在奋斗之路上为您保驾护航。退订请回复TD", 0, 1, "武汉", "10.126.2.8", 18, 5);
    private SmsLogsPO SmsLogs12 = new SmsLogsPO(null, LocalDateTime.parse("2020-04-17 23:36:33.073", dtf), LocalDateTime.parse("2020-04-17 23:36:33.073", dtf), "10690000988", "13990000002", "招商银行", "【招商银行】尊贵的王五先生，恭喜您获得iphone 56抽奖资格，还可领5元打车红包，仅限100天", 0, 1, "武汉", "10.126.2.8", 18, 5);

    @Test
    @Order(1)
    public void createDocument() throws IOException {
        IndexResponse response = client.index(request -> request
                .index(ElasticSearchIndexTest.INDEX_NAME)
                .id("1")
                .document(SmsLogs01));
        System.out.println(response);
    }

    @Test
    @Order(2)
    public void batchCreateDocument() throws IOException {
        List<BulkOperation> bulkOperationList = new ArrayList<>(12);
        bulkOperationList.add(BulkOperation.of(o -> o.index(index -> index.document(SmsLogs01))));
        bulkOperationList.add(BulkOperation.of(o -> o.index(index -> index.document(SmsLogs02))));
        bulkOperationList.add(BulkOperation.of(o -> o.index(index -> index.document(SmsLogs03))));
        bulkOperationList.add(BulkOperation.of(o -> o.index(index -> index.document(SmsLogs04))));
        bulkOperationList.add(BulkOperation.of(o -> o.index(index -> index.document(SmsLogs05))));
        bulkOperationList.add(BulkOperation.of(o -> o.index(index -> index.document(SmsLogs06))));
        bulkOperationList.add(BulkOperation.of(o -> o.index(index -> index.document(SmsLogs07))));
        bulkOperationList.add(BulkOperation.of(o -> o.index(index -> index.document(SmsLogs08))));
        bulkOperationList.add(BulkOperation.of(o -> o.index(index -> index.document(SmsLogs09))));
        bulkOperationList.add(BulkOperation.of(o -> o.index(index -> index.document(SmsLogs10))));
        bulkOperationList.add(BulkOperation.of(o -> o.index(index -> index.document(SmsLogs11))));
        bulkOperationList.add(BulkOperation.of(o -> o.index(index -> index.document(SmsLogs12))));
        BulkResponse response = client.bulk(request -> request
                .index(ElasticSearchIndexTest.INDEX_NAME)
                .operations(bulkOperationList));
        System.out.println(response);
    }

    @Test
    @Order(3)
    public void existsDocument() throws IOException {
        BooleanResponse response = client.exists(request -> request
                .index(ElasticSearchIndexTest.INDEX_NAME)
                .id("1"));
        System.out.println(response);
    }

    @Test
    @Order(4)
    public void queryDocument() throws IOException {
        // 根据 id 查询
        GetResponse<SmsLogsPO> response = client.get(request -> request
                        .index(ElasticSearchIndexTest.INDEX_NAME)
                        .id("1u9_VpEBCrSApDIskHH3"),
                SmsLogsPO.class);
        System.out.println(response);
    }

    @Test
    @Order(4)
public void searchDocument() throws IOException {
//        Query nameQuery = MatchQuery.of(m -> m.field("name").query("小米15"))._toQuery();
//        Query priceQuery = MatchQuery.of(m -> m.field("price").query(3999.99))._toQuery();
//        List<FieldValue> provinceList = new ArrayList<>(2);
//        provinceList.add(FieldValue.of("北京"));
//        provinceList.add(FieldValue.of("武汉"));
    // 搜索
    SearchResponse<SmsLogsPO> response = client.search(request -> request
                    .index(ElasticSearchIndexTest.INDEX_NAME)
                    .query(q -> q
//                                        .term(t -> t.field("province").value("北京"))    // term 查询
//                                        .terms(t -> t.field("province").terms(ts -> ts.value(provinceList)))  // terms 查询
                                    .matchAll(m -> m) // match_all 查询
//                                        .match(m -> m.field("smsContent").query("尊敬尊贵")) // match 查询
//                                        .match(m -> m.field("smsContent").query("尊贵 先生").operator(Operator.And)) // 布尔 match 查询
//                                        .multiMatch(m -> m.query("北京").fields("province","smsContent")) // multi_match 查询
//                                        .ids(i ->i.values("1u9_VpEBCrSApDIskHH3", "2O9_VpEBCrSApDIskHH3"))
//                                        .prefix(p -> p.field("corpName").value("途虎养车")) // prefix 查询
//                                        .fuzzy(f -> f.field("corpName").value("途虎养车").prefixLength(2)) // fuzzy 查询
//                                        .wildcard(f -> f.field("corpName").value("途虎*")) // wildcard 查询
//                                        .range(r -> r.field("fee").gte(JsonData.of(5)).lte(JsonData.of(10))) // range 查询
//                                        .regexp((r -> r.field("mobile").value("180[0-9]{8}"))) // regexp 查询
//                                        .bool(b -> b // bool 查询
//                                                .should(TermQuery.of(t -> t.field("province").value("北京"))._toQuery(), TermQuery.of(t -> t.field("province").value("武汉"))._toQuery())
//                                                .mustNot(TermQuery.of(t -> t.field("operatorId").value("2"))._toQuery())
//                                                .must(MatchQuery.of(m -> m.field("smsContent").query("中国"))._toQuery(), MatchQuery.of(m -> m.field("smsContent").query("平安"))._toQuery())) // bool 查询
//                                        .boosting(b -> b //boosting 查询
//                                                .positive(MatchQuery.of(m -> m.field("smsContent").query("收获安装"))._toQuery())
//                                                .negative(MatchQuery.of(m -> m.field("smsContent").query("王五"))._toQuery())
//                                                .negativeBoost(0.5))
//                                        .bool(b -> b // filter 查询
//                                                .filter(
//                                                        TermQuery.of(t -> t.field("province").value("武汉"))._toQuery(),
//                                                        RangeQuery.of(r -> r.field("fee").lte(JsonData.of(5)))._toQuery()
//                                                ))
                    )
//                        .highlight(h -> h // 高亮查询
//                                .fields("smsContent", v -> v
//                                        .fragmentSize(10)
//                                        .preTags("<font color='red'>")
//                                        .postTags("</font>")))
//                        .aggregations("agg_result", a -> a    // 聚合查询（去重计数）
//                                .cardinality(c -> c
//                                        .field("province")))
                    .aggregations("range_result", r -> r
                            .range(rg -> rg
                                    .field("fee")
                                    .ranges(
                                            AggregationRange.of(ar -> ar.to("5")),
                                            AggregationRange.of(ar -> ar.from("5").to("10")),
                                            AggregationRange.of(ar -> ar.from("10")))))

//                        .sort(s -> s.field(f -> f.field("price").order(SortOrder.Asc))) //排序字段1
//                        .sort(s -> s.field(f -> f.field("create_date").order(SortOrder.Desc))) //排序字段2
                    .from(0)   // 浅分页，类似 mysql 的 limit 参见：https://www.elastic.co/guide/en/elasticsearch/reference/current/paginate-search-results.html
                    .size(20), // 默认查询 10 条,如果需要查询更多则需要指定 size
            SmsLogsPO.class);
    System.out.println(response.hits().total().value()); //数量
    response.hits().hits().forEach(h -> {
        SmsLogsPO log = h.source(); // 实体类
        System.out.println(log);
        System.out.println(h.highlight());
    });
//    Aggregate aggregate = response.aggregations().get("agg_result");
//    System.out.println(aggregate.cardinality().value());
    Aggregate aggregate = response.aggregations().get("range_result");
    for(RangeBucket bucket:aggregate.range().buckets().array()){
        System.out.println(bucket);
    }
}

    @Test
    @Order(4)
    public void scrollDocument() throws IOException {
        long num = 0L;
        // 执行 scro11 查询
        SearchResponse<SmsLogsPO> response = client.search(request -> request
                        .index(ElasticSearchIndexTest.INDEX_NAME)
                        .scroll(Time.of(t -> t.time("1m")))
                        .query(q ->
                                q.matchAll(m -> m) // match_all 查询
                        )
                        .size(5),
                SmsLogsPO.class);
        System.out.println(response.hits().total().value()); // 数量
        System.out.println(response.scrollId()); // scrollId
        response.hits().hits().forEach(h -> {
            SmsLogsPO log = h.source(); // 实体类
            System.out.println(log);
        });
        num = response.hits().hits().size();

        // 根据 scro1l 查询下一页数据
        String scrollId = response.scrollId();
        while (num > 0) {
            ScrollResponse<SmsLogsPO> scrollResponse = client.scroll(s -> s
                            .scrollId(scrollId)
                            .scroll(Time.of(t -> t.time("1m"))),
                SmsLogsPO.class
            );
            System.out.println(scrollResponse.hits().total().value()); // 数量
            System.out.println(scrollResponse.scrollId()); // scrollId
            scrollResponse.hits().hits().forEach(h -> {
                SmsLogsPO log = h.source(); // 实体类
                System.out.println(log);
            });
            num = scrollResponse.hits().hits().size();
        }

        // 删除 scro11 在 ES 上下文中的数据
        ClearScrollResponse clearScrollResponse = client.clearScroll(s -> s.scrollId(scrollId));
        System.out.println(clearScrollResponse);
    }

    @Test
    @Order(5)
    public void updateDocument() throws IOException {
//        goodsVO.setCreateDate(LocalDateTime.parse("2022-12-14 00:00:00", dtf));
//        UpdateResponse<GoodsVO> response = client.update(request -> request
//                        .index(ElasticSearchConstants.DEMO_INDEX_ES_DEMO_GOODS)
//                        .id("1")
//                        .doc(goodsVO),
//                GoodsVO.class);
//        System.out.println(response);
    }

    @Test
    @Order(6)
    public void deleteDocument() throws IOException {
//        DeleteResponse response = client.delete(request -> request
//                .index(ElasticSearchConstants.DEMO_INDEX_ES_DEMO_GOODS)
//                .id("1"));
//        System.out.println(response);
    }

    @Test
    @Order(6)
    public void deleteByQueryDocument() throws IOException {
        DeleteByQueryResponse response = client.deleteByQuery(request -> request
                        .index(ElasticSearchIndexTest.INDEX_NAME)
                        .query(q ->
                                        q.range(r -> r.field("fee").lt(JsonData.of(4))) // range 查询
                        ));
        System.out.println(response);
    }


}
