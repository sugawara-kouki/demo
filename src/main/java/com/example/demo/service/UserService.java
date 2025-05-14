package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
  
  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  // ユーザ全件取得
  public List<User> findAllUsers() {
    return userRepository.findAll();
  }

  // IDによるユーザー取得
  public Optional<User> findUserById(Long id) {
    return userRepository.findById(id);
  }

  // メールアドレスによるユーザー取得
  public Optional<User> findUserByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  // ユーザー作成
  @Transactional
  public User createUser(User user) {
    // メールアドレスの重複チェック
    if (userRepository.existsByEmail(user.getEmail())) {
        throw new RuntimeException("メールアドレスが既に使用されています: " + user.getEmail());
    }
    return userRepository.save(user);
  }

  // ユーザー更新
  @Transactional
  public User updateUser(Long id, User userDetails) {
    User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("ユーザーが見つかりません ID: " + id));

    // メールアドレスの変更があり、かつ新しいメールアドレスが既に使用されている場合
    if (!user.getEmail().equals(userDetails.getEmail()) && 
        userRepository.existsByEmail(userDetails.getEmail())) {
        throw new RuntimeException("メールアドレスが既に使用されています: " + userDetails.getEmail());
    }

    user.setName(userDetails.getName());
    user.setEmail(userDetails.getEmail());

    // パスワードが提供されている場合のみ更新
    if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
        user.setPassword(userDetails.getPassword());
    }

    return userRepository.save(user);
  }

  // ユーザー削除
  @Transactional
  public void deleteUser(Long id) {
    User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("ユーザーが見つかりません ID: " + id));
    userRepository.delete(user);
    }

    // 名前で検索
    public List<User> findUsersByName(String name) {
    return userRepository.findByNameContaining(name);
  }
}
