package br.com.gustavobarbozamarques.springbootbatch.batch;

import br.com.gustavobarbozamarques.springbootbatch.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class SummaryTasklet implements Tasklet {

    private final UserRepository userRepository;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        log.info("JOB FINISHED! TOTAL IMPORTED: {}", userRepository.count());
        return RepeatStatus.FINISHED;
    }
}
