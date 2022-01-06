package br.com.gustavobarbozamarques.springbootbatch.repositories;

import br.com.gustavobarbozamarques.springbootbatch.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}
