import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exitProgram = false;

        System.out.println("=== WELCOME TO THE MEAL ORDER SYSTEM ===");

        while (!exitProgram) {
            printMainMenu();
            int choice = getUserChoice(scanner, 1, 2);

            switch (choice) {
                case 1 -> createMealOrder(scanner);
                case 2 -> {
                    System.out.println("Thank you for using our system! Goodbye.");
                    exitProgram = true;
                }
            }
        }

        scanner.close();
    }

    private static void printMainMenu() {
        System.out.println("\n=== MAIN MENU ===");
        System.out.println("1. Create a new order");
        System.out.println("2. Exit");
        System.out.print("Select an option: ");
    }

    private static int getUserChoice(Scanner scanner, int min, int max) {
        int choice = -1;
        while (choice < min || choice > max) {
            try {
                choice = Integer.parseInt(scanner.nextLine());
                if (choice < min || choice > max) {
                    System.out.print("Invalid option. Please try again: ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid number: ");
            }
        }
        return choice;
    }

    private static void createMealOrder(Scanner scanner) {
        System.out.println("\n=== CREATE NEW ORDER ===");

        // Select burger type
        System.out.println("\nBURGER TYPE:");
        System.out.println("1. Regular (4.00$)");
        System.out.println("2. Deluxe (8.50$ - includes free drink and side)");
        System.out.print("Select an option: ");
        int burgerChoice = getUserChoice(scanner, 1, 2);

        String burgerType = (burgerChoice == 1) ? "regular" : "deluxe";

        // Select drink
        System.out.println("\nDRINK:");
        System.out.println("1. Coca-Cola");
        System.out.println("2. 7-Up");
        System.out.println("3. Water");
        System.out.println("4. Beer");
        System.out.print("Select an option: ");
        int drinkChoice = getUserChoice(scanner, 1, 4);

        String drinkType = switch (drinkChoice) {
            case 1 -> "coca-cola";
            case 2 -> "7-up";
            case 3 -> "water";
            case 4 -> "beer";
            default -> "coca-cola";
        };

        // Select side
        System.out.println("\nSIDE:");
        System.out.println("1. French Fries");
        System.out.println("2. Salad");
        System.out.println("3. Onion Rings");
        System.out.println("4. Chili");
        System.out.print("Select an option: ");
        int sideChoice = getUserChoice(scanner, 1, 4);

        String sideType = switch (sideChoice) {
            case 1 -> "fries";
            case 2 -> "salad";
            case 3 -> "rings";
            case 4 -> "chili";
            default -> "fries";
        };

        // Create the order
        MealOrder order = new MealOrder(burgerType, drinkType, sideType);

        // Drink Size
        System.out.println("\nDRINK SIZE:");
        System.out.println("1. Small (-0.50$)");
        System.out.println("2. Medium (standard price)");
        System.out.println("3. Large (+1.00$)");
        System.out.print("Select an option: ");
        int sizeChoice = getUserChoice(scanner, 1, 3);

        String drinkSize = switch (sizeChoice) {
            case 1 -> "SMALL";
            case 2 -> "MEDIUM";
            case 3 -> "LARGE";
            default -> "MEDIUM";
        };

        order.setDrinkSize(drinkSize);

        // Select additional toppings
        String[] availableToppings = {"AVOCADO", "CHEESE", "BACON", "HAM", "SALAMI", "LETTUCE", "MAYO", "KETCHUP", "MUSTARD"};

        if (burgerChoice == 1) {
            System.out.println("\nADDITIONAL TOPPINGS (maximum 3):");
            printToppingsMenu(availableToppings);

            String[] selectedToppings = new String[3];
            for (int i = 0; i < 3; i++) {
                System.out.print("Select topping #" + (i+1) + " (0 for none): ");
                int toppingChoice = getUserChoice(scanner, 0, availableToppings.length);

                if (toppingChoice == 0) {
                    selectedToppings[i] = "";
                } else {
                    selectedToppings[i] = availableToppings[toppingChoice-1];
                }
            }
            order.addBurgerToppings(selectedToppings[0], selectedToppings[1], selectedToppings[2]);

        } else { // Deluxe burger (5 toppings)
            System.out.println("\nADDITIONAL TOPPINGS (maximum 5, all free):");
            printToppingsMenu(availableToppings);

            String[] selectedToppings = new String[5];
            for (int i = 0; i < 5; i++) {
                System.out.print("Select topping #" + (i+1) + " (0 for none): ");
                int toppingChoice = getUserChoice(scanner, 0, availableToppings.length);

                if (toppingChoice == 0) {
                    selectedToppings[i] = "";
                } else {
                    selectedToppings[i] = availableToppings[toppingChoice-1];
                }
            }
            order.addBurgerToppings(selectedToppings[0], selectedToppings[1], selectedToppings[2],
                    selectedToppings[3], selectedToppings[4]);
        }

        // Show order summary
        System.out.println("\n=== YOUR ORDER SUMMARY ===");
        order.printItemizedList();

        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    private static void printToppingsMenu(String[] toppings) {
        System.out.println("0. None");
        for (int i = 0; i < toppings.length; i++) {
            System.out.println((i+1) + ". " + toppings[i]);
        }
    }
}