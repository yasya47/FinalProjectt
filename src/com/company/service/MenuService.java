package com.company.service;

import java.util.Scanner;
import com.company.model.Menu;
import com.company.model.UserType;

public class MenuService {

    private MovieService movieService;
    private UserService userService;
    private TicketService ticketService;
    private Scanner scanner;
    private Menu menu;

    {
        movieService = new MovieService();
        userService = new UserService();
        ticketService = new TicketService();
        scanner = new Scanner(System.in);
        menu = new Menu();
    }

    public void startWelcomeMenu() {
        userService.createFileUsers();
        movieService.createFileMovies();
        menu.printTheWelcomeMenuView();
        startMenu();
    }

    public void startMenu() {
        if (scanner.hasNextInt()) {
            int var = scanner.nextInt();
            switch (var) {
                case 1:
                    userService.createUser();
                    break;
                case 2:
                    userService.login();
                    break;
                case 3:
                    exit();
                    break;
                default:
                    System.out.println("Что-то не так, попробуйте снова\n");
                    startWelcomeMenu();
            }
            subMenu();
        } else {
            System.out.println("Что-то не так, попробуйте снова\n");
            scanner.next();
            startWelcomeMenu();
        }
    }

    public void subMenu() {
        UserType type = userService.getUser().getUserType();
        switch (type) {
            case COMMON_USER:
                startMenuCommon();
                break;
            case MANAGER:
                startMenuManager();
                break;
            case ADMINISTRATOR:
                startMenuAdministrator();
                break;
        }
    }

    public void startMenuCommon() {
        menu.printTheCommonMenuView();
        if (scanner.hasNextInt()) {
            int var = scanner.nextInt();
            switch (var) {
                case 1:
                    movieService.showMovieList();
                    break;
                case 2:
                    ticketService.purchaseTicket(userService, movieService);
                    break;
                case 3:
                    ticketService.showPurchaseTickets(userService, movieService);
                    break;
                case 4:
                    ticketService.returnTicket(userService, movieService);
                    break;
                case 5:
                    exit();
                    break;
                default:
                    System.out.println("Что-то не так, попробуйте снова\n");
            }
            startMenuCommon();
        }
    }

    public void startMenuManager() {
        menu.printManagerEditorMenu();
        if (scanner.hasNextInt()) {
            int var = scanner.nextInt();
            switch (var) {
                case 1:
                    movieService.showMovieList();
                    break;
                case 2:
                    movieService.changeTheDateShowMovie();
                    break;
                case 3:
                    userService.printHashMapUsers();
                    break;
                case 4:
                    ticketService.purchaseTicketForManager(userService, movieService);
                    break;

                case 5:
                    exit();
                    break;
                default:
                    System.out.println("Что-то не так, попробуйте снова\n");
            }
        }
        startMenuManager();
    }

    public void startMenuAdministrator() {
        menu.printTheAdministratorMenuView();
        if (scanner.hasNextInt()) {
            int var = scanner.nextInt();
            switch (var) {
                case 1:
                    userService.printHashMapUsers();
                    break;
                case 2:
                    userService.updateUser();
                    break;
                case 3:
                    userService.deleteUser();
                    break;
                case 4:
                    movieService.createMovie();
                    break;
                case 5:
                    movieService.deleteMovie();
                    break;
                case 6:
                    exit();
                    break;
                default:
                    System.out.println("Что-то не так, попробуйте снова\n");
            }
            startMenuAdministrator();
        }
    }

    private void exit() {
        userService.rewriteUsers();
        scanner.close();
        System.exit(0);
    }
}
