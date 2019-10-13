<img src="https://github.com/MarcinNidecki/car-door-web-service/blob/master/src/main/resources/static/img/logo2.png" width="350"/>

# 1. Opis ogólny

>The project was designed using:
>Java, Spring Boot, Spring Data, Spring MVC, Spring Security, Hibernate, REST, Swagger, Lombok, Junit, Thymeleaf, HTLM, >CSS, JavaScript, Gradle, Git, Sonar, CircleCi.
>
>  http://cardoor.mnidecki.pl         https://sleepy-oasis-69014.herokuapp.com


## PROJECT NAME
*CARDOOR RENTAL*

## User feature:
<ul>
  <li>searching car according to availability on given dates</li>
  <li>filtering results by car parameters</li>
  <li>selection of additional options for booking</li>
  <li>creating an account while completing the necessary data for booking</li>
  <li>or login while creating bookings</li>
  <li>account activation by clicking the link</li>
  <li>email with booking confirmation</li>
 <li>adding comments and rating star to the car (for a given model)</li>
</ul>


## Admin feature:

<ul>
  <li>adding the make and model of the car</li>
  <li>adding a car type (economy, premium ...)</li>
  <li>adding the picture</li>
  <li>adding a car in the exact specification</li>
  <li>simple preview of booking list</li>

 <li>order and user manipulation will be implemented in the next stage</li>
</ul>






# 2. Application Settings:

## 2.1 DATABSE MySQL

It is recommended to create a MySQL database using scripts located in the sql directory.

DEMO access data:

l: admin ****
l: user ****






# 2. application.properties


# DATABASE
At the beginning you should set the access data to the ftp server.

spring.jpa.hibernate.ddl-auto=update
spring.jpa.database=mysql
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5Dialect
spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true
spring.datasource.url=jdbc:mysql://localhost:3306/testowa?serverTimezone=Europe/Warsaw&allowPublicKeyRetrieval=true&useSSL=False
spring.datasource.username=********
spring.datasource.password=*******


--------
# KAYAK API
KAYAK API - rapidapi.com // about 200 queries per month
The application finds the average car rental price in the location we search (according to data from the kajak.pl portal).

Therefore, we need to configure the credentials.

kayak.api.location.endpoint=https://apidojo-kayak-v1.p.rapidapi.com/locations/search?where=
kayak.api.car.endpoint=https://apidojo-kayak-v1.p.rapidapi.com/cars/create-session

kayak.api.header.key.name=x-rapidapi-key   
kayak.api.header.key.value=MISSING KEY

kayak.api.header.host.name=x-rapidapi-host
kayak.api.header.host.value=apidojo-kayak-v1.p.rapidapi.co

----------
# ACCU WEATHER
ACCU WEATHER API - developer.accuweather.com
Every day at 7 am, the application downloads the weather for all cities added to the database and saves it without exceeding the query limit.
You can get the key for free at rapidapi.com or use the one provided by me.


accu.weather.endpoint=http://dataservice.accuweather.com
accu.weather.key=MISSING KEY!

---------
# FTP CONFIGURATION
Due to the limited hosting of heroku, I had to bypass file write lock.
Enter data to the ftp server and the path where the files will be saved!
It is necessary for the correct saving and displaying of photos!


ftp.host=${FTP_HOST}

ftp.login=${FTP_USERNAME}

ftp.password=${FTP_PASSWORD}

ftp.domain.image.path=http://debowyzakatek.pl/images/


---------
# EMAIL

You must configure an email address.

E-mails are sent after creating user account and as confirmation of car rental.

admin.mail=${ADMIN_MAIL}

spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=${MAIL_USERNAME}
spring.mail.password=${MAIL_PASSWORD}
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
spring.mail.properties.mail.smtp.starttls.required=true
spring.mail.properties.mail.smtp.connectiontimeout=5000
spring.mail.properties.mail.smtp.timeout=5000
spring.mail.properties.mail.smtp.writetimeout=5000


# MAIN LOCATION ON WEBSITE FROM HEADER AND FOOTER

Company headquarters address. It is displayed on each page in the footer and header.




# To run you will need:
<ul>
  <li>MySQL server</li>
  <li>Gradle</li>
  <li> IntelliJ Idea / Tomcat </li>
</ul>

</br>



### To run the project, follow these steps:
1.	Clone repository: https://github.com/kubabar1/CarRental.git
```
  git clone https://github.com/MarcinNidecki/car-door-web-service.git
```

2. use sql scripts with sample test data to create a database in MySql server 


3. build the application using Gradle	

After the Gradle program finishes, go to the build/libs folder and copy the cardoor-0.0.1-SNAPSHOW.war
to the folder * webapps * of our * Tomcat * server.
    
Then start the Tomcat server using the startup.bat command

 *http://localhost:8080/filename

If you want to open project just in your IDE 
remember to comment  this code in CardoorApplication.class  and plugin war from build.gradlle

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return  application.sources(CardoorApplication.class);
    }  
