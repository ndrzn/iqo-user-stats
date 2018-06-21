## Test Task - User Statistic

### Usage

##### Routes: 

1. Show unique user statistic

```html
   GET http://host:port/stat 
```
2. Register user entry

```html
    Content-Type: application/json
    POST http://host:port/user
    Data: {"user_id":"<username>"}
```
3. Show statistic by user

```html
    GET http://host:port/user/{id}
    id - Int
```
    
### Installation

#####1. Option: test api via temp server

[http://51.38.39.172/user/1](http://51.38.39.172/user/1)

 
#####2. Option: Download and run fatJar (~35mb)
```html
    wget https://bitbucket.org/jsfwa/iqo-user-stats/downloads/iqo-user-stats-0.1.4
    sudo chmod +x iqo-user-stats-0.1.4
    sudo ./iqo-user-stats-0.1.4 [-p <port>] [--in-memory]
```
* [--in-memory] - just to play with api without using database

#####3. Option: sbt
```html
    git clone https://jsfwa@bitbucket.org/jsfwa/iqo-user-stats.git
    sbt "run [-p <port>] [--in-memory]"  
```       
    