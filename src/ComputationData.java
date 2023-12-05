package src;

import java.io.Serializable;

public record ComputationData(double value, String functionId) implements Serializable {
}
