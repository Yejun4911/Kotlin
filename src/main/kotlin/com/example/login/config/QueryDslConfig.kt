package com.dooribun.auth.config

import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class QueryDslConfig {
    @PersistenceContext
    private lateinit var entityManager: EntityManager
//     QueryDSL 관련 설정을 여기에 추가합니다.
//     예를 들어, JPAQueryFactory 빈을 등록할 수 있습니다.
     @Bean
     fun jpaQueryFactory(): JPAQueryFactory {
         return JPAQueryFactory(entityManager)
     }

}