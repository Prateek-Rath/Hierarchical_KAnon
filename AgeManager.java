public class AgeManager implements LevelManager {

    public String manage(String value) {
        try {
            int age = Integer.parseInt(value);
            if (age < 18000) {
                return "Child";
            } else if (age < 65) {
                return "Adult";
            } else {
                return "Senior";
            }
        } catch (NumberFormatException e) {
            System.err.println("Invalid age value: " + value);
            return value; // Return original value if it's not a valid integer
        }
    }
}