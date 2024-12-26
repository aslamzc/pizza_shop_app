package com.aslam.app.Interfaces;

import com.aslam.app.Utils.SystemScanner;

public interface IProfileState {
    SystemScanner scanner = SystemScanner.getInstance();

    void display();
}
