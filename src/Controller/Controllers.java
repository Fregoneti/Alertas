package Controller;

import Play.Main;

public class Controllers {

    Main app;
    public void setMainApp(Main app){
        this.app=app;
        this.onLoad();
    }
    //To be ovewritten
    void onLoad(){};
    //To be ovewritten
    void doOnCloseModal(Object response){}
}
