package com.jesus.common.base.expant.dao;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;

@NoRepositoryBean
public class IBaseDao<T, ID extends Serializable> extends SimpleJpaRepository<T,ID> implements BaseDao<T,ID>  {

    private EntityManager entityManager;


    public IBaseDao(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
    }

    //父类没有不带参数的构造方法，这里手动构造父类
    public IBaseDao(Class<T> domainClass, EntityManager entityManager) {
        this(JpaEntityInformationSupport.getEntityInformation(domainClass, entityManager), entityManager);
        this.entityManager = entityManager;
    }

    //通过EntityManager来完成自定义的查询
    @Override
    public List<Object[]> selectBySQL(String sql) {
        return entityManager.createNativeQuery(sql).getResultList();
    }

}
