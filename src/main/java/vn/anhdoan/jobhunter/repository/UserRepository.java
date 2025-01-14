package vn.anhdoan.jobhunter.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.anhdoan.jobhunter.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}