package com.soroweb.board.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@MapperScan( basePackages = { "com/soroweb/board/mapper" } )
@EnableTransactionManagement
public class DatabaseConfig {

	@Bean
	public SqlSessionFactory sqlSessionFactory( DataSource dataSource ) throws Exception {
		final SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		
		sqlSessionFactoryBean.setDataSource( dataSource );
		sqlSessionFactoryBean.setMapperLocations( new PathMatchingResourcePatternResolver().getResources( "classpath:mapper/*Mapper.xml" ) );
		sqlSessionFactoryBean.setConfigLocation( new PathMatchingResourcePatternResolver().getResource( "classpath:config/mybatis-config.xml" ) );
		
		return sqlSessionFactoryBean.getObject();
	}
	
	@Bean
	public SqlSessionTemplate sqlSessionTemplate( SqlSessionFactory sqlSessionFactory ) throws Exception {
		SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate( sqlSessionFactory );
		
		return sqlSessionTemplate;
	}
}
