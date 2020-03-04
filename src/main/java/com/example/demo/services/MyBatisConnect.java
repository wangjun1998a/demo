package com.example.demo.services;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author alin
 */
public class MyBatisConnect {
//    @Test
//    public void test() throws IOException {
//        String resource = "mybatis_config.xml";
//        InputStream inputStream = Resources.getResourceAsStream(resource);
//        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
//        try (SqlSession session = sqlSessionFactory.openSession()) {
//            EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
//            Employee employee = mapper.getEmployeeById(1);
//            System.out.println(employee);
//        }
//    }
}
