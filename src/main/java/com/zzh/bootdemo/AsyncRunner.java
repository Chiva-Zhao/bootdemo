package com.zzh.bootdemo;

import com.zzh.bootdemo.model.User;
import com.zzh.bootdemo.service.GitHubLookupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

//@Component
public class AsyncRunner implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(AsyncRunner.class);
    private final GitHubLookupService gitHubLookupService;

    public AsyncRunner(GitHubLookupService gitHubLookupService) {
        this.gitHubLookupService = gitHubLookupService;
    }

    @Override
    public void run(String... strings) throws Exception {
        long start = System.currentTimeMillis();
        CompletableFuture<User> u1 = gitHubLookupService.findUser("PivotalSoftware");
        CompletableFuture<User> u2 = gitHubLookupService.findUser("CloudFoundry");
        CompletableFuture<User> u3 = gitHubLookupService.findUser("Spring-Projects");

        // Wait until they are all done
        CompletableFuture.allOf(u1, u2, u3).join();
        logger.info("Elapsed time: " + (System.currentTimeMillis() - start));
        logger.info("-->" + u1.get());
        logger.info("-->" + u2.get());
        logger.info("-->" + u3.get());
    }
}
