package br.com.gustavobarbozamarques.springbootbatch.batch;

import br.com.gustavobarbozamarques.springbootbatch.dtos.UserInputDTO;
import br.com.gustavobarbozamarques.springbootbatch.entities.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@EnableBatchProcessing
@Slf4j
public class JobConfiguration {

    @Bean
    public Job importJob(JobBuilderFactory jobBuilderFactory, Step step1, Step step2) {
        return jobBuilderFactory.get("importJob")
                .incrementer(new RunIdIncrementer())
                .start(step1)
                .next(step2)
                .build();
    }

    @Bean
    public Step step1(
            StepBuilderFactory stepBuilderFactory,
            FlatFileItemReader<UserInputDTO> userItemReader,
            ItemProcessor<UserInputDTO, User> userItemProcessor,
            ItemWriter<User> userItemWriter) {

        return stepBuilderFactory.get("step1")
                .<UserInputDTO, User>chunk(10)
                .reader(userItemReader)
                .processor(userItemProcessor)
                .writer(userItemWriter)
                .faultTolerant()
                .skip(Exception.class)
                .skipPolicy((ex, skipCount) -> {
                    log.info(ex.getMessage());
                    return true;
                })
                .build();
    }

    @Bean
    public Step step2(
            StepBuilderFactory stepBuilderFactory,
            SummaryTasklet summaryTasklet) {

        return stepBuilderFactory.get("step2")
                .tasklet(summaryTasklet)
                .build();
    }

    @Bean
    public FlatFileItemReader<UserInputDTO> userItemReader(@Value("${csv.file}") String csvFile) {
        log.info("Reading CSV file");

        var fieldSetMapper = new BeanWrapperFieldSetMapper<UserInputDTO>();
        fieldSetMapper.setTargetType(UserInputDTO.class);

        var lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setNames("name", "email");
        lineTokenizer.setDelimiter(";");

        var lineMapper = new DefaultLineMapper<UserInputDTO>();
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        var reader = new FlatFileItemReader<UserInputDTO>();
        reader.setResource(new ClassPathResource(csvFile));
        reader.setLineMapper(lineMapper);
        return reader;
    }

}
