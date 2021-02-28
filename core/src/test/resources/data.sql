INSERT INTO public.gift_certificates
(id, created_date, last_update_date, description, duration, name, price)
VALUES (1, now(), now(), 'description1', 13, 'name1', 123),
       (2, now(), now(), 'description2', 13, 'name2', 123),
       (3, now(), now(), 'description3', 13, 'name3', 123),
       (4, now(), now(), 'description4', 13, 'name4', 123),
       (5, now(), now(), 'description5', 13, 'name5', 123);

INSERT INTO public.tags
    (id, created_date, last_update_date, name)
VALUES (1, now(), now(), 'name1'),
       (2, now(), now(), 'name2'),
       (3, now(), now(), 'name3'),
       (4, now(), now(), 'name4'),
       (5, now(), now(), 'name5');

INSERT INTO public.gift_tags
    (gift_id, tag_id)
VALUES (1, 1),
       (1, 2),
       (2, 3),
       (2, 4),
       (2, 5);