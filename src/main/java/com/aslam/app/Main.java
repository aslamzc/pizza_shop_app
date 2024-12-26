package com.aslam.app;

import com.aslam.app.System.App;
import com.aslam.app.Utils.SystemScanner;

public class Main {

    public static void main(String[] args) {

        SystemScanner scanner = SystemScanner.getInstance();
        new App(scanner).run();

    }
}