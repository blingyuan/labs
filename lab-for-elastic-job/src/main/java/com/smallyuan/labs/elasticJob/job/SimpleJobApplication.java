package com.smallyuan.labs.elasticJob.job;

import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;

public class SimpleJobApplication {
    public static void main(String[] args) {
        CoordinatorRegistryCenter regCenter = setUpRegistryCenter();

        setUpSimpleJob(regCenter);
    }

    private static CoordinatorRegistryCenter setUpRegistryCenter() {
        CoordinatorRegistryCenter registryCenter = new ZookeeperRegistryCenter(new ZookeeperConfiguration("localhost:2181","SimpleJobDemo"));
        registryCenter.init();
        return registryCenter;
    }

    private static void setUpSimpleJob(CoordinatorRegistryCenter regCenter) {
        JobCoreConfiguration jobCoreConfiguration = JobCoreConfiguration.newBuilder("SimpleJobDemo","0/5 * * * * ?",3).build();
        SimpleJobConfiguration simpleJobConfiguration = new SimpleJobConfiguration(jobCoreConfiguration,SimpleJobDemo.class.getCanonicalName());
        new JobScheduler(regCenter, LiteJobConfiguration.newBuilder(simpleJobConfiguration).build()).init();
    }
}
