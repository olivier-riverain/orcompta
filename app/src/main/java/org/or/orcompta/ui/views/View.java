package org.or.orcompta.ui.views;

import org.or.orcompta.ui.controls.Controller;

import javafx.stage.Stage;

public interface View {
    public void initView(Stage stage, Controller controller);
    public void show();
    public void createView();
}
