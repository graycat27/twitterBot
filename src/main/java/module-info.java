module twitterBot {
    requires java.sql;
    requires java.naming;
    requires jakarta.servlet;
    requires com.google.gson;
    requires org.apache.httpcomponents.client5.httpclient5;
    requires org.apache.httpcomponents.core5.httpcore5;
    requires org.mybatis;
    requires org.mybatis.spring;
    requires org.mybatis.spring.boot.autoconfigure;
    requires org.postgresql.jdbc;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.context;
    requires spring.core;
    requires spring.jdbc;
    requires spring.web;

    exports com.github.graycat27.twitterbot.utils;

    // Gson needs to right for reflection access
    opens com.github.graycat27.twitterbot.twitter.api.response to com.google.gson;
    opens com.github.graycat27.twitterbot.twitter.api.response.data to com.google.gson;
}