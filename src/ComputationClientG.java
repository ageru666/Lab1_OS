package src;

import src.ComputationClient;

import java.util.logging.Logger;

public class ComputationClientG extends ComputationClient {
    private static final Logger logger = Logger.getLogger(ComputationServer.class.getName());
    private static double g(double x) {
        return 3 * x;
    }

    public static void main(String[] args) {
        double x = 5.5;
        if (isValidNumber(x)) {
            logger.info("g(x) = " + g(x));
            sendNumber("localhost", 1234, g(x), "g");
        } else {
            logger.warning("Недійсні вхідні дані");
        }
    }

    private static boolean isValidNumber(double number) {
        return number >= 0;
    }
}
