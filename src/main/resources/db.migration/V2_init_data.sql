INSERT INTO user_roles(name) VALUES (${role.superuser});
INSERT INTO user_roles(name) VALUES (${role.admin});
INSERT INTO user_roles(name) VALUES (${role.moderator});
INSERT INTO user_roles(name) VALUES (${role.user});

INSERT INTO users(activated, e_mail, first_name, last_name, password, username, user_role_id)
    VALUES (true, ${superuser.email}, ${superuser.first-name}, ${superuser.last-name}, ${superuser.password-hash},
            ${superuser.username}, 1);
