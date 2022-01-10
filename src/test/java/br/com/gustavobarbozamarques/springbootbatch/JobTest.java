package br.com.gustavobarbozamarques.springbootbatch;

import br.com.gustavobarbozamarques.springbootbatch.repositories.UserRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@SpringBatchTest
class JobTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;

    @Autowired
    private UserRepository userRepository;

    @After
    public void cleanUp() {
        jobRepositoryTestUtils.removeJobExecutions();
        userRepository.deleteAll();
    }

    @Test
    void testJob_shouldReturnCompleted() throws Exception {
        var jobExecution = jobLauncherTestUtils.launchJob();
        var jobExitStatus = jobExecution.getExitStatus();
        Assert.assertEquals("COMPLETED", jobExitStatus.getExitCode());
    }

}
