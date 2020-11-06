package com.group11.cmpt276_project.viewmodel;

public class MainPageViewModel {

    private int selectedTab;

    private static class MainPageViewModelHolder {
        private static final MainPageViewModel INSTANCE = new MainPageViewModel();
    }

    private MainPageViewModel() {
        this.selectedTab = 0;
    }

    public static MainPageViewModel getInstance() {
        return MainPageViewModelHolder.INSTANCE;
    }

    public void setSelectedTabTab(int tab) {
        this.selectedTab = tab;
    }

    public int getSelectedTab() {
        return this.selectedTab;
    }
}
