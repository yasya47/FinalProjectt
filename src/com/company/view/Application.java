package com.company.view;

import com.company.service.MenuService;

public class Application {
    private MenuService menuService;

    {
        menuService = new MenuService();
    }

    public void startApplication() {
        menuService.startWelcomeMenu();
    }
}
