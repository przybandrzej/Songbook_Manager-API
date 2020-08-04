for i in {1..5}; do \
  curl -X POST -H "Content-Type: application/json" -d \
    '{"id": 0, "name":"category '$i'"}' "http://localhost:8080/api/categories"; \
done

curl -X POST -H "Content-Type: application/json" -d \
    '{"id": 0, "name":"superuser"}' "http://localhost:8080/api/user_roles"; \

curl -X POST -H "Content-Type: application/json" -d \
    '{"id": 0, "name":"admin"}' "http://localhost:8080/api/user_roles"; \

curl -X POST -H "Content-Type: application/json" -d \
    '{"id": 0, "name":"moderator"}' "http://localhost:8080/api/user_roles"; \

curl -X POST -H "Content-Type: application/json" -d \
    '{"id": 0, "name":"user"}' "http://localhost:8080/api/user_roles"; \

curl -X POST -H "Content-Type: application/json" -d \
    '{"roleId": 1, "email":"admin@admin.com", "password": "superuser", "username": "superuser", "firstName": "Super", "lastName": "User"}' "http://localhost:8080/api/admin/user";
