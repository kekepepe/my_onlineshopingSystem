# Online Shopping System (COMP603 Assignment)

## Project Overview
This is a desktop-based Online Shopping System developed in Java (JDK 21) using Swing for the GUI and Apache Derby for the database.

## Key Features
- **Zero Configuration**: The database is automatically created and seeded with initial data upon the first run.
- **GUI**: Built entirely with Java Swing (no FXML or Web technologies).
- **Design Patterns**: Implements **Strategy Pattern** for payments and **DAO Pattern** for data access.
- **OOP Principles**: Demonstrates Encapsulation, Inheritance (Product hierarchy), and Polymorphism.

## How to Run
### Prerequisites
- JDK 21 or higher.
- Maven (optional, if building from source).
- NetBeans IDE (recommended).

### Running in NetBeans
1. Open the project in NetBeans.
2. Right-click the project and select **Run**.
3. The application will launch.
4. **Note**: A folder named `shopping_db` will be created in the project root directory. This contains the embedded database.

### Login Credentials (Seed Data)
- **Admin/User**: `admin` / `admin123` (Balance: $1000)
- **Regular User**: `user` / `user123` (Balance: $50)

## Project Structure
- `src/main/java/com/comp603/shopping/models`: Domain entities (User, Product, Order).
- `src/main/java/com/comp603/shopping/dao`: Database access logic.
- `src/main/java/com/comp603/shopping/services`: Business logic and Strategy implementations.
- `src/main/java/com/comp603/shopping/gui`: Swing UI panels and frames.
- `src/main/java/com/comp603/shopping/config`: Database connection and auto-initialization.

## Design Highlights
- **Polymorphism**: `Product` is an abstract class with `PhysicalProduct` and `DigitalProduct` subclasses. The UI treats them uniformly but displays different details based on type.
- **Strategy Pattern**: `PaymentStrategy` interface allows swapping between `CreditCardStrategy` and `WalletStrategy` dynamically during checkout.
