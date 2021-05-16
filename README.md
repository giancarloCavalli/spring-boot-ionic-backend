# Online Shopping
[![NPM](https://img.shields.io/npm/l/react)](https://github.com/giancarloCavalli/spring-boot-ionic-backend/blob/master/LICENSE) 

## About the project
Online shopping is a full stack application that works as an e-commerce. It was built during the course named "Spring Boot, Hibernate, REST, Ionic, JWT, S3, MySQL, MongoDB" from Udemy. Thanks to professor [Nelio Alves](https://github.com/acenelio), his didactics are amazing.

# Menu
- [Features](#features-)
- [Architecture](#architecture-)
- [Stack](#stack-)
- [How to run the app](#how-to-run-the-app-)
- [App demonstration](#app-demonstration-)
- [Author](#author-)

# Features [🔝](#online-shopping)
- User signup
- User login
- Shopping cart
- Shopping pages
- Photo capturing using camera
- Image/photo upload to Amazon S3
- Automated email sending
- Infinite scroll

# Architecture [🔝](#online-shopping)
![conceptual_model](https://github.com/giancarloCavalli/Assets/blob/master/spring-boot-ionic/diagrama.png)

![architecture](https://github.com/giancarloCavalli/Assets/blob/master/spring-boot-ionic/Arquitetura%20em%20camadas.png)

# Stack [🔝](#online-shopping)
## Back end
- Java 11
- Amazon S3
- JWT Token
- Spring Data
- Spring Web
- Spring Security
- JPA / Hibernate
- Maven
- Thymeleaf
- H2 Database
## Front end ([repo](https://github.com/giancarloCavalli/spring-boot-ionic-frontend))
- AngularJS
- Ionic
- HTML / CSS / JS / TypeScript
- Bootstrap

# How to run the app [🔝](#online-shopping)

## Config
<h3>application.properties</h3>
You can switch between "test" and "dev" environment by changing the value as shown below <br>

`spring.profiles.active=test` <br>
- test: uses mockemail service, so it doesn't use smtp email services. It uses H2 database (doesn't require any database installation) <br>
- dev: uses smtp email service, so it requires the config related to email service. It requires database instalation (in this project it is configured to use MySQL)

```
spring.jpa.open-in-view=false

default.sender=<your_email_sender>
default.recipient=
```

<h4>:heavy_exclamation_mark: If you fork this repo, be cautious to not commit changes with your amazon AWS private information :heavy_exclamation_mark:</h4>

```
jwt.secret=<secret_caracters_on_token_generation>
jwt.expiration=<expiration_time_in_milliseconds>

aws.access_key_id=<access_key>
aws.secret_access_key=<secret_access>
s3.bucket=<amazon_S3_bucket_name>
s3.region=<s3_region>

img.prefix.client.profile=<prefix_to_the_profile_pictures>
img.profile.size=200

spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB
```

ps: If you use a gmail account, you gotta set a configuration that allow the app access to it. You can do it following [this steps](https://hotter.io/docs/email-accounts/secure-app-gmail/)

<h3>application-test.properties</h3>
You can leave it this way or change the "testdb" to another database name.

```
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

<h3>application-dev.properties</h3>

```
spring.datasource.url=jdbc:mysql://localhost:3306/curso_spring
spring.datasource.username=root
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=create
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

<h4>:heavy_exclamation_mark: If you fork this repo, be cautious to not commit changes with your email private information :heavy_exclamation_mark:</h4>
It is preconfigured with gmail setup, you just need to set your username(email) and password.

```
spring.mail.host=smtp.gmail.com
spring.mail.username=
spring.mail.password=
spring.mail.properties.mail.smtp.auth = true
spring.mail.properties.mail.smtp.socketFactory.port = 465
spring.mail.properties.mail.smtp.socketFactory.class = javax.net.ssl.SSLSocketFactory
spring.mail.properties.mail.smtp.socketFactory.fallback = false
spring.mail.properties.mail.smtp.starttls.enable = true
spring.mail.properties.mail.smtp.ssl.enable = true
```

## Back end
Prerequisits: Java 11, MySQL

```bash
# clone repository
git clone https://github.com/giancarloCavalli/spring-boot-ionic-backend

# open backend folder
cd spring-boot-ionic-backend

# run project using maven
./mvnw spring-boot:run
```

# App demonstration [🔝](#online-shopping)
## Signup page
![Signup page](https://github.com/giancarloCavalli/Assets/blob/master/spring-boot-ionic/SignupPage.gif)
## Shopping pages
![Shopping pages](https://github.com/giancarloCavalli/Assets/blob/master/spring-boot-ionic/Shopping%20e%20CartPage.gif)
## Profile & Photo upload to Amazon S3 bucket
![Profile photo upload](https://github.com/giancarloCavalli/Assets/blob/master/spring-boot-ionic/Profile-%20Photo%20upload.gif)
## Order conclusion
![Order conclusion](https://github.com/giancarloCavalli/Assets/blob/master/spring-boot-ionic/Order%20conclusion.gif)

# Author [🔝](#online-shopping)

Giancarlo Cavalli

[![Linkedin Badge](https://img.shields.io/badge/-LinkedIn-blue?style=for-the-badge&logo=Linkedin&logoColor=white&link=https://www.linkedin.com/in/ntfm/)](https://www.linkedin.com/in/giancarlo-cavalli-933385175/)
[![Gmail Badge](https://img.shields.io/badge/-Gmail-c14438?style=for-the-badge&logo=Gmail&logoColor=white&link=mailto:gc.giancarloo@gmail.com)](mailto:gc.giancarloo@gmail.com)
[![Instagram Badge](https://img.shields.io/badge/Instagram-E4405F?style=for-the-badge&logo=instagram&logoColor=white)](https://www.instagram.com/giancarloc_/)

