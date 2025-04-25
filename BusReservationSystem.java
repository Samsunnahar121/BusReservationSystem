import java.sql.*;
import java.util.*;

public class BusReservationSystem {
    // ANSI color codes
    static final String RESET = "\u001B[0m";
    static final String RED = "\u001B[31m";
    static final String GREEN = "\u001B[32m";
    static final String YELLOW = "\u001B[33m";
    static final String BLUE = "\u001B[34m";
    static final String PURPLE = "\u001B[35m";
    static final String CYAN = "\u001B[36m";

    static final String DB_URL = "jdbc:mysql://localhost:3306/bus_reservation";
    static final String DB_USER = "root";
    static final String DB_PASS = "Samsunnahar1*";
    static Connection conn;
    static Scanner scanner = new Scanner(System.in);
    static int currentUserId = -1;

    public static void main(String[] args) {
        printWelcomeArt();
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            while (true) {
                printMainMenu();
                int choice = getIntInput("Choose an option [1-4]: ", 1, 4);
                
                switch (choice) {
                    case 1 -> register();
                    case 2 -> login();
                    case 3 -> searchBuses();
                    case 4 -> {
                        printExitArt();
                        System.exit(0);
                    }
                }
            }
        } catch (SQLException e) {
            printError("Database Error: " + e.getMessage());
        }
    }

    private static void printWelcomeArt() {
        System.out.println(CYAN + "==============================================");
        System.out.println("   _____ _    ___  ____  ____  _____ ____  ");
        System.out.println("  | __  | |  | |  |    \\|    \\|   __|    \\ ");
        System.out.println("  | __ -| |__| |  |  |  |  |  |   __|  |  |");
        System.out.println("  |_____|_____|___|____/|____/|_____|____/ ");
        System.out.println("==============================================" + RESET);
    }

    private static void printMainMenu() {
        System.out.println(YELLOW + "\n╔════════════════════════════╗");
        System.out.println("║       MAIN MENU            ║");
        System.out.println("╠════════════════════════════╣");
        System.out.println("║ 1. " + GREEN + "Register" + YELLOW + "             ║");
        System.out.println("║ 2. " + BLUE + "Login" + YELLOW + "                ║");
        System.out.println("║ 3. " + PURPLE + "Search Buses" + YELLOW + "        ║");
        System.out.println("║ 4. " + RED + "Exit" + YELLOW + "                 ║");
        System.out.println("╚════════════════════════════╝" + RESET);
    }

    static void register() throws SQLException {
        System.out.println(CYAN + "\n╔════════════════════════════╗");
        System.out.println("║       REGISTRATION         ║");
        System.out.println("╚════════════════════════════╝" + RESET);
        
        String name = getInput("Full Name: ");
        String email = getInput("Email: ");
        String password = getInput("Password: ");

        String query = "INSERT INTO users (full_name, email, password) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, password);
            stmt.executeUpdate();
            printSuccess("Registration successful!");
        }
    }

    static void login() throws SQLException {
        System.out.println(BLUE + "\n╔════════════════════════════╗");
        System.out.println("║         LOGIN             ║");
        System.out.println("╚════════════════════════════╝" + RESET);
        
        String email = getInput("Email: ");
        String password = getInput("Password: ");

        String query = "SELECT * FROM users WHERE email=? AND password=?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                currentUserId = rs.getInt("user_id");
                printSuccess("Welcome back, " + rs.getString("full_name") + "!");
                userDashboard();
            } else {
                printError("Invalid credentials");
            }
        }
    }

    static void userDashboard() throws SQLException {
        while (true) {
            System.out.println(PURPLE + "\n╔════════════════════════════╗");
            System.out.println("║      USER DASHBOARD       ║");
            System.out.println("╠════════════════════════════╣");
            System.out.println("║ 1. " + GREEN + "Book Ticket" + PURPLE + "         ║");
            System.out.println("║ 2. " + BLUE + "View Bookings" + PURPLE + "        ║");
            System.out.println("║ 3. " + RED + "Cancel Ticket" + PURPLE + "         ║");
            System.out.println("║ 4. " + YELLOW + "Logout" + PURPLE + "               ║");
            System.out.println("╚════════════════════════════╝" + RESET);

            int choice = getIntInput("Choose option [1-4]: ", 1, 4);
            switch (choice) {
                case 1 -> bookTicket();
                case 2 -> viewBookings();
                case 3 -> cancelTicket();
                case 4 -> {
                    currentUserId = -1;
                    return;
                }
            }
        }
    }

    static void searchBuses() throws SQLException {
        System.out.println(CYAN + "\n╔════════════════════════════╗");
        System.out.println("║       BUS SEARCH          ║");
        System.out.println("╚════════════════════════════╝" + RESET);
        
        String source = getInput("From: ");
        String destination = getInput("To: ");

        String query = "SELECT * FROM buses WHERE source = ? AND destination = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, source);
            stmt.setString(2, destination);
            ResultSet rs = stmt.executeQuery();

            System.out.println(GREEN + "\n+---------+------------------+------------+-----------+");
            System.out.println("| Bus ID  | Bus Name         | Type       | Fare      |");
            System.out.println("+---------+------------------+------------+-----------+");
            while (rs.next()) {
                System.out.printf("| %-7d | %-16s | %-10s | $%-8.2f |\n",
                    rs.getInt("bus_id"),
                    rs.getString("bus_name"),
                    rs.getString("bus_type"),
                    rs.getDouble("fare"));
            }
            System.out.println("+---------+------------------+------------+-----------+" + RESET);
        }
    }

    static void bookTicket() throws SQLException {
        searchBuses();
        int busId = getIntInput("Enter Bus ID to book: ", 1, 999);
        int seats = getIntInput("Number of seats: ", 1, 10);

        String fareQuery = "SELECT fare FROM buses WHERE bus_id = ?";
        try (PreparedStatement fareStmt = conn.prepareStatement(fareQuery)) {
            fareStmt.setInt(1, busId);
            ResultSet rs = fareStmt.executeQuery();

            if (rs.next()) {
                double totalFare = rs.getDouble("fare") * seats;
                String insert = "INSERT INTO bookings (user_id, bus_id, seats, total_fare) VALUES (?, ?, ?, ?)";
                try (PreparedStatement insertStmt = conn.prepareStatement(insert)) {
                    insertStmt.setInt(1, currentUserId);
                    insertStmt.setInt(2, busId);
                    insertStmt.setInt(3, seats);
                    insertStmt.setDouble(4, totalFare);
                    insertStmt.executeUpdate();
                    printSuccess(String.format("Booked successfully! Total: $%.2f", totalFare));
                }
            } else {
                printError("Invalid Bus ID");
            }
        }
    }

    static void viewBookings() throws SQLException {
        String query = "SELECT * FROM bookings WHERE user_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, currentUserId);
            ResultSet rs = stmt.executeQuery();

            System.out.println(BLUE + "\n+-------------+---------+--------+-------------+");
            System.out.println("| Booking ID  | Bus ID  | Seats  | Total Fare  |");
            System.out.println("+-------------+---------+--------+-------------+");
            while (rs.next()) {
                System.out.printf("| %-11d | %-7d | %-6d | $%-10.2f |\n",
                    rs.getInt("booking_id"),
                    rs.getInt("bus_id"),
                    rs.getInt("seats"),
                    rs.getDouble("total_fare"));
            }
            System.out.println("+-------------+---------+--------+-------------+" + RESET);
        }
    }

    static void cancelTicket() throws SQLException {
        int bookingId = getIntInput("Enter Booking ID to cancel: ", 1, 9999);
        String query = "DELETE FROM bookings WHERE booking_id = ? AND user_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, bookingId);
            stmt.setInt(2, currentUserId);
            int rows = stmt.executeUpdate();
            
            if (rows > 0) {
                printSuccess("Booking cancelled successfully");
            } else {
                printError("No booking found with ID: " + bookingId);
            }
        }
    }

    // Helper methods
    private static String getInput(String prompt) {
        System.out.print(YELLOW + "➤ " + RESET + prompt);
        return scanner.nextLine();
    }

    private static int getIntInput(String prompt, int min, int max) {
        while (true) {
            try {
                System.out.print(YELLOW + "➤ " + RESET + prompt);
                int input = Integer.parseInt(scanner.nextLine());
                if (input >= min && input <= max) return input;
                printError("Please enter between " + min + "-" + max);
            } catch (NumberFormatException e) {
                printError("Invalid number format");
            }
        }
    }

    private static void printSuccess(String message) {
        System.out.println(GREEN + "✓ " + message + RESET);
    }

    private static void printError(String message) {
        System.out.println(RED + "✗ " + message + RESET);
    }

    private static void printExitArt() {
        System.out.println(RED + "\n==============================================");
        System.out.println("   ______                _ _             ");
        System.out.println("  |  ____|              | | |            ");
        System.out.println("  | |__ ___  _   _ _ __ | | | _____ _ __ ");
        System.out.println("  |  __/ _ \\| | | | '_ \\| | |/ / _ \\ '__|");
        System.out.println("  | | | (_) | |_| | | | | |   <  __/ |   ");
        System.out.println("  |_|  \\___/ \\__,_|_| |_|_|_|\\_\\___|_|   ");
        System.out.println("==============================================" + RESET);
    }
}