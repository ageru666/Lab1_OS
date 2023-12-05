package src;

import src.ComputationClient;

import java.util.logging.Logger;

public class ComputationClientF extends ComputationClient {
    private static final Logger logger = Logger.getLogger(ComputationServer.class.getName());
    private static double f(double x) {
        return x * x;
    }

    public static void main(String[] args) {
        double x = 5.5;
        if (isValidNumber(x)) {
            logger.info("f(x) = " + f(x));
            sendNumber("localhost", 1234, f(x), "f");
        } else {
            logger.warning("Недійсні вхідні дані");
        }
    }

    private static boolean isValidNumber(double number) {
        return number >= 0;
    }
}
