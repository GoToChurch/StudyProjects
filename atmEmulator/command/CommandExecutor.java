package atmEmulator.command;

import atmEmulator.Operation;
import atmEmulator.exception.InterruptOperationException;

import java.util.HashMap;
import java.util.Map;

public class CommandExecutor {
    private static Map<Operation, Command> allKnownCommandsMap = new HashMap<>() {{
        put(Operation.EXIT, new ExitCommand());
        put(Operation.INFO, new InfoCommand());
        put(Operation.DEPOSIT, new DepositCommand());
        put(Operation.WITHDRAW, new WithdrawCommand());
        put(Operation.LOGIN, new LoginCommand());
    }};

    private CommandExecutor() {}

    public static final void execute(Operation operation) throws InterruptOperationException {
        allKnownCommandsMap.get(operation).execute();
    }
}
