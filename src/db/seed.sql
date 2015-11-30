INSERT INTO post (name, title, content) VALUES 
('Bruce', 'Enabling Foreign Key Support', 'Foreign key constraints are disabled by default (for backwards compatibility), so must be enabled separately for each database connection. (Note, however, that future releases of SQLite might change so that foreign key constraints enabled by default. Careful developers will not make any assumptions about whether or not foreign keys are enabled by default but will instead enable or disable them as necessary.)'),
('Bruce', 'Required and Suggested Database Indexes', 'Usually, the parent key of a foreign key constraint is the primary key of the parent table. If they are not the primary key, then the parent key columns must be collectively subject to a UNIQUE constraint or have a UNIQUE index. If the parent key columns have a UNIQUE index, then that index must use the collation sequences that are specified in the CREATE TABLE statement for the parent table.' ),
('Edward Nigma', 'Riddle Riddle', 'Q: A dad and his son were riding their bikes and crashed. Two ambulances came and took them to different hospitals. The man’s son was in the operating room and the doctor said, “I can’t operate on you. You’re my son.”
How is that possible?
A: The doctor is his mom!'),
('Bane', 'Oh, you think darkness is your ally.', 'But you merely adopted the dark; I was born in it, moulded by it. I didnt see the light until I was already a man, by then it was nothing to me but BLINDING!');

INSERT INTO post (name, date_created, content) VALUES
('Bruce', '2014-08-12 09:23:12', 'Following example uses PrepareStatement method to create PreparedStatement. It also uses setInt & setString methods of PreparedStatement to set parameters of PreparedStatement.'),
('SQLite', '2000-05-22 15:11:10', 'All five date and time functions take a time string as an argument. The time string is followed by zero or more modifiers. The strftime() function also takes a format string as its first argument..'),
('Sinchew', '2015-11-29 17:17:00', '（彭亨‧北根29日訊）首相拿督斯里納吉說，我國曾於2013年發生蘇祿軍侵入事件，導致10名軍警為國犧牲，也有2名平民受害；假如我國人民還可以非常友善、笑容滿臉地與曾經侵入我國的蘇祿軍相關人士合照，這則會傷了沙巴人及為國犧牲軍警家屬的心。'); 
