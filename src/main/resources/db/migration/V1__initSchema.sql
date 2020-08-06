
CREATE TABLE authors (
                         id bigint NOT NULL,
                         biography_url character varying(255),
                         name character varying(255) NOT NULL,
                         photo_resource character varying(255)
);

CREATE SEQUENCE authors_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE authors_id_seq OWNED BY authors.id;

CREATE TABLE categories (
                            id bigint NOT NULL,
                            name character varying(255) NOT NULL
);

CREATE SEQUENCE categories_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE categories_id_seq OWNED BY categories.id;

CREATE TABLE playlists (
                           id bigint NOT NULL,
                           creation_time timestamp without time zone DEFAULT now() NOT NULL,
                           is_private boolean NOT NULL,
                           name character varying(255) NOT NULL,
                           owner_id bigint NOT NULL
);

CREATE SEQUENCE playlists_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE playlists_songs (
                                 playlist_id bigint NOT NULL,
                                 song_id bigint NOT NULL
);

CREATE TABLE song_adds (
                           id bigint NOT NULL,
                           "timestamp" timestamp without time zone NOT NULL,
                           user_id bigint NOT NULL,
                           song_id bigint NOT NULL
);

CREATE SEQUENCE song_adds_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE song_adds_id_seq OWNED BY song_adds.id;

CREATE TABLE song_edits (
                            id bigint NOT NULL,
                            "timestamp" timestamp without time zone NOT NULL,
                            user_id bigint NOT NULL,
                            song_id bigint NOT NULL
);

CREATE SEQUENCE song_edits_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE song_edits_id_seq OWNED BY song_edits.id;

CREATE TABLE songs (
                       id bigint NOT NULL,
                       guitar_tabs text NOT NULL,
                       is_awaiting boolean NOT NULL,
                       lyrics text NOT NULL,
                       title character varying(255) NOT NULL,
                       trivia text,
                       author_id bigint NOT NULL,
                       category_id bigint NOT NULL
);

CREATE TABLE songs_coauthors (
                                 author_id bigint NOT NULL,
                                 song_id bigint NOT NULL,
                                 coauthor_function character varying(255) NOT NULL,
                                 CONSTRAINT songs_coauthors_coauthor_function_check CHECK (((coauthor_function)::text = ANY ((ARRAY['muzyka'::character varying, 'tekst'::character varying])::text[])))
);

CREATE SEQUENCE songs_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE songs_id_seq OWNED BY songs.id;

CREATE TABLE songs_tags (
                            song_id bigint NOT NULL,
                            tag_id bigint NOT NULL
);

CREATE TABLE tags (
                      id bigint NOT NULL,
                      name character varying(255) NOT NULL
);

CREATE SEQUENCE tags_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE tags_id_seq OWNED BY tags.id;

CREATE TABLE user_roles (
                            id bigint NOT NULL,
                            name character varying(255) NOT NULL
);

CREATE SEQUENCE user_roles_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE user_roles_id_seq OWNED BY user_roles.id;

CREATE TABLE users (
                       id bigint NOT NULL,
                       activated boolean NOT NULL,
                       activation_key character varying(20),
                       e_mail character varying(255) NOT NULL,
                       first_name character varying(255),
                       image_url character varying(256),
                       last_name character varying(255),
                       password character varying(255) NOT NULL,
                       registration_date timestamp without time zone NOT NULL,
                       reset_date timestamp without time zone,
                       reset_key character varying(20),
                       username character varying(255) NOT NULL,
                       user_role_id bigint NOT NULL,
                       CONSTRAINT users_check CHECK (((length((password)::text) >= 6) AND (length((username)::text) >= 4)))
);

CREATE SEQUENCE users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE users_id_seq OWNED BY users.id;

CREATE TABLE users_songs (
                             user_id bigint NOT NULL,
                             song_id bigint NOT NULL
);

CREATE TABLE users_songs_ratings (
                                     song_id bigint NOT NULL,
                                     user_id bigint NOT NULL,
                                     rating double precision NOT NULL,
                                     CONSTRAINT users_songs_ratings_rating_check CHECK (((rating >= (0)::double precision) AND (rating <= (1)::double precision)))
);

ALTER TABLE ONLY authors ALTER COLUMN id SET DEFAULT nextval('authors_id_seq'::regclass);

ALTER TABLE ONLY categories ALTER COLUMN id SET DEFAULT nextval('categories_id_seq'::regclass);

ALTER TABLE ONLY playlists ALTER COLUMN id SET DEFAULT nextval('playlists_id_seq'::regclass);

ALTER TABLE ONLY song_adds ALTER COLUMN id SET DEFAULT nextval('song_adds_id_seq'::regclass);

ALTER TABLE ONLY song_edits ALTER COLUMN id SET DEFAULT nextval('song_edits_id_seq'::regclass);

ALTER TABLE ONLY songs ALTER COLUMN id SET DEFAULT nextval('songs_id_seq'::regclass);

ALTER TABLE ONLY tags ALTER COLUMN id SET DEFAULT nextval('tags_id_seq'::regclass);

ALTER TABLE ONLY user_roles ALTER COLUMN id SET DEFAULT nextval('user_roles_id_seq'::regclass);

ALTER TABLE ONLY users ALTER COLUMN id SET DEFAULT nextval('users_id_seq'::regclass);

ALTER TABLE ONLY authors
    ADD CONSTRAINT authors_pkey PRIMARY KEY (id);

ALTER TABLE ONLY categories
    ADD CONSTRAINT categories_pkey PRIMARY KEY (id);

ALTER TABLE ONLY playlists
    ADD CONSTRAINT playlists_pkey PRIMARY KEY (id);

ALTER TABLE ONLY playlists_songs
    ADD CONSTRAINT playlists_songs_pkey PRIMARY KEY (playlist_id, song_id);

