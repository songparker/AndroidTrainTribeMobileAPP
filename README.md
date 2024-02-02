# Train Ticket Project Final Deliverable

```
Heping Song
```
# 1.1 System Planning

**a. Business Case:**

The Train Ticket Booking App is developed to provide a convenient and hassle-free experience
for booking train tickets. With the increasing number of people using trains for travel, this app
aims to streamline the ticket booking process and make it accessible from anywhere and
anytime. The app will be user-friendly, allowing users to search and book train tickets easily.

**b. SWOT Analysis:**

Strengths:

- Easy and convenient booking process.
- Provides real-time information on train schedules and availability.
- Ability to purchase tickets through the app.

Weaknesses:

- Dependency on the internet to use the app.
- Limited availability of train routes.
- Competition from other ticket booking apps.

Opportunities:

- Ability to expand the app to include other modes of transportation.
- Increase revenue through commission on ticket sales.
- Integration with other travel apps.

Threats:

- Economic instability and fluctuations in demand for travel.
- Emergence of new ticket booking apps.
- Changes in government policies and regulations.

**c. Constraints:**


- The app must be compatible with a range of mobile devices and operating systems.
- The app must adhere to privacy and security regulations.
- The app must be able to handle a large volume of users and transactions.

**d. Feasibility:**

The Train Ticket Booking App is feasible as it solves a common problem faced by travelers and
provides a convenient solution. The app is technically feasible as the required technologies and
resources are readily available. The app is financially feasible as revenue can be generated
through commission on ticket reservations.

**e. Project Management:**

The project will follow an Agile methodology, with regular sprints and iterations. A project
manager will oversee the development process, and a team of developers, designers, and
testers will work together to create the app.

# 1.2 System Analysis

**a. Fact Finding and answer the following questions:**

- Who: Train travelers.
- What: A mobile app for booking train tickets.
- Where: Anywhere.
- When: Anytime.
- How: Through a user-friendly mobile app.
- Why: To make the train ticket booking process convenient and hassle-free.

**b. Requirement Modeling:**

- Outputs: Train ticket bookings.
- Inputs: User details and travel information.
- Processes: Search and booking of train tickets.
- Performance: Quick and efficient ticket booking process.

**c. Data and Process Modeling as in Structured Analysis:**


Data modeling will include the creation of a data dictionary, and process modeling will include
the creation of a data flow diagram.

**d. Modeling Documents:**

- Class/ Object Diagram:
<img src="https://github.com/songparker/AndroidTrainTribeMobileAPP/blob/master/documentation/images/diagrams/Class_diagram.jpg?raw=true" alt="Class Diagram" width="800">

- Use Case Diagram:
<img src="https://github.com/songparker/AndroidTrainTribeMobileAPP/blob/master/documentation/images/diagrams/State_diagram.jpg?raw=true" alt="State Diagram" width="800">

- State Diagram:
<img src="https://github.com/songparker/AndroidTrainTribeMobileAPP/blob/master/documentation/images/diagrams/Use_case_diagram.jpg?raw=true" alt="Use Case Diagram" width="800">

- Data Dictionaries: All data attribute names are very straightforward.

# 1.3 System Design

**a. Development Strategies:**

The app will be designed using a blueprint mobile app design approach.

**b. System Design for Prototyping:**


The first prototype will include a physical design for the user interface, data storage, and
architecture.

```
i. User Interface Considerations: The user interface will be designed to be intuitive and
user-friendly.
ii. Data Considerations: The app will be designed to store and retrieve data efficiently.
iii. Architecture Considerations: The app will be designed to be scalable and easy to
maintain.
```
**c. Output Design:**

The app will provide visual output to users in the form of train schedules and ticket bookings.

```
i. Exception Reports: The app will provide notifications for exceptions such as ticket
soldout.
ii. Summary Reports: The app will provide a summary of the user's travel itinerary.
```
**d. User Interface Design:**

The user interface will be designed to be visually appealing and user-friendly, with clear labeling
and instructions. The interface will be intuitive and easy to navigate, with a consistent design
and layout across all screens.

**e. Data Design:**

The mobile app will use a data structure that allows for efficient storage and retrieval of user
data. Data will be stored in a file system or database system, depending on the specific needs of
the app.

```
i. Use Data Structure: The app will use a data structure that allows for easy
organization and retrieval of user data, such as a hierarchical or relational database.
ii. File System: The app will use a file system to store user data, allowing for easy
backup and recovery of data in the event of a system failure.
iii. Database System: The app will use a database system to manage user data, allowing
for efficient insertion, deletion, and updating of data. The app will also allow for
querying of the database to retrieve output data, such as travel plans and
reservations.
```
# 1.4. System Implementation


**a. System Architecture:**

**b. System Construction by now:**



**Deliverable3（edit user information, check train schedule）**

```
Deliverable
```

```
Deliverable
```
**I’ve also done the layout-landscape accordingly, for more details, please check my attached
android files to check the source codes.**

# 1.5. System Testing

**a. Unit Testing:**

In this phase, individual components of the system are tested in isolation to ensure they
function correctly. The main goal is to verify that each unit of the software performs as
designed. The unit testing process should cover all code paths and should include both positive
and negative test cases.

**b. Integration Testing:**

This phase involves testing the interaction between different components of the system. The
goal is to verify that the various components of the system work together as expected.
Integration testing can be done at various levels such as module integration, subsystem
integration, and system integration.

**c. System Testing:**


This phase involves testing the system as a whole to ensure that it meets the specified
requirements. The testing should cover all aspects of the system, including functional and non-
functional requirements. System testing should be done with realistic data and scenarios to
simulate real-world usage. The main goal of system testing is to identify defects that could
impact the system's overall performance or functionality.

# Project Deliverable3 Details

```
Heping Song
```
This file is more focused on what I have done for Deliverable3.

**1. Edit User information**

```
“Edit Your File”, “Confirm”, ”Return”
```
```
Before editing user info
```
```
After editing user info
```

**2. Check train schedule**

```
“Search Button”, “Schedule List” Click, “Confirm Booking”
```
**3. Book train ticket**


```
There are some conditions for the ticket number input, and the ticket availability would
be updated after the order has been confirmed; the user will get a notification about the
order confirmation and order information.
```
```
Order table would be generated accordingly as well.
```
**4. Other tables sample data**

```
Train Table
```
The schedule table will be updated after the new order changes the ticket availability.
