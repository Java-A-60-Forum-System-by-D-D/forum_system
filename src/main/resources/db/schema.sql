
set FOREIGN_KEY_CHECKS = 0;
create or replace table categories
(
    id            int auto_increment
        primary key,
    category_name varchar(30) null,
    description   varchar(50) null
);

create or replace table roles
(
    id   int auto_increment
        primary key,
    role enum ('ADMIN', 'MODERATOR', 'USER') null
);

create or replace table spring_session
(
    PRIMARY_ID            char(36)     not null
        primary key,
    SESSION_ID            char(36)     not null,
    CREATION_TIME         bigint       not null,
    LAST_ACCESS_TIME      bigint       not null,
    MAX_INACTIVE_INTERVAL int          not null,
    EXPIRY_TIME           bigint       not null,
    PRINCIPAL_NAME        varchar(100) null,
    constraint SPRING_SESSION_IX1
        unique (SESSION_ID)
)
    row_format = DYNAMIC;

create or replace index SPRING_SESSION_IX2
    on spring_session (EXPIRY_TIME);

create or replace index SPRING_SESSION_IX3
    on spring_session (PRINCIPAL_NAME);

create or replace table spring_session_attributes
(
    SESSION_PRIMARY_ID char(36)     not null,
    ATTRIBUTE_NAME     varchar(200) not null,
    ATTRIBUTE_BYTES    blob         not null,
    primary key (SESSION_PRIMARY_ID, ATTRIBUTE_NAME),
    constraint SPRING_SESSION_ATTRIBUTES_FK
        foreign key (SESSION_PRIMARY_ID) references spring_session (PRIMARY_ID)
            on delete cascade
)
    row_format = DYNAMIC;

create or replace index SPRING_SESSION_ATTRIBUTES_IX1
    on spring_session_attributes (SESSION_PRIMARY_ID);

create or replace table tags
(
    id   int auto_increment
        primary key,
    name varchar(255) null
);

create or replace table users
(
    id            int auto_increment
        primary key,
    is_blocked    bit          null,
    created_at    datetime(6)  null,
    updated_at    datetime(6)  null,
    first_name    varchar(32)  not null,
    last_name     varchar(32)  not null,
    password_hash varchar(100) not null,
    email         varchar(255) not null,
    phone_number  varchar(255) null,
    photo_url     varchar(255) null,
    username      varchar(255) not null,
    constraint UK23y4gd49ajvbqgl3psjsvhff6
        unique (username),
    constraint UKncoa9bfasrql0x4nhmh1plxxy
        unique (email)
);

create or replace table posts
(
    category_id int           null,
    id          int auto_increment
        primary key,
    likes_count int           null,
    user_id     int           not null,
    created_At  datetime(6)   null,
    updated_at  datetime(6)   null,
    title       varchar(64)   not null,
    content     varchar(8192) not null,
    constraint FKeatd60h39ra63bdk44w8vfvk2
        foreign key (category_id) references categories (id),
    constraint FKqwy1e63idnvjerwvc47tq3k5
        foreign key (user_id) references users (id)
);

create or replace table comments
(
    id                int auto_increment
        primary key,
    parent_comment_id int          null,
    post_id           int          not null,
    user_id           int          not null,
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

create or replace table likes
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

create or replace table post_tags
(
    id     int not null,
    tag_id int not null,
    primary key (id, tag_id),
    constraint FKeouwh57y9blek9aa4mulekcui
        foreign key (id) references posts (id),
    constraint FKm6cfovkyqvu5rlm6ahdx3eavj
        foreign key (tag_id) references tags (id)
);

create or replace table users_roles
(
    role_Id int not null,
    user_id int not null,
    primary key (role_Id, user_id),
    constraint FKd7cnvrrg9medric9br3dty68
        foreign key (user_id) references users (id),
    constraint FKq5kuha6esfmijvmn2jcsap0bs
        foreign key (role_Id) references roles (id)
);
       set FOREIGN_KEY_CHECKS = 1;

