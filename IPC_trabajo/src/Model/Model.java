package Model;

import Views.MainView;

public class Model {
    private static Model model;
    private final MainView mainView;

    private Model(){
        this.mainView = new MainView();
    }

    public static synchronized Model getInstance(){
        if (model == null){
            model = new Model();
        }
        return model;
    }

    public MainView getMainView(){
        return mainView;
    }
}
