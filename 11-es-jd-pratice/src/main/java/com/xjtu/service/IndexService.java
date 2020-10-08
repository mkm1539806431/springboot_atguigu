package com.xjtu.service;

import com.alibaba.fastjson.JSON;
import com.xjtu.bean.Content;
import com.xjtu.utils.HtmlParse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class IndexService {

    @Autowired
    RestHighLevelClient highLevelClient;
    //1.解析数据放入es索引库中
    public boolean parseHtml(String keyWords) throws IOException {
        HtmlParse htmlParse = new HtmlParse();
        List<Content> contents = htmlParse.parseHtmlJd(keyWords);

        BulkRequest bulkRequest = new BulkRequest("index");
        bulkRequest.timeout("1s");

        for (Content content : contents) {
            bulkRequest.add(
                    new IndexRequest("jd_goods")
                    .source(JSON.toJSONString(content), XContentType.JSON)
            );
            System.out.println(JSON.toJSONString(content));
        }

        BulkResponse response = highLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);


        return !response.hasFailures();
    }

//    2.获取这些数据，实现搜索功能

    public List<Map<String,Object>> searchPage(String keyword, int pageNo, int pageSize) throws IOException {

        if (pageNo<1){
            pageNo=1;
        }
        SearchRequest searchRequest = new SearchRequest("jd_goods");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

//        分页
        sourceBuilder.from(pageNo);
        sourceBuilder.size(pageSize);

//        条件搜索--精确匹配
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("title",keyword);
        sourceBuilder.query(termQueryBuilder);
        sourceBuilder.timeout(new TimeValue(20, TimeUnit.SECONDS));

        searchRequest.source(sourceBuilder);
        SearchResponse response= highLevelClient.search(searchRequest,RequestOptions.DEFAULT);
        List<Map<String,Object>> list=new ArrayList<>();
        for (SearchHit hit : response.getHits().getHits()) {
            list.add(hit.getSourceAsMap());
        }
        return list;


    }


}
