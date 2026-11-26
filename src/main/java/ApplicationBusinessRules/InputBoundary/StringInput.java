package ApplicationBusinessRules.InputBoundary;

public class StringInput implements InputBoundary {

    private String input;

    public StringInput(String input) {
        this.input = input.trim();

    }

    public String getInput() {
        return input;
    }
}
