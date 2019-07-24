package com.jesus.common.base.exart.service;

import com.jesus.common.base.exart.dao.IBaseDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import javax.persistence.EntityManager;
import java.io.Serializable;

public class BaseDaoFactoryBean<R extends JpaRepository<T, ID>, T,
        ID extends Serializable> extends JpaRepositoryFactoryBean<R, T, ID> {

    public BaseDaoFactoryBean(Class<? extends R> repositoryInterface) {
        super(repositoryInterface);
    }

    @Override
    protected RepositoryFactorySupport createRepositoryFactory(EntityManager em) {
        return new BaseDaoFactory(em);
    }

    //创建一个内部类，该类不用在外部访问
    private static class BaseDaoFactory<T, I extends Serializable>
            extends JpaRepositoryFactory {

        private final EntityManager em;

        public BaseDaoFactory(EntityManager em) {
            super(em);
            this.em = em;
        }

        //此方法以经在父类方法中实现了,所以不用再自定义了
//        @Override
//        protected JpaRepositoryImplementation getTargetRepository(RepositoryInformation information) {
//            return super(information, em);
//        }

        //设置具体的实现类的class
        @Override
        protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
            return IBaseDao.class;
        }
    }

}