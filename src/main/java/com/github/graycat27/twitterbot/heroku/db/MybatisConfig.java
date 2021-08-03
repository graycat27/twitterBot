package com.github.graycat27.twitterbot.heroku.db;

import org.apache.ibatis.io.VFS;
import org.apache.ibatis.session.AutoMappingBehavior;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.boot.autoconfigure.integration.IntegrationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.JDBCType;

@Configuration
public class MybatisConfig {

  /*  public static DataSourceTransactionManager transactionManager(DataSource dataSource){
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource);

        return transactionManager;
    }
 */
    public static SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();

        DataSourceBuilder<PGSimpleDataSource> builder = DataSourceBuilder.create().type(PGSimpleDataSource.class);
        builder.driverClassName("org.postgresql.Driver");
        builder.url(System.getenv("JDBC_DATABASE_URL"));
        builder.username(System.getenv("JDBC_DATABASE_USERNAME"));
        builder.password(System.getenv("JDBC_DATABASE_PASSWORD"));

        sessionFactory.setDataSource(builder.build());
        sessionFactory.setVfs(SpringBootVFS.class);

        org.apache.ibatis.session.Configuration config = new org.apache.ibatis.session.Configuration();
        config.setMapUnderscoreToCamelCase(true);
        config.setAutoMappingBehavior(AutoMappingBehavior.FULL);
        VFS.addImplClass(SpringBootVFS.class);
        config.getTypeAliasRegistry().registerAliases("com.github.graycat27.twitterbot.heroku.db.domain");
        sessionFactory.setConfiguration(config);

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sessionFactory.setMapperLocations(resolver.getResources("classpath:/mybatis/*Mapper.xml"));

        return sessionFactory.getObject();
    }
}
