## Names: Cyusa Divince
## ID: 27516
## Course: Web Technology and Internet
## Group: E Wednesday Evening




## 🎫 Customer Complaint & Support Ticket Management System

> A RESTful Spring Boot application that allows Customers to submit complaints, which are assigned to support staff and managed through a structured ticket workflow
> — built on Rwanda's administrative location hierarchy.

---

## 📖 Description

This system is a backend API built with **Spring Boot** and **Spring Data JPA** that manages users, locations, support tickets, and support staff. It demonstrates 
real-world database relationships including self-referential, One-to-One, One-to-Many, and Many-to-Many, along with sorting, pagination, and recursive province-based 
user retrieval across Rwanda's full administrative hierarchy (Province → District → Sector → Cell → Village).

---

## ⚙️ Core Features

- 📍 **Location Management** — Save Rwanda's administrative hierarchy with parent-child linking
- 👤 **User Registration** — Register citizens with a One-to-One profile and link them to a location
- 🎫 **Ticket Submission** — Citizens submit complaints that are tracked through statuses
- 👨‍💼 **Staff Assignment** — Support staff are assigned to tickets via a Many-to-Many relationship
- 🔄 **Status Updates** — Employees can update ticket status (OPEN → IN_PROGRESS → RESOLVED → CLOSED)
- 🗑️ **Safe Deletion** — Only RESOLVED tickets can be deleted
- 📊 **Sorting** — Users can be retrieved sorted alphabetically by name
- 📄 **Pagination** — Large datasets are served in pages for better performance
- 🔎 **Province Search** — Retrieve all users under a province using its **code OR name** 
- ✅ **Duplicate Prevention** — `existsBy()` checks prevent duplicate emails and location codes

---

## 🛠️ Technologies Used

| Technology | Purpose |
|---|---|
| Java 21 | Core programming language |
| Spring Boot 3.x | Application framework |
| Spring Data JPA | Database ORM and query generation |
| Hibernate | JPA implementation |
| Postgresql | Relational database |
| Maven | Dependency management |
| Postman | API testing |

---

## ✅ Prerequisites

Before running this project make sure you have the following installed:

- **Java 
- **Maven 3.8.9
- **PostgreSQL 
- **Postman** (for testing)

### Database Setup
```sql
CREATE DATABASE SupportTicketSystem;
```

Then update `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/SupportTicketSystem
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
server.port=8082
```

### Running the Project
```bash
# Clone the repository
git clone https://github.com/your-username/CustomerComplaintSystem.git

# Navigate into the project
cd CustomerComplaintSystem

# Run with Maven
mvn spring-boot:run 
```

The server will start at: `http://localhost:8082`

---

## 🗄️ Entity Relationship Diagram (ERD)

