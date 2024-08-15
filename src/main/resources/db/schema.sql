create table roles
(
    id   int auto_increment
        primary key,
    role enum ('ADMIN', 'MODERATOR', 'USER') null
);

create table tags
(
    id   int auto_increment
        primary key,
    name varchar(255) null
);

create table users
(
    id            int auto_increment
        primary key,
    is_blocked    bit          null,
    created_at    datetime(6)  null,
    updated_at    datetime(6)  null,
    email         varchar(255) not null,
    first_name    varchar(255) not null,
    last_name     varchar(255) not null,
    password_hash varchar(255) not null,
    phone_number  varchar(255) null,
    username      varchar(255) not null
);

create table posts
(
    id          int auto_increment
        primary key,
    likes_count int          null,
    user_id     int          null,
    created_At  datetime(6)  null,
    updated_at  datetime(6)  null,
    content     varchar(255) not null,
    title       varchar(255) not null,
    constraint FKqwy1e63idnvjerwvc47tq3k5
        foreign key (user_id) references users (id)
);

create table comments
(
    id                int auto_increment
        primary key,
    parent_comment_id int          null,
    post_id           int          null,
    user_id           int          null,
    created_At        datetime(6)  null,
    updated_at        datetime(6)  null,
    content           varchar(255) not null,
    constraint FK7h839m3lkvhbyv3bcdv7sm4fj
        foreign key (parent_comment_id) references comments (id),
    constraint FKja5i1vdiw6jmv6pno39qlr6od
        foreign key (user_id) references users (id),
    constraint FKrcbvcroe2c5uu8ij99lb7vcfd
        foreign key (post_id) references posts (id)
);

create table likes
(
    id         int auto_increment
        primary key,
    post_id    int         null,
    user_id    int         null,
    created_At datetime(6) null,
    constraint FKa91dvnfnx92g2c4k7w1aw9ebu
        foreign key (user_id) references users (id),
    constraint FKsrb4xtf50k8mp3wd8tf5r6s1y
        foreign key (post_id) references posts (id)
);

create table post_tags
(
    id     int not null,
    tag_id int not null,
    primary key (id, tag_id),
    constraint FKeouwh57y9blek9aa4mulekcui
        foreign key (id) references posts (id),
    constraint FKm6cfovkyqvu5rlm6ahdx3eavj
        foreign key (tag_id) references tags (id)
);

create table users_roles
(
    role_Id int not null,
    user_id int not null,
    primary key (role_Id, user_id),
    constraint FKd7cnvrrg9medric9br3dty68
        foreign key (user_id) references users (id),
    constraint FKq5kuha6esfmijvmn2jcsap0bs
        foreign key (role_Id) references roles (id)
);
