# Project Documentation: LGVB

This document provides a detailed overview of the LGVB (Leshka \& Friends Virtual Bank) project, including its architecture, structure, key components, and the design patterns used.

## 1\. Overview

The LGVB project is a desktop banking application built using Java Swing. It provides users with functionalities such as viewing their balance, managing cards, handling loans, and performing transactions. The application is designed with a focus on maintainability, scalability, and a clean separation of concerns through a layered architecture.

## 2\. Architectural Design

The project is built upon a **Multi-Layered Architecture** that strictly separates concerns. This is a more robust and scalable approach than a traditional MVC pattern, as it clearly defines the role of each component in the system. The primary layers are:

* **View Layer:** This is the outermost layer, responsible for the user interface. It is built with Java Swing components. Its sole responsibility is to display data to the user and capture user input. It does not contain any business logic.

  * *Key Components:* `MainView`, `Dashboard`, `Wallet`, `Sidebar`.

* **Controller Layer:** This layer acts as an intermediary between the View and the Service Layer. It receives events from the View (e.g., button clicks), processes them, and makes calls to the Service Layer to perform business operations. It then takes the result from the services and updates the View.

  * *Key Components:* `MainController`, `LoanController`, `DashboardController`.

* **Service Layer:** This layer contains all the core business logic of the application. It orchestrates data and calls between different DAOs to fulfill complex business rules. It is completely independent of the UI.

  * *Key Components:* `LoanService`, `WalletService`, `AuthService`.

* **Data Access Layer (DAO):** This layer is responsible for all communication with the database. It abstracts the details of persistence, providing a clean API for the Service Layer to perform CRUD (Create, Read, Update, Delete) operations without needing to know the underlying SQL.

  * *Key Components:* `LoanDAO`, `WalletDAO`, `UserDAO`.

* **Model/Domain Layer:** This layer consists of the plain data objects that represent the entities in the application, such as `User`, `Loan`, and `Wallet`. These objects are passed between the other layers.

This strict separation ensures that changes in one layer (e.g., switching the UI from Swing to web) have minimal impact on the other layers.

## 3\. Project Structure

The source code is organized into packages based on their functional roles within the architecture.

```
src/main/java/com/leshka\_and\_friends/lgvb/
├── auth/              # User authentication and session management.
├── core/              # Core business logic, services, and controllers.
│   ├── app/           # Main application controllers and service registration.
│   ├── card/
│   ├── loan/
│   └── user/
├── exceptions/        # Custom exception classes.
├── notification/      # Notification system (Observer pattern).
├── utils/             # Utility classes like database connection managers.
└── view/              # All Swing UI components (Views).
    ├── components/
    ├── dashboard/
    ├── forms/
    └── wallet/
```

* `src/main/resources/`: Contains non-code assets like images, fonts, and the SQL setup scripts.
* `pom.xml`: The Maven project file, defining dependencies and build configurations.

## 4\. Key Components \& Class Descriptions

Below are descriptions of the most relevant classes, grouped by their responsibility.

### Application Entry \& Control

* `LGVB.java`: The main entry point of the application.
* `core.app.AppController`: Orchestrates the application lifecycle, managing the login flow and initialization of the main UI and controllers.
* `core.app.MainController`: The root controller that delegates tasks to specialized sub-controllers.

### Authentication \& Session Management

* `auth.AuthController`: Manages the logic for the login screen.
* `auth.AuthService`: Contains the business logic for verifying user credentials.
* `auth.SessionManager`: A singleton class responsible for managing the user session.

### Service Layer \& Data Management

* `core.app.ServiceRegistry` \& `ServiceLocator`: Implements the **Service Locator** pattern for dependency management.
* `\*.service.\*Service` (e.g., `LoanService`): Interfaces and implementations containing the application's business logic.
* `\*.dao.\*DAO` (e.g., `LoanDAO`): Classes that implement the **Data Access Object (DAO)** pattern for database operations.

## 5\. Design Patterns Used

The project leverages several key design patterns to ensure a clean, scalable, and maintainable codebase.

