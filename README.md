<img src="https://i.ibb.co/JTkG8x6/testimgur.png" alt="Texto alternativo" width="50%" />



Backend repository for the **Happy Troublers** project:  
https://github.com/HappyTroublers/HappyTroublers-Backend

Frontend of the project:  
https://github.com/P1-FemCoders-VLC/happy-travel-front.git

---

## ⚓ Project Objective

> ⛵ Ahoy! Speaking of vacations… who doesn't love sailing the seas and discovering new lands?
HappyTroublers be a web application for all bold adventurers who love to happily stir the waters on their journeys — 'cause the best voyages be about chartin' courses beyond the usual ports, arrr!


- **Unauthenticated users** can view dream destinations shared by others.
- **Authenticated users** can create, edit, and delete their own destinations.

This repository contains the development of the project's **backend API**, built with **Java and Spring Boot**, following a RESTful architecture and connected to a **MySQL** database. The frontend has already been partially developed and was provided by Bootcamp Factoría F5.

---

## 💻 Technologies Used

### Backend:
- Java
- Maven
- Spring Boot
- Spring Security
- JWT
- MySQL
- Lombok
- Mockito

### Frontend (provided by the client):
- HTML
- CSS
- JavaScript
- React.js
- Next.js

---

## 🛠 Tools
- IntelliJ IDEA
- Visual Studio Code
- Jira
- Git & GitHub
- Postman

---

### 🚧 Under development using layered MVC architecture.

## 🔱‍ Main Features (MVP)

### Users (In progress)
- User registration and login
- Passwords encrypted with BCrypt
- Authentication with JWT (Bearer Token)
- Protected routes for authenticated users
- User management accessible only by administrators:
    - View full list
    - Edit user data and roles
    - Delete users

### Destinations
- Create new destinations (authenticated users)
- View full list of destinations (authenticated and unauthenticated users)
- Filter by title or location (In progress)
- Only the creators can edit or delete their destinations

---

## 🔒 Frontend-backend connection (In progress)

- CORS configured to allow requests from `http://localhost:3000`
- Endpoint and field verification used by the frontend

---

# 🗡️ Installation
## By Dockerfile
### In Docker, create a file called Dockerfile with this content:

FROM nginx:alpine  
COPY . /usr/share/nginx/html  
EXPOSE 80  
CMD ["nginx", "-g", "daemon off;"]

### Then, in your terminal, build your image:

docker build -t HappyTroublers-Backend .

### And run the container:

docker run -p 8080:80 HappyTroublers-Backend

### Then, open your browser at:
http://localhost:8080

---

## 📧 Made by:
#### Bruna Sonda
https://github.com/brunasonda

#### Judit Corbalán
https://github.com/J-uds

#### Lara Pla Moreno
https://github.com/Lizar22

#### Mariya Byehan
https://github.com/Marichka75

### Thank you! ✨






