package com.aslam.app.Commands;

import com.aslam.app.Interfaces.ICommand;

public class OrderCommand {

    private ICommand command;

    public void setCommand(ICommand command) {
        this.command = command;
    }

    public void run() {
        command.handle();
    }
}
