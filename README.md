## Reference POC for using sendgrid and twilio for email and sms notification with spring-boot

### Setup

fill-out the required fields in application.properties

```
#SendGrid
com.hardik.ballista.sendgrid.email-id=<Email-id goes here>
com.hardik.ballista.sendgrid.key=<Token key goes here>

#Twilio
com.hardik.ballista.twilio.account-sid=<Twilio SID goes here>
com.hardik.ballista.twilio.auth-token=<Twilio auth token goes here>
com.hardik.ballista.twilio.contact-number=<Twilio contact number goes here>
```

run the below commands in the core

```
mvn clean install
```

```
mvn spring-boot:run
```