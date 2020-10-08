package com.xjtu.controller;

import com.xjtu.bean.Content;
import com.xjtu.service.IndexService;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
public class ContentController {
    @Autowired
    IndexService indexService;
    @GetMapping("/save/{keyword}")
    @ResponseBody
    public boolean save(@PathVariable("keyword") String keyword) throws IOException {
        return indexService.parseHtml(keyword);
    }

    @GetMapping("/search/{keyword}/{pageNo}/{pageSize}")
    @ResponseBody
    public List<Map<String, Object>> search(@PathVariable("keyword") String keyword,
                                             @PathVariable("pageNo") int pageNo,
                                             @PathVariable("pageSize")int pageSize) throws IOException {
        List<Map<String, Object>> contents = indexService.searchPage(keyword, pageNo, pageSize);
        return contents;
    }

}