![](https://github.com/Cyusa-Divince-27516/midterm_27516_groupE/blob/main/ScreenShots/ERD%20Diagram.png).


## 📬 API Endpoints

### 📍 Location Endpoints

---

#### `POST /api/locations/save` — Save a Location

Saves a location. For non-PROVINCE types, a `parentId` must be provided as a query parameter.

| Query Param | Required | Description |
|---|---|---|
| `parentId` | No (Yes for non-provinces) | UUID of the parent location |


**Type values:** `PROVINCE` `DISTRICT` `SECTOR` `CELL` `VILLAGE`

![](https://github.com/Cyusa-Divince-27516/midterm_27516_groupE/blob/main/ScreenShots/Save%20Province.png)

#### Save the district under province with the parentId from Province UUID
![](https://github.com/Cyusa-Divince-27516/midterm_27516_groupE/blob/main/ScreenShots/Save%20District.png)


---

#### `GET /api/locations/all` — Get All Locations

Returns all saved locations.

![](https://github.com/Cyusa-Divince-27516/midterm_27516_groupE/blob/main/ScreenShots/Retrieve%20All%20Locations.png)

---

### 👤 User Endpoints

---

#### `POST /api/users/save` — Register a User

Saves a new user. Attach a `locationId` as a query param. Profile is saved automatically via cascade.

| Query Param | Required | Description |
|---|---|---|
| `locationId` | Yes | UUID of the user's location (using village UUID) |



![](https://github.com/Cyusa-Divince-27516/midterm_27516_groupE/blob/main/ScreenShots/save%20User.png)

---

#### `GET /api/users/all?page=0&size=5` — Get All Users (Paginated)

Returns users in pages. Default is page `0` with `5` records per page.

| Query Param | Default | Description |
|---|---|---|
| `page` | `0` | Page number (starts at 0) |
| `size` | `5` | Number of records per page |

![](https://github.com/Cyusa-Divince-27516/midterm_27516_groupE/blob/main/ScreenShots/GetUsersBYPagination.png)

---

#### `GET /api/users/allSorted` — Get All Users Sorted A–Z

Returns all users sorted alphabetically by name of the person using Spring Data JPA `Sort`.

![](https://github.com/Cyusa-Divince-27516/midterm_27516_groupE/blob/main/ScreenShots/Retrieve%20All%20User%20Sorted.png)

---

#### `GET /api/users/byProvince?code=KGL` — Get Users by ProvinceCode 

Retrieves all users registered anywhere under the given province using **recursive location traversal** using ProvinceCode or ProvinceName

| Query Param | Required | Description |
|---|---|---|
| `code` | One of the two | Province code e.g. `KGL` |
| `name` | One of the two | Province name e.g. `Kigali City` |

**Examples:**
```
GET /api/users/byProvince?code=KGL
GET /api/users/byProvince?name=Kigali City
```

### retrieve Users Using ProvinceName eg: Eastern Province

![](https://github.com/Cyusa-Divince-27516/midterm_27516_groupE/blob/main/ScreenShots/Get%20users%20By%20Province%20Name.png)


### retrieve Users Using ProvinceCode eg: EST
---

![](https://github.com/Cyusa-Divince-27516/midterm_27516_groupE/blob/main/ScreenShots/Get_userByProvinceCode.png)

### 🎫 Ticket Endpoints

---

#### `POST /api/tickets/save` — Submit a Complaint Ticket

Creates a new ticket linked to the user who submitted it.

| Query Param | Required | Description |
|---|---|---|
| `userId` | Yes | UUID of the user creating the ticket |


**Status values:** `OPEN` `IN_PROGRESS` `RESOLVED` `CLOSED`

![](https://github.com/Cyusa-Divince-27516/midterm_27516_groupE/blob/main/ScreenShots/Create%20Ticket.png)

---

#### `POST /api/tickets/assign` — Assign Staff to a Ticket

Assigns a support staff member to a ticket. Updates the `ticket_staff`

![](https://github.com/Cyusa-Divince-27516/midterm_27516_groupE/blob/main/ScreenShots/AssignTicket%20To%20staff%20Member.png)

---

#### `PUT /api/tickets/updateStatus` — Update Ticket Status

Allows an employee to change the status of a ticket.



![](https://github.com/Cyusa-Divince-27516/midterm_27516_groupE/blob/main/ScreenShots/Update%20Ticket%20Status.png)

---

#### `DELETE /api/tickets/delete` — Delete a Resolved Ticket

Permanently deletes a ticket. **Only works if the ticket status is `RESOLVED`.** Any other status returns a `400` error.

![](https://github.com/Cyusa-Divince-27516/midterm_27516_groupE/blob/main/ScreenShots/Delete%20Resolved%20Reports.png)

---

#### `GET /api/tickets/all` — Get All Tickets

Returns all tickets submitted By the Customers.

![](https://github.com/Cyusa-Divince-27516/midterm_27516_groupE/blob/main/ScreenShots/Get%20All%20Tickets.png)

---

### 👨‍💼 Support Staff Endpoints

---

#### `POST /api/staff/save` — Register Support Staff

Saves a new staff member. Rejects duplicate emails.


![](http://github.com/Cyusa-Divince-27516/midterm_27516_groupE/blob/main/ScreenShots/Save%20Staff.png)

---

#### `GET /api/staff/all` — Get All Support Staff

Returns all registered support staff members.

![](https://github.com/Cyusa-Divince-27516/midterm_27516_groupE/blob/main/ScreenShots/Retrieve%20All%20Staffs.png)

---

## 🔗 Relationships Overview
```
locations ──(self-ref)──► locations
locations ──(1:N)───────► users
users     ──(1:1)───────► user_profiles
users     ──(1:N)───────► tickets
tickets   ──(M:N)───────► support_staff  [via ticket_staff]
```

---

## 📁 Project Structure
```
auca.ac.rw.CustomerComplaintSystem
├── controller
│     ├── LocationController.java
│     ├── UserController.java
│     ├── TicketController.java
│     └── SupportStaffController.java
├── service
│     ├── LocationService.java
│     ├── UserService.java
│     ├── TicketService.java
│     └── SupportStaffService.java
├── repository
│     ├── LocationRepository.java
│     ├── UserRepository.java
│     ├── TicketRepository.java
│     └── SupportStaffRepository.java
├── domain
│     ├── Location.java
│     ├── User.java
│     ├── UserProfile.java
│     ├── Ticket.java
│     ├── SupportStaff.java
│     ├── ELocationType.java
│     └── ETicketStatus.java
└── CustomerComplaintSystemApplication.java
```

---

## 👨‍💻 Author

**Cyusa Divince**
AUCA — Adventist University Of central Africa


---
