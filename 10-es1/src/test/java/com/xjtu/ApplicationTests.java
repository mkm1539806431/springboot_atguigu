package com.xjtu;

import com.alibaba.fastjson.JSON;
import com.xjtu.bean.Book;
import com.xjtu.repository.BookRepository;
import org.apache.tomcat.util.net.openssl.OpenSSLUtil;
import org.elasticsearch.action.admin.cluster.repositories.delete.DeleteRepositoryAction;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.junit.jupiter.api.Test;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.GetQuery;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class ApplicationTests {

    @Autowired
    ElasticsearchOperations elasticsearchOperations;
    @Autowired
    RestHighLevelClient highLevelClient;
    @Autowired
    private BookRepository bookRepository;
    @Test
    void contextLoads() {
        IndexRequest request = new IndexRequest("ustc", "book", "1")
                .source(Collections.singletonMap("feature", "high-level-rest-client"))
                .setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
        try {
            IndexResponse index = highLevelClient.index(request, RequestOptions.DEFAULT);
            System.out.println(index.getIndex());
            System.out.println(index.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * RestHighLevelClient的get index
     * @throws IOException
     */
    @Test
    void getIndex() throws IOException {
        GetRequest getRequest = new GetRequest("ustc","book","1");
        GetResponse documentFields = highLevelClient.get(getRequest, RequestOptions.DEFAULT);
        System.out.println(documentFields);
    }

    /**
     * ElasticsearchOperations保存索引
     */

    @Test
    void saveIndex() {
        Book book = new Book();
        book.setAuthor("吴承恩");
        book.setBookName("西游记");
        book.setId(2);
        IndexCoordinates indexCoordinates = IndexCoordinates.of("store");
        IndexQuery indexQuery = new IndexQueryBuilder()
                .withId(book.getId().toString())
                .withObject(book)
                .build();
        String index = elasticsearchOperations.index(indexQuery,indexCoordinates);
        System.out.println(index);
    }

    /**
     * ElasticsearchOperations查询索引
     */
    @Test
    void findId() {
        Book book = elasticsearchOperations.queryForObject(GetQuery.getById("1"), Book.class);
        System.out.println(book);
    }


    @Test
    void TestBookRepository() {
        List<Book> books = bookRepository.findByBookNameAndAuthor("的世界", "遥");
        books.forEach(System.out::println);
    }


    @Test
    void testCreateIndex() throws IOException {
        CreateIndexRequest request = new CreateIndexRequest("index");
        CreateIndexResponse createIndexResponse = highLevelClient.indices().create(request, RequestOptions.DEFAULT);
        System.out.println(createIndexResponse);
    }

    @Test
    void testExistIndex() throws IOException {
        GetIndexRequest request=new GetIndexRequest("index");
        boolean exists = highLevelClient.indices().exists(request, RequestOptions.DEFAULT);
        System.out.println(exists);
    }

    @Test
    void testDeleteIndex() throws IOException {
        DeleteIndexRequest request= new DeleteIndexRequest("index");
        AcknowledgedResponse exists = highLevelClient.indices().delete(request, RequestOptions.DEFAULT);
        System.out.println(exists);
    }


    @Test
    void testAddDocument() throws IOException {
        Book book = new Book();
        book.setAuthor("吴承恩");
        book.setBookName("西游记11");
        book.setId(2);

        IndexRequest request=new IndexRequest("index");
        request.id("1");
        request.timeout("1s");

//        将数据放入json
        request.source(JSON.toJSONString(book), XContentType.JSON);

//        客户端发送请求
        IndexResponse response = highLevelClient.index(request, RequestOptions.DEFAULT);
        System.out.println(response.status());
        System.out.println(response.toString());

    }

    @Test
    void testIsExists() throws IOException{
        GetRequest request = new GetRequest("index","1");
//        不获取返回的_source上下文
        request.fetchSourceContext(new FetchSourceContext(false));
        request.storedFields("_none_");

        boolean exists = highLevelClient.exists(request, RequestOptions.DEFAULT);

        System.out.println(exists);
    }

    @Test
    void testGetDocument() throws IOException{
        GetRequest request = new GetRequest("index","1");
//        不获取返回的_source上下文

        GetResponse response=highLevelClient.get(request,RequestOptions.DEFAULT);

        System.out.println(response.getSourceAsString());
        System.out.println(response);
    }

    @Test
    void testUpdateDocument() throws IOException{
        UpdateRequest request = new UpdateRequest("index","1");
//        不获取返回的_source上下文
        request.timeout("1s");

        Book book = new Book();
        book.setAuthor("吴承恩123");
        book.setBookName("西游记112");
        book.setId(2);

        request.doc(JSON.toJSONString(book),XContentType.JSON);

        UpdateResponse response=highLevelClient.update(request,RequestOptions.DEFAULT);
        System.out.println(response.status());
    }


    @Test
    void testDeleteRequest() throws IOException {
        DeleteRequest request=new DeleteRequest("index","1");
        request.timeout("1s");
        DeleteResponse response =highLevelClient.delete(request,RequestOptions.DEFAULT);
        System.out.println(response.status());
    }


    @Test
    void testBulkRequest() throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout("1s");

        ArrayList<Book> list=new ArrayList<>();
        list.add(new Book(12,"三国","luo"));
        list.add(new Book(13,"三国2","luo"));
        list.add(new Book(14,"三国3","luo"));
        list.add(new Book(15,"三国4","luo"));
        list.add(new Book(16,"三国5","luo"));

        for (int i=0;i<list.size();++i){
            bulkRequest.add(
                    new IndexRequest("index")
                    //不用id可以生成随机id
                    .id(""+(i+1))
                    .source(JSON.toJSONString(list.get(i)),XContentType.JSON));
        }
        BulkResponse responses = highLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println(responses.hasFailures());

    }



    //查询
    //SearchRequest搜索请求
    //SearchSourceBUilder条件构造
    //HighlightBUilder高亮
    //TermQUeryBUilder精确查询
    //MatchAllQueryBUilder
    @Test
    void testSearch() throws IOException {
        SearchRequest searchRequest = new SearchRequest("index");

        //        构建搜索条件
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("bookName", "三国");
        sourceBuilder.query(termQueryBuilder);
        sourceBuilder.timeout(new TimeValue(60,TimeUnit.SECONDS));

        searchRequest.source(sourceBuilder);



        SearchResponse response = highLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println(response.getHits());
        System.out.println("================================");
        for(SearchHit hits:response.getHits().getHits()){
            System.out.println(hits.getSourceAsMap());
        }


    }

}
