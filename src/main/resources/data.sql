INSERT INTO users (username, email, password, role) VALUES
('Blackbeard', 'blackbeard@pirates.com', '$2a$10$lc5Xlw.Wv6stGyOA6YpEFe5qFo4.AIT9bvIuWKYk/o0qlGdBLoxsy', 'ADMIN'),
('AnneBonny', 'anne@pirates.com', '$2a$10$3B9Clbt3tlEkvxPmwjZO3eATjaTT3PyXDdFMxkDbSZ.1OKfPrUuAC', 'USER'),
('CalicoJack', 'jack@pirates.com', '$2a$10$xCWc0eD0EEG.w3aVOI9wQeKeFG5zUVTMo1gwYeOaomHFmcnHVJkcG', 'USER'),
('MaryRead', 'mary@pirates.com', '$2a$10$Rv3bNqFHrACcYidS78VaiOo1xGaLNxit/uvseW1wL/S.ZbFb2cQgS', 'USER'),
('HenryMorgan', 'morgan@pirates.com', '$2a$10$yJraagKjfvoit5kOxdkGJOPvQkAP/U2jQKgSUSf16X5pzXFUjXOpu', 'USER'),
('LongJohnSilver', 'silver@pirates.com', '$2a$10$juK7PrSv80CvzMd1ulBy1.cfig/vU.EITg2P719B8rOD9.UF9T38.', 'USER'),
('CaptainKidd', 'kidd@pirates.com', '$2a$10$rnLAv.Plf9EGARraior1memedmQeq9ScKp8/DajshQJyh7j5xo/BG', 'USER'),
('CharlesVane', 'vane@pirates.com', '$2a$10$mu6tiMzNvf3n.6LuzKC8sufT3MDct5UNHNTUlurd.aATwuG0CUdLS', 'USER'),
('BartholomewRoberts', 'roberts@pirates.com', '$2a$10$hdQqwzN.SmQ6DbxKTJR9p.khZQYJGlsZxbaT5MBZtAAxjhfyocxuS', 'USER'),
('JackSparrow', 'sparrow@pirates.com', '$2a$10$V57qP2BDxRjUiv7ib1cfkOeGUYSDTJxL7CsIUtbm3BrW4HwQrY2P2', 'USER');

INSERT INTO destinations (city, country, description, image_url, user_id) VALUES
('Tortuga', 'Caribbean', 'A notorious pirate haven known for rum and revelry.', 'https://example.com/tortuga.jpg', 1),
('Port Royal', 'Jamaica', 'Once called the richest and wickedest city in the world.', 'https://example.com/portroyal.jpg', 2),
('Isla de Muerta', 'Unknown', 'A mysterious island that cannot be found except by those who know where it is.', 'https://example.com/muerta.jpg', 3),
('Nassau', 'Bahamas', 'Famous pirate stronghold and base of the Pirate Republic.', 'https://example.com/nassau.jpg', 4),
('Madagascar', 'East Africa', 'A remote paradise for pirates to rest and hide treasures.', 'https://example.com/madagascar.jpg', 5),
('Shipwreck Cove', 'Secret', 'Legendary meeting place of the Pirate Lords.', 'https://example.com/shipwreck.jpg', 6),
('Dead Man''s Chest Island', 'Secret', 'Home to Davy Jones'' heart.', 'https://example.com/deadmanschest.jpg', 7),
('Skull Island', 'Mystery Sea', 'Skull-shaped island rumored to be cursed.', 'https://example.com/skullisland.jpg', 8),
('The Black Lagoon', 'Unknown', 'Dark waters filled with legends and sea monsters.', 'https://example.com/blacklagoon.jpg', 9),
('Devil''s Triangle', 'Atlantic Ocean', 'Area of mysterious disappearances and pirate ghost ships.', 'https://example.com/devilstriangle.jpg', 10);
