package ApplicationBusinessRules.InputBoundary;

public class StringInput {

    private String input;

    public StringInput(String input) {
        String trimmed = input.trim();
        if(trimmed.matches("\\d+")) { // if the string is just numbers
            this.input = "";
        }
        else {
            this.input = trimmed;
        }
    }

    public String getInput() {
        return input;
    }
}
