package com.company.service;

import com.company.model.User;
import com.company.model.UserType;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class UserService {


    private HashMap<String, User> usersHashMap;
    private User currentUser;
    private Scanner scanner;
    private File usersDataBase;

    {
        scanner = new Scanner(System.in);
        usersDataBase = new File("UsersDataBase.txt");
    }

    public void createUser() {
        String login, password;
        login = checkLogin();
        password = checkPassword();
        currentUser = new User(login, password, UserType.COMMON_USER);
        usersHashMap.put(currentUser.getLogin(), currentUser);
        System.out.println("Пользователь создан успешно.");
    }

    public void updateUser() {
        System.out.println("Введите login:");
        String login = scanner.nextLine();
        User user = usersHashMap.get(login);
        System.out.println("Введите новый пароль:");
        user.setPassword(scanner.nextLine());
        usersHashMap.put(login, user);
        System.out.println("Пароль обновлен");
    }

    public void deleteUser() {
        System.out.println("Введите login:");
        String login = scanner.nextLine();
        User user = usersHashMap.get(login);
        usersHashMap.remove(login);
        System.out.println("Пользователь удален");
    }

    private String checkLogin() {
        System.out.println("Введите login:");
        String login = scanner.nextLine();
        if (login.isEmpty()) {
            System.out.println("Что-то не так, повторите снова");
            checkLogin();
        } else if (getUsersHashMap().containsKey(login)) {
            System.out.println(
                    "Пользователь " + login + " уже существует, придумайте другой login и повторите снова");
            checkLogin();
        } else {
            return login;
        }
        return null;
    }

    private String checkPassword() {
        System.out.println("Введите пароль:");
        String password = scanner.nextLine();
        if (password.isEmpty()) {
            System.out.println("Что-то не так, повторите снова");
            checkPassword();
        } else {
            return password;
        }
        return null;
    }

    public void login() {
        System.out.println("Введите login:");
        String login = scanner.nextLine();
        if (getUsersHashMap().containsKey(login)) {
            currentUser = usersHashMap.get(login);
            System.out.println("Введите пароль:");
            String password = scanner.nextLine();
            if (password.equals(currentUser.getPassword())) {
                System.out.println("Вход выполнен успешно");
            } else {
                System.out.println("Что-то не так, повторите снова");
                login();
            }
        } else {
            System.out.println("Что-то не так, повторите снова");
            login();
        }
        System.out.println();
    }

    public void createFileUsers() {
        if (!usersDataBase.exists()) {
            try {
                usersDataBase.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public HashMap<String, User> getUsersHashMap() {
        if (usersHashMap == null) {
            usersHashMap = new HashMap<>();
            User tempUser;
            String var;
            try (BufferedReader br = new BufferedReader(new FileReader(usersDataBase))) {
                while ((var = br.readLine()) != null) {
                    String[] array = var.trim().split("; ");
                    tempUser = new User(
                            Integer.parseInt(getFieldValue(array[0])),
                            getFieldValue(array[1]),
                            getFieldValue(array[2]),
                            UserType.valueOf(getFieldValue(array[3]))
                    );
                    if (tempUser.getId() > User.getCounter()) {
                        User.setCounter(tempUser.getId());
                    }
                    usersHashMap.put(tempUser.getLogin(), tempUser);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return usersHashMap;
    }

    public String getFieldValue(String str) {
        int index = str.indexOf("=");
        return str.substring(index + 1);
    }

    public void rewriteUsers() {
        HashMap<String, User> var = getUsersHashMap();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(usersDataBase))) {
            for (User user : var.values()) {
                bw.write(String
                        .format("id=%s; Login=%s; Password=%s; UserType=%s%n", user.getId(), user.getLogin(),
                                user.getPassword(), user.getUserType()));
            }
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printHashMapUsers() {
        for (User user : getUsersHashMap().values()) {
            if (user.getUserType().equals(UserType.COMMON_USER)) {
                System.out.printf("id=%s, login=%s%n", user.getId(), user.getLogin());
            }
        }
    }

    public User getUser() {
        return currentUser;
    }
}




