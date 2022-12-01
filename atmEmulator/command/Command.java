package atmEmulator.command;

import atmEmulator.exception.InterruptOperationException;

interface Command {
    void execute() throws InterruptOperationException;
}