* **Factory Method:** This pattern is used to create objects without specifying the exact class of object that will be created. The different loan types (`PersonalLoan`, `CarLoan`, `HousingLoan`) are designed to be instantiated by a factory, allowing the system to be easily extended with new loan products in the future.

  * **Location:** The hierarchy under `core.loan.Loan` and its subtypes in `core.loan.types`.

* **Singleton:** This pattern ensures that a class has only one instance and provides a global point of access to it. This is crucial for managing shared resources.

  * **Location:** `utils.DBConnection` (for a single database connection), `auth.SessionManager` (to manage the single user session), and `core.app.ServiceLocator` (to provide global access to services).

* **Proxy:** A proxy object is used to control access to another object. The `LoanServiceProxy` is a "Protection Proxy" that adds a layer of security. It intercepts calls to the real loan service to ensure that only users with the `ADMIN` role can approve or reject loans.

  * **Location:** `core.loan.LoanServiceProxy.java`.

* **Facade:** This pattern provides a simplified, unified interface to a more complex subsystem. The `AppFacade` (and other facades like `LoanFacade`) simplifies the interactions between the Controller layer and the underlying Service layer.

  * **Location:** `core.app.AppFacade`, `core.loan.LoanFacade`.

* **State:** This pattern allows an object to alter its behavior when its internal state changes. The `Loan` object behaves differently based on its `status` field (`PENDING`, `ACTIVE`, `CLOSED`). Logic in `LoanServiceImpl` checks the loan's state before performing operations like approving or rejecting it.

  * **Location:** The `status` field within `core.loan.Loan.java` and the state-driven logic in `core.loan.LoanServiceImpl.java`.

* **Observer:** This pattern defines a one-to-many dependency between objects so that when one object changes state, all its dependents are notified and updated automatically. It is used for the application's notification system.

  * **Location:** The `notification` package.

## 6. Application Flow Example: Applying for a Loan

To understand how the architecture and design patterns work together, let's trace the flow of a user applying for a new loan.

1.  **User Interaction (View Layer):**
    *   The user is on the `LoanView` panel and clicks the "Apply for Loan" button.

2.  **Event Handling (Controller Layer):**
    *   The `LoanController` receives the `ActionEvent` from the button. It gathers the required data (e.g., amount, purpose) from the form fields in the view.

3.  **Simplified Interface (Facade Pattern):**
    *   Instead of interacting with multiple services, the `LoanController` calls a single method on the `LoanFacade`, for example: `loanFacade.applyForLoan(amount, purpose)`.

4.  **Access Control (Proxy Pattern):**
    *   The `LoanFacade` retrieves the `LoanService` from the `ServiceLocator`. The registered service is actually the `LoanServiceProxy`.
    *   The call to `applyForLoan` is first intercepted by the `LoanServiceProxy`. It checks the currently logged-in user's role from the `SessionManager` (**Singleton**). Since the user is not an admin, the proxy determines they are allowed to apply, and forwards the call to the real `LoanServiceImpl`.

5.  **Business Logic (Service Layer):**
    *   The `LoanServiceImpl` executes the core business logic.
    *   It may use a **Factory Method** to create a new `PersonalLoan` object (**Model/Domain Layer**).
    *   It calculates the interest rate and generates a unique reference number.
    *   It sets the initial status of the new `Loan` object to `PENDING`. This demonstrates the **State Pattern**, as this object will behave differently later depending on this status.

6.  **Data Persistence (Data Access Layer):**
    *   The `LoanServiceImpl` passes the newly created `Loan` object to the `LoanDAO`.
    *   The `LoanDAO` handles the final step, transforming the `Loan` object into an SQL `INSERT` statement and executing it against the database via the `DBConnection` (**Singleton**).

7.  **Feedback to User:**
    *   Once the DAO confirms the insertion, the flow returns up the chain. The `LoanService` might return the new loan's reference number.
    *   The `LoanController` receives this information and updates the `LoanView` to show a success message to the user. If any step failed, a failure message would be shown instead.

This example illustrates how each layer has a distinct responsibility and how design patterns are used to create a secure, maintainable, and decoupled system.
