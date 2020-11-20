package Chungmu_ro.schoolManagement;

import Chungmu_ro.schoolManagement.repository.JpaStudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

@Configuration
public class SpringConfig {

    //    private DataSource dataSource;
    private EntityManager em;

    @Autowired
    public SpringConfig(EntityManager em) {
        this.em = em;
    }

    @Bean
    public MemberRepository MemberRepository(){

        return new JpaMemberRepository(em);
    }
    @Bean
    public StudentRepository StudentRepository{

        return new JpaStudentRepository(em);
    }
}

