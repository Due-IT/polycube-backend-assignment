-- Members
INSERT INTO members (name, grade)
VALUES ('일반인', 'NORMAL'), ('우수회원', 'VIP'), ('최우수회원', 'VVIP');

-- DiscountSettings
INSERT INTO discount_settings (policy_name, discount_percent, discount_amount, priority)
VALUES ('NORMAL', 0, 0, 1),
    ('VIP', 00, 1000, 1),
    ('VVIP', 10, 0, 1),
    ('POINT', 5, 0, 2);