ALTER TABLE ONLY song_adds
    ADD CONSTRAINT song_adds_pkey PRIMARY KEY (id);

ALTER TABLE ONLY song_edits
    ADD CONSTRAINT song_edits_pkey PRIMARY KEY (id);

ALTER TABLE ONLY songs_coauthors
    ADD CONSTRAINT songs_coauthors_pkey PRIMARY KEY (author_id, song_id);

ALTER TABLE ONLY songs
    ADD CONSTRAINT songs_pkey PRIMARY KEY (id);

ALTER TABLE ONLY songs_tags
    ADD CONSTRAINT songs_tags_pkey PRIMARY KEY (song_id, tag_id);

ALTER TABLE ONLY tags
    ADD CONSTRAINT tags_pkey PRIMARY KEY (id);

ALTER TABLE ONLY user_roles
    ADD CONSTRAINT uk_182xa1gitcxqhaq6nn3n2kmo3 UNIQUE (name);

ALTER TABLE ONLY authors
    ADD CONSTRAINT uk_9mhkwvnfaarcalo4noabrin5j UNIQUE (name);

ALTER TABLE ONLY users
    ADD CONSTRAINT uk_ehv2osdo3bfokh566calsfx32 UNIQUE (e_mail);

ALTER TABLE ONLY users
    ADD CONSTRAINT uk_r43af9ap4edm43mmtq01oddj6 UNIQUE (username);

ALTER TABLE ONLY tags
    ADD CONSTRAINT uk_t48xdq560gs3gap9g7jg36kgc UNIQUE (name);

ALTER TABLE ONLY categories
    ADD CONSTRAINT uk_t8o6pivur7nn124jehx7cygw5 UNIQUE (name);

ALTER TABLE ONLY user_roles
    ADD CONSTRAINT user_roles_pkey PRIMARY KEY (id);

ALTER TABLE ONLY users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);

ALTER TABLE ONLY users_songs
    ADD CONSTRAINT users_songs_pkey PRIMARY KEY (user_id, song_id);

ALTER TABLE ONLY users_songs_ratings
    ADD CONSTRAINT users_songs_ratings_pkey PRIMARY KEY (song_id, user_id);

ALTER TABLE ONLY playlists_songs
    ADD CONSTRAINT fk2emrpqhxanqunhcodn7s5yca4 FOREIGN KEY (playlist_id) REFERENCES playlists(id);

ALTER TABLE ONLY users_songs
    ADD CONSTRAINT fk3vx5y8rvh2oek198t925nqbup FOREIGN KEY (song_id) REFERENCES songs(id);

ALTER TABLE ONLY song_adds
    ADD CONSTRAINT fk5459wu32i4sy7okjc30bh4eiy FOREIGN KEY (song_id) REFERENCES songs(id);

ALTER TABLE ONLY playlists_songs
    ADD CONSTRAINT fk5pyqf1ff6dfj1xbn5p626i0dr FOREIGN KEY (song_id) REFERENCES songs(id);

ALTER TABLE ONLY users_songs
    ADD CONSTRAINT fk7jxoobydr84yuyj84n7161pre FOREIGN KEY (user_id) REFERENCES users(id);

ALTER TABLE ONLY songs_tags
    ADD CONSTRAINT fk9ryivoo3ne3egb2wa7yawus15 FOREIGN KEY (song_id) REFERENCES songs(id);

ALTER TABLE ONLY users_songs_ratings
    ADD CONSTRAINT fkbmrck5akyseff59mo09c4py5l FOREIGN KEY (user_id) REFERENCES users(id);

ALTER TABLE ONLY song_edits
    ADD CONSTRAINT fkbqac0dw95btyxd2d0tkdekfxi FOREIGN KEY (song_id) REFERENCES songs(id);

ALTER TABLE ONLY songs_coauthors
    ADD CONSTRAINT fkcppt0hj2oykosf66nbovfw6j3 FOREIGN KEY (author_id) REFERENCES authors(id);

ALTER TABLE ONLY songs_tags
    ADD CONSTRAINT fkg245s9siiemx0ngjxbhksmbm3 FOREIGN KEY (tag_id) REFERENCES tags(id);

ALTER TABLE ONLY songs_coauthors
    ADD CONSTRAINT fkjo2ddfnj8v06mg5p0hvhpfcl6 FOREIGN KEY (song_id) REFERENCES songs(id);

ALTER TABLE ONLY song_edits
    ADD CONSTRAINT fkl4nb4t2utkl5p94egwsa4ykr8 FOREIGN KEY (user_id) REFERENCES users(id);

ALTER TABLE ONLY playlists
    ADD CONSTRAINT fkn0r1rrm19f8j7wfl656bd9sus FOREIGN KEY (owner_id) REFERENCES users(id);

ALTER TABLE ONLY song_adds
    ADD CONSTRAINT fkpbeor2nij9va4ytc3o29347mu FOREIGN KEY (user_id) REFERENCES users(id);

ALTER TABLE ONLY songs
    ADD CONSTRAINT fksq04x1ul37p279vnj1fyc4is2 FOREIGN KEY (author_id) REFERENCES authors(id);

ALTER TABLE ONLY songs
    ADD CONSTRAINT fkst02nu3f6odq3fm7knvediclw FOREIGN KEY (category_id) REFERENCES categories(id);

ALTER TABLE ONLY users
    ADD CONSTRAINT fksy1luwgtc2qas77si4xlrkjtl FOREIGN KEY (user_role_id) REFERENCES user_roles(id);

ALTER TABLE ONLY users_songs_ratings
    ADD CONSTRAINT fktgmk1mkxrpgq6chgk3on665g5 FOREIGN KEY (song_id) REFERENCES songs(id);
