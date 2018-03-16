package com.f_lin.gateway.filter;

import com.f_lin.gateway.po.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author F_lin fengjunlin@23mofang.com
 * @since 2018/3/16
 **/
@RestController
@RequestMapping("/api/test")
public class TestController implements TestApi {
    @Autowired
    MongoOperations mongoOperations;

    @GetMapping
    public Test testFeign() {
        return mongoOperations.findOne(Query.query(Criteria.where("").is("")), Test.class);
    }

}
