package br.com.gustavobarbozamarques.springbootbatch.batch;

import br.com.gustavobarbozamarques.springbootbatch.entities.User;
import br.com.gustavobarbozamarques.springbootbatch.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
public class UserItemWriter implements ItemWriter<User> {

    private final UserRepository userRepository;

    @Override
    public void write(List<? extends User> items) throws Exception {
        log.info("Saving to database chunk count {}", items.size());
        userRepository.saveAll(items);
    }
}
