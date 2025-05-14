package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  // メールアドレスでユーザを検索
  Optional<User> findByEmail(String email);

  // 名前に特定の文字列を含むユーザを検索
  List<User> findByNameContaining(String name);

  // メールアドレスが存在するかチェック
  boolean existsByEmail(String email);
}
