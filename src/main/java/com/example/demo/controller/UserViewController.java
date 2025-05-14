package com.example.demo.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;

@Controller
public class UserViewController {
  private final UserService userService;

  public UserViewController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/users")
  public String listUsers(Model model) {
    model.addAttribute("users", userService.findAllUsers());
    return "users/list";
  }

  @GetMapping("/users/create")
  public String showCreateForm(Model model) {
    model.addAttribute("user", new User());
    return "users/create";
  }

  @GetMapping("/users/edit/{id}")
  public String showEditForm(@PathVariable Long id, Model model) {
    Optional<User> user = userService.findUserById(id);
    if (user.isPresent()) {
      model.addAttribute("user", user.get());
      return "users/edit";
    } else {
      return "redirect:/users";
    }
  }

  @PostMapping("/users/create")
  public String createUser(@ModelAttribute User user) {
    userService.createUser(user);
    return "redirect:/users?success=true&action=create";
  }

  @PostMapping("/users/edit/{id}")
  public String updateUser(@PathVariable Long id, @ModelAttribute User user) {
    userService.updateUser(id, user);
    return "redirect:/users?success=true&action=update";
  }

  @GetMapping("/users/delete/{id}")
  public String deleteUser(@PathVariable Long id) {
    userService.deleteUser(id);
    return "redirect:/users?success=true&action=delete";
  }
}
