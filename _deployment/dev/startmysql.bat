docker stop grouppayments_mysql
docker rm grouppayments_mysql
docker run -d -e MYSQL_ALLOW_EMPTY_PASSWORD=yes -e MYSQL_ROOT_PASSWORD= -p 3306:3306 --name grouppayments_mysql mysql:8