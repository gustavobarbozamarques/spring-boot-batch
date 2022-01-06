package br.com.gustavobarbozamarques.springbootbatch.batch;

import br.com.gustavobarbozamarques.springbootbatch.dtos.UserInputDTO;
import br.com.gustavobarbozamarques.springbootbatch.entities.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import javax.validation.Validator;

@Component
@AllArgsConstructor
@Slf4j
public class UserItemProcessor implements ItemProcessor<UserInputDTO, User> {

    private final Validator validator;

    @Override
    public User process(UserInputDTO item) throws Exception {

        if (!validator.validate(item).isEmpty()) {
            log.info("Skip invalid user {}", item);
            return null;
        }

        return User.builder()
                .name(item.getName())
                .email(item.getEmail())
                .build();
    }
}
