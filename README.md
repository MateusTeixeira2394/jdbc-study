# JDBC Study

## Descripton

To study JDBC I used this project to implement a tiny bank app using DDD (Domain Driven Design) pattern and Servlet technology. This app allows the user to:

- Create a new account
- Find the account
- List all accounts stored in the database
- Delete account
- Make deposit
- Make withdraw
- Transfer some amount from account to another one

## Configuration

To run the project you must configure the database connection by the following environment variables:

- db_port: The port to where the database are configure
- db_name: The name of the database
- db_user: The user that accesses the database
- db_pass: The password to access the database

Besides that, the database used in this project was the [Mysql](https://www.mysql.com/)


## Routes

|Route name           |HTTP Verb|Route                                               |Body                                           |
|---------------------|---------|----------------------------------------------------|-----------------------------------------------|
|Acreate a new Account|POST     |/jdbc-study/account                                 |[New Account Body](#new-account-body)          |
|Deposit              |POST     |/jdbc-study/account/deposit                         |[Deposit/Withdraw Body](#deposit-withdraw-body)|
|Withdraw             |POST     |/jdbc-study/account/withdraw                        |[Deposit/Withdraw Body](#deposit-withdraw-body)|
|Transfer             |POST     |/jdbc-study/account/transfer                        |[Transfer Body](#transfer-body)                |
|List all acounts     |GET      |/jdbc-study/account/all                             |                                               |
|Find an account      |GET      |/jdbc-study/account?id=1 or /jdbc-study/account?id=1|                                               |
|Delete an account    |DELETE   |/jdbc-study/account?account=1                       |                                               |



### New Account Body {#new-account-body}

```json

{
    "account":1,
    "name": "String",
    "cpf": "String",
    "email":"String",
    "agency": 1
}

```

### Deposit/Withdraw Body {#deposit-withdraw-body}

```json

{
    "account":1,
    "value": 1.0
}

```
### Transfer Body {#transfer-body}

```json

{
    "sender":1,
    "receiver": 1,
    "value": 1.0
}

```